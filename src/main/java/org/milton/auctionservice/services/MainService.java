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
        User user = userService.getUserById(paymentRequest.getCustomerId()).get();
        Item item = itemService.getItemById(paymentRequest.getItemId()).get();
        AuctionOrder order = new AuctionOrder();
        order.setBuyer(user);
        order.setItem(item);
        if(paymentRequest.getShipping()){
            order.setTotalPrice(item.getCurrentPrice() + item.getShippingPrice());
        }
        else{
            order.setTotalPrice(item.getCurrentPrice());
        }

        System.out.println(order);

        return orderRepository.save(order);
    }
}
