package CoffeeShop.coffeeshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coffee")
public class Coffee extends Product {
   
    private boolean isCold;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public boolean isCold() {
        return isCold;
    }
    public void setCold(boolean isCold) {
        this.isCold = isCold;
    }
    public Coffee() {
    }
    public Coffee(String name,String imgSrc, double price,boolean isAvailable,boolean isCold) {
        super(name,imgSrc,price, isAvailable);
        this.isCold = isCold;
    }
  

    @Override public String toString(){  
        return  "Coffee: " + this.getName();
    }
    
}
