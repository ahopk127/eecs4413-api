package org.milton.auctionservice.controller; // package declaration - indicating that this file belongs to the controller layer of the application 

import org.milton.auctionservice.models.Item; // importing Item class - model representing an item in the auction
import org.milton.auctionservice.models.AuctionOrder; // importing AuctionOrder class - model representing an auction order
import org.milton.auctionservice.request.BiddingRequest; // importing BiddingRequest class - request model for handling bidding
import org.milton.auctionservice.request.PaymentRequest; // importing PaymentRequest class - request model for handling payment
import org.milton.auctionservice.services.MainService; // importing MainService class - service layer for business logic
import org.springframework.http.ResponseEntity; // importing ResponseEntity class - Spring class used to structure HTTP responses
import org.springframework.web.bind.annotation.*; // importing RestController, RequestMapping, PostMapping, CrossOrigin and RequestBody - Spring annotations for creating RESTful APIs
//Spring annotations for handling web requests

@RestController // Marks this class as a REST API controller, meaning it handles HTTP requests and returns JSON responses.
@RequestMapping("/api/main") // all endpoints in this controller will start with /api/main
@CrossOrigin(origins = "*") // Allow requests from all origins (useful for frontend-backend communication when hosted on different domains)

public class MainController {

    /*
    * This class handles HTTP requests related to bidding and payments
    * Delegates business logic to the MainService class
    * Accepts JSON requests and returns JSON responses
    * Uses dependency injection to access MainService 
    * Supports CORS for cross-origin resource sharing for frontend communication
    */

    private final MainService mainService;

    public MainController(MainService mainService) { //Maincontroller depends on MainService to handle business logic
        this.mainService = mainService; //The constructor injects the MainService when an instance of MainController is created, following Spring's dependency injection pattern.


    @PostMapping("/bid") //defines a Post endpoint for creating a bid - at api/main/bid
    //@RequestBody BiddingRequest request: The request body should be a JSON object of type BiddingRequest, containing bid details.
    public ResponseEntity<Item> createBid(@RequestBody BiddingRequest request) {
        //wraos the response in ResponseEntity.ok - ensuring an HTTP 200 OK status. 
        return ResponseEntity.ok(mainService.createBid(request)); //calls mainService.createBid(request) to process the bid 
    }

    @PostMapping("/pay_now") //defines a Post endpoint for processing payment - at api/main/pay_now
    //@RequestBody PaymentRequest request: The request body should be a JSON object of type PaymentRequest, containing payment details.
    public ResponseEntity<AuctionOrder> payNow(@RequestBody PaymentRequest request) {
        //wraps the response in ResponseEntity.ok - returns the created AuctionOrder.
        return ResponseEntity.ok(mainService.createOrder(request)); //calls mainService.createOrder(request) to process the payment
    }
}

