package CoffeeShop.coffeeshop.dto;

public class InventoryDTO {
    private int quantity;

    public InventoryDTO(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
