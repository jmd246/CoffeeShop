package CoffeeShop.coffeeshop.dto;

public class ProductDTO {
    private String name,imgSrc;
    private double price;
    boolean available;
 
    public ProductDTO(String name, String imgSrc, double price,boolean available) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.price = price;
        this.available = available;
    }
    public ProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    
}
