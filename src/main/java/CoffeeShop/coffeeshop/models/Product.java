package CoffeeShop.coffeeshop.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // ✅ Creates separate tables for subclasses

public abstract class Product implements  Purchaseable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    
    private double price;
    private String name;
    private boolean isAvailable;

    
    
    public Product() {
    }
    public Product(String name, double price, boolean isAvailable) {
        this.price = price;
        this.name = name;
        this.isAvailable = isAvailable;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }    
}
