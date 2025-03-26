package org.milton.auctionservice.request;

public class PaymentRequest {
    private Long coustomerId;
    private Long itemId;
    private Boolean isShipping;

    public Long getCoustomerId() {
        return coustomerId;
    }

    public void setCoustomerId(Long coustomerId) {
        this.coustomerId = coustomerId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Boolean getShipping() {
        return isShipping;
    }

    public void setShipping(Boolean shipping) {
        isShipping = shipping;
    }
}
