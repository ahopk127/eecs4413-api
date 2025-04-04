package org.milton.auctionservice.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType;
    private Timestamp duration;
    private Timestamp arrival;
    private Double startingPrice;
    private Double currentPrice;
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private Double shippingPrice;
    private Double expeditedShippingPrice;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User highestBidder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public User getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(User highestBidder) {
        this.highestBidder = highestBidder;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getExpeditedShippingPrice() {
        return expeditedShippingPrice;
    }

    public void setExpeditedShippingPrice(Double expeditedShippingPrice) {
        this.expeditedShippingPrice = expeditedShippingPrice;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public void setArrival() {
        this.arrival = arrival;
    }
}
