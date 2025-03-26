package org.milton.auctionservice.controller;

import org.milton.auctionservice.models.Item;
import org.milton.auctionservice.models.AuctionOrder;
import org.milton.auctionservice.request.BiddingRequest;
import org.milton.auctionservice.request.PaymentRequest;
import org.milton.auctionservice.services.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main")
@CrossOrigin(origins = "*") // Allow all origins

public class MainController {


    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }


    @PostMapping("/bid")
    public ResponseEntity<Item> createBid(@RequestBody BiddingRequest request) {
        return ResponseEntity.ok(mainService.createBid(request));
    }

    @PostMapping("/pay_now")
    public ResponseEntity<AuctionOrder> payNow(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(mainService.createOrder(request));
    }
}
