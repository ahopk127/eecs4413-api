package org.milton.auctionservice.models;

import jakarta.persistence.*;

@Entity
public class AuctionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Double TotalPrice;

    public AuctionOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }
}
