package org.milton.auctionservice.services;

import org.milton.auctionservice.models.*;
import org.milton.auctionservice.repository.BiddingRepository;
import org.milton.auctionservice.repository.OrderRepository;
import org.milton.auctionservice.request.BiddingRequest;
import org.milton.auctionservice.request.PaymentRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MainService {

    private final ItemService itemService;
    private final UserService userService;
    private final BiddingRepository biddingRepository;
    private final OrderRepository orderRepository;

    public MainService(ItemService itemService, UserService userService, BiddingRepository biddingRepository, OrderRepository orderRepository) {
        this.itemService = itemService;
        this.userService = userService;
        this.biddingRepository = biddingRepository;
        this.orderRepository = orderRepository;
    }

    public Item createBid(BiddingRequest request) {
    	
    	if (request.getBidAmount() <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero.");
        }
        Item item = itemService.getItemById(request.getItemId()).get();
        User user = userService.getUserById(request.getUserId()).get();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if(item.getAuctionType().equals(AuctionType.DUTCH) && item.getStatus().equals(ItemStatus.AVAILABLE)){
            item.setStatus(ItemStatus.SOLD);
            item.setHighestBidder(user);
            biddingRepository.save(getBiddingObject(user,item,item.getStartingPrice()));
            return itemService.updateItem(item.getId(), item);
        }

        else if(item.getStatus().equals(ItemStatus.AVAILABLE) && item.getAuctionType().equals(AuctionType.FORWARD) && item.getCurrentPrice() < request.getBidAmount() &&
                    item.getStartingPrice() < request.getBidAmount() &&
                    item.getDuration().compareTo(currentTime) > 0) {
                item.setCurrentPrice(request.getBidAmount());
                item.setHighestBidder(user);
                biddingRepository.save(getBiddingObject(user,item,request.getBidAmount()));
                return itemService.updateItem(item.getId(), item);

        }

        return item;
        
        
    }

    private Bidding getBiddingObject(User user,Item item, Double bidAmount){
        Bidding bidding = new Bidding();
        bidding.setBidder(user);
        bidding.setItem(item);
        bidding.setBidingPrice(bidAmount);
        return bidding;
    }

    public AuctionOrder createOrder(PaymentRequest paymentRequest) {
        User user = userService.getUserById(paymentRequest.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        Item item = itemService.getItemById(paymentRequest.getItemId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));

        AuctionOrder order = new AuctionOrder();
        order.setBuyer(user);
        order.setItem(item);

        // Handle nulls safely by defaulting to 0.0
        double currentPrice = item.getCurrentPrice() != null ? item.getCurrentPrice() : 0.0;
        double shippingPrice = item.getShippingPrice() != null ? item.getShippingPrice() : 0.0;
        double expeditedPrice = item.getExpeditedShippingPrice() != null ? item.getExpeditedShippingPrice() : 0.0;

        if (paymentRequest.getShipping()) {
            order.setTotalPrice(currentPrice + shippingPrice + expeditedPrice);
        } else {
            order.setTotalPrice(currentPrice + shippingPrice);
        }
        
        

        return orderRepository.save(order);
    }

}
