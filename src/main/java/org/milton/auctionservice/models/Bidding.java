package org.milton.auctionservice.models; //package declaration - indicating that this file belongs to the models layer of the application

import jakarta.persistence.*; // importing JPA (Java Persistence API) annotations from java persistence - used to map this class to a database table

@Entity //marks this class as a JPA entity, meaning it will be mapped to a database table

/*
* this class defines a bidding entity linked to a user/bidder and an item
* uses JPA annotations to map class to a relational database
* stores the bid amount in biddingPrice
* getter and setter methods allow controlled access
*/
public class Bidding { //declares the class Bidding - representing a bid in the auction system

    @Id //marks this field as the primary key of the entity
    @GeneratedValue(strategy = GenerationType.AUTO) //automatically generates unique IDs for each bid, allowing the database to decide the generation strategy
    private Long  id; //stores the unique identifier for each bid

    @ManyToOne //represents a many-to-one relationship - one user can place many bids
    @JoinColumn(name = "bidder_id") //specifies that the bidder_id column in the table will store the foreign key linking to User
    private User bidder; //Stores the user who placed the bid

    @ManyToOne //represents a many-to-one relationship - a single item can receive many bids
    @JoinColumn(name = "item_id") //links the bidding.item_id column to the Item entity's primary key
    private Item item; //Stores the item being bid on

    private Double bidingPrice; //Stores the bid amount placed by the user

    public Long getId() {
        return id; //returns the bid ID - primary key
    }

    public void setId(Long id) {
        this.id = id; //allows setting the bid ID
    }

    public User getBidder() {
        return bidder; //returns the user who placed the bid
    }

    public void setBidder(User bidder) {
        this.bidder = bidder; //sets the bidder information
    }

    public Item getItem() {
        return item; //returns the item being bid on
    }

    public void setItem(Item item) {
        this.item = item; //sets the item being bid on
    }

    public Double getBidingPrice() {
        return bidingPrice; //returns the bid amount
    }

    public void setBidingPrice(Double bidingPrice) {
        this.bidingPrice = bidingPrice; //sets the bid amount 
    }
}
