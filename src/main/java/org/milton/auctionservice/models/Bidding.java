package org.milton.auctionservice.models;

import jakarta.persistence.*;

@Entity
public class Bidding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Double bidingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getBidingPrice() {
        return bidingPrice;
    }

    public void setBidingPrice(Double bidingPrice) {
        this.bidingPrice = bidingPrice;
    }
}
