package CoffeeShop.coffeeshop.dto;

public class OrderDTO {
    private int quantity;
    public OrderDTO(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
