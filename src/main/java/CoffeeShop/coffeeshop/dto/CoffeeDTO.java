package CoffeeShop.coffeeshop.dto;

public class CoffeeDTO {
   private boolean cold;
    private String name;
    private double price;
    public CoffeeDTO(boolean cold, String name, double price) {
        this.cold = cold;
        this.name = name;
        this.price = price;
    }
    public boolean isCold() {
        return cold;
    }
    public void setCold(boolean cold) {
        this.cold = cold;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
}
