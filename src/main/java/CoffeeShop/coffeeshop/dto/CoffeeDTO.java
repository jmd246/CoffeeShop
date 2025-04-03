package CoffeeShop.coffeeshop.dto;

public class CoffeeDTO {
   private boolean isCold,isAvailable;
   
    private String name;
    private double price;
    public CoffeeDTO(boolean isCold, boolean  isAvailable,String name, double price) {
        this.isCold = isCold;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public boolean isCold() {
        return isCold;
    }
    public void setCold(boolean cold) {
        this.isCold = cold;
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
