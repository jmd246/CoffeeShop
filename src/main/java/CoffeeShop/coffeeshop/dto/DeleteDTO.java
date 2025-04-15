package CoffeeShop.coffeeshop.dto;

public class DeleteDTO {
    private long productId,orderId;

    public DeleteDTO(long productId, long orderId) {
        this.productId = productId;
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public long getOrderId() {
        return orderId;
    }

}
