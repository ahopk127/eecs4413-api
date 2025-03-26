package org.milton.auctionservice.request;

public class PaymentRequest {
    private Long customerId;
    private Long itemId;
    private Boolean isShipping;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
