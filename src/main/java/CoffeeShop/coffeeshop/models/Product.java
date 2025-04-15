package CoffeeShop.coffeeshop.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // ✅ Creates separate tables for subclasses

public abstract class Product implements  Purchaseable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double price;
    private String imgSrc;
   
    private String name;
    private boolean isAvailable;


    
    
    public Product() {
    }
    public Product(String name, String imgSrc,double price, boolean isAvailable) {
        this.price = price;
        this.name = name;
        this.isAvailable = isAvailable;
        this.imgSrc = imgSrc;
    }
    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
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
    public long getId(){
        return id;
    }    
}
