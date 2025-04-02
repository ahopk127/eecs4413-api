package org.milton.auctionservice.services; //package declaration - places MainService in the auctionservice.services package

import org.milton.auctionservice.models.*; //importing all models - including Item, User, AuctionOrder, Bidding...
import org.milton.auctionservice.repository.BiddingRepository; // importing BiddingRepository - repository interface for database operations related to bidding
import org.milton.auctionservice.repository.OrderRepository; // importing OrderRepository - repository interface for database operations related to orders
import org.milton.auctionservice.request.BiddingRequest; // importing BiddingRequest - custom request model for handling bidding requests
import org.milton.auctionservice.request.PaymentRequest;// importing PaymentRequest - custom request model for handling payment requests
import org.springframework.stereotype.Service; // importing Service - Spring annotation to indicate that this class is a service component

import java.sql.Timestamp; // importing Timestamp - used to handle auction time comparisons
/*
* this service handles bidding (create bid)
    * validates bid amount
    * updates item details - highest bid, status, current price
    * saves bid to the database
    * supports Dutch and forward auction types
* this service handles order creation (create order) 
    * fetches buyer and item
    * handles total price calculation - includes shipping and expedited shipping
    * saves and returs order to the database
* this service uses helper method (getbiddingObject) - simplifies bid object creation
*/
@Service //marks this class as a Spring managed service - indicating that this class contains business logic and can be injected into other components
public class MainService { //provides method to handle bidding and order processing

    private final ItemService itemService; //service for fetching and updating auction items
    private final UserService userService; //service for fetching user details
    private final BiddingRepository biddingRepository; //handles bid storage
    private final OrderRepository orderRepository; //handles order storage

    //constructor injection - spring automatically injects instances of ItemService, UserService, BiddingRepository and OrderRepository when creating this service
    public MainService(ItemService itemService, UserService userService, BiddingRepository biddingRepository, OrderRepository orderRepository) {
        this.itemService = itemService; //service for managing auction items
        this.userService = userService; //service for managing user data
        this.biddingRepository = biddingRepository; //repository for storing bids
        this.orderRepository = orderRepository; //repository for storing orders
    }

    public Item createBid(BiddingRequest request) { //handles placing a bid 
        //takes BiddingRequest and updates the item accordingly
    	
        //validation checks
    	if (request.getBidAmount() <= 0) { //ensures that the bid amount is greater than zero
            throw new IllegalArgumentException("Bid amount must be greater than zero.");
        }
        Item item = itemService.getItemById(request.getItemId()).get(); //retrieves the auction item by ID from the ItemService
        User user = userService.getUserById(request.getUserId()).get(); //retriever the user who is bidding by ID from the UserService
        Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //captures the current timestamp

        //if it's a Dutch auction and the item is available, the highest bidder wins immediately
        if(item.getAuctionType().equals(AuctionType.DUTCH) && item.getStatus().equals(ItemStatus.AVAILABLE)){
            item.setStatus(ItemStatus.SOLD); //the item is marked as sold
            item.setHighestBidder(user); //the highest bidder is set
            biddingRepository.save(getBiddingObject(user,item,item.getStartingPrice())); //the bid is saved to the repository
            return itemService.updateItem(item.getId(), item); //the updated item is returned
        }

        //conditions for placing a bid in a forward auction
        //if item is available for bidding
        //auction type is forward 
        //new bid is higher than the current highest bid
        //the new bid is higher than the starting price
        //the auction has not expired
        else if(item.getStatus().equals(ItemStatus.AVAILABLE) && item.getAuctionType().equals(AuctionType.FORWARD) && item.getCurrentPrice() < request.getBidAmount() &&
                    item.getStartingPrice() < request.getBidAmount() &&
                    item.getDuration().compareTo(currentTime) > 0) {
                item.setCurrentPrice(request.getBidAmount()); //new highest bid is set
                item.setHighestBidder(user); //highest bidder is updated
                biddingRepository.save(getBiddingObject(user,item,request.getBidAmount())); //bid is stored in the database
                return itemService.updateItem(item.getId(), item); //updated item is returned

        }

        return item;
        
        
    }

    private Bidding getBiddingObject(User user,Item item, Double bidAmount){ //creates a new Bidding object
        //links new bid to the user, item and bid amount
        Bidding bidding = new Bidding(); 
        bidding.setBidder(user);
        bidding.setItem(item);
        bidding.setBidingPrice(bidAmount);
        return bidding;
    }

    public AuctionOrder createOrder(PaymentRequest paymentRequest) { //handles order creation when user purchases an item
        //retrieves the user and item from the database
        User user = userService.getUserById(paymentRequest.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID")); //if user not found, throws exception
        Item item = itemService.getItemById(paymentRequest.getItemId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid item ID")); //if item not found, throws exception

        AuctionOrder order = new AuctionOrder(); //creates a new AuctionOrder and assigns the buyer and item
        order.setBuyer(user);
        order.setItem(item);

        // Handles/Prevents nulls - if currentPrice, shippingPrice or expeditedPrice are null, it defaults to 0.0
        double currentPrice = item.getCurrentPrice() != null ? item.getCurrentPrice() : 0.0;
        double shippingPrice = item.getShippingPrice() != null ? item.getShippingPrice() : 0.0;
        double expeditedPrice = item.getExpeditedShippingPrice() != null ? item.getExpeditedShippingPrice() : 0.0;

        //calculates the total price
        if (paymentRequest.getShipping()) {
            order.setTotalPrice(currentPrice + shippingPrice + expeditedPrice); //is expedited shipping is selected, add the expedited price
        } else {
            order.setTotalPrice(currentPrice + shippingPrice); //if not, just add the regular shipping price
        
        

        return orderRepository.save(order); //Saves the order to the database and returns it.
    }

}
}