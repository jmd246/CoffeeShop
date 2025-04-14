package CoffeeShop.coffeeshop.dto;

public class OrderUpdateRequest {
    private int newQuantity,quantityChange;
    private long orderId,productId;
    public OrderUpdateRequest(int newQuantity, int quantityChange, long orderId, long productId) {
        this.newQuantity = newQuantity;
        this.quantityChange = quantityChange;
        this.orderId = orderId;
        this.productId = productId;
    }
    public int getNewQuantity() {
        return newQuantity;
    }
    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
    public int getQuantityChange() {
        return quantityChange;
    }
    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
}