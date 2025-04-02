package org.milton.auctionservice.models; //package declaration - indicating that this file belongs to the models layer of the application

import jakarta.persistence.*; // importing JPA (Java Persistence API) annotations from java persistence - used to map this class to a database table and handles persistence
import java.sql.Timestamp; //importing Timestamp - used to represent a specific point in time, used for date-time fields

@Entity //marks this class as a JPA entity, meaning it will be mapped to a database table
public class Item { //declares the class Item - representing an item in the auction system

    @Id //marks this field as the primary key of the entity
    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO allows the database provider to pick the best stratefy for generating IDs
    //specifies that the database should automatically generate values for id
    private Long id;

    private String name; //stores the name of the item
    private String description; //stores a brief description of the item

    @Enumerated(EnumType.STRING) //specifies that the auctionType (which is an enum) should be stored in the database as a string instead of an integer
    private AuctionType auctionType; //represents the type of auction (e.g., Forward, Dutch.)
    private Timestamp duration; //represents how long the auction will last
    private Timestamp arrival; //represents the arrival time of the item
    private Double startingPrice; // the initial price of the item when the auction starts
    private Double currentPrice; // the current highest bid price for the item

    @Enumerated(EnumType.STRING) //specifies that the status (which is an enum) should be stored in the database as a string instead of an integer
    private ItemStatus status; //represents the current status of the item (e.g., Available, Sold, etc.)

    private Double shippingPrice; //the standard shipping price for the item
    private Double expeditedShippingPrice; //the cost of expedited shipping for the item

    @ManyToOne //represents a many-to-one relationship - multiple items can be owned by a single owner
    @JoinColumn(name = "owner_id") //entity's owner is mapped using a foreign key column named owner_id in the database
    private User owner; //user who owns the auction item

    @ManyToOne //represents a many-to-one relationship - multiple items can be bid on by a single bidder
    @JoinColumn(name = "bidder_id") //specifies that the entity's highestBidder is mapped using a foreign key column named bidder_id in the database
    private User highestBidder; //the user who placed the highest bid on the item

    public Long getId() {
        return id; //gets the item ID - primary key
    }

    public void setId(Long id) {
        this.id = id; //sets the item ID
    }

    public String getName() {
        return name; //gets the name of the item
    }

    public void setName(String name) {
        this.name = name; //sets the name of the item
    }

    public String getDescription() {
        return description; //gets the description of the item
    }

    public void setDescription(String description) {
        this.description = description; //sets the description of the item
    }

    public AuctionType getAuctionType() {
        return auctionType; //gets the auction type of the item
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType; //sets the auction type of the item
    }

    public Timestamp getDuration() {
        return duration; //gets the duration of the auction
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration; //sets the duration of the auction
    }

    public Double getStartingPrice() {
        return startingPrice; //gets the starting price of the item
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice; //sets the starting price of the item
    }

    public Double getCurrentPrice() {
        return currentPrice; //gets the current price of the item
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice; //sets the current price of the item
    }

    public ItemStatus getStatus() {
        return status; //gets the status of the item
    }

    public void setStatus(ItemStatus status) {
        this.status = status; //sets the status of the item
    }

    public User getHighestBidder() {
        return highestBidder; //gets the user who placed the highest bid on the item
    }

    public void setHighestBidder(User highestBidder) {
        this.highestBidder = highestBidder; //sets the user who placed the highest bid on the item
    }

    public User getOwner() {
        return owner; //gets the user who owns the auction item
    }

    public void setOwner(User owner) {
        this.owner = owner; //sets the user who owns the auction item
    }

    public Double getShippingPrice() {
        return shippingPrice; //gets the standard shipping price for the item
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice; //sets the standard shipping price for the item
    }

    public Double getExpeditedShippingPrice() {
        return expeditedShippingPrice; //gets the expedited shipping price for the item
    }

    public void setExpeditedShippingPrice(Double expeditedShippingPrice) {
        this.expeditedShippingPrice = expeditedShippingPrice; //sets the expedited shipping price for the item
    }

    public Timestamp getArrival() {
        return arrival; //gets the arrival time of the item
    }

    public void setArrival() {
        this.arrival = arrival; //sets the arrival time of the item
    }
}
