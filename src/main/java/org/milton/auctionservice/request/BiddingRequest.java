package org.milton.auctionservice.request;

public class BiddingRequest {
    private Long itemId;
    private Long userId;
    private double bidAmount;

    public BiddingRequest(Long itemId, Long userId, double bidAmount) {
        this.itemId = itemId;
        this.userId = userId;
        this.bidAmount = bidAmount;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
