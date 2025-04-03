package CoffeeShop.coffeeshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coffee")
public class Coffee extends Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    public Coffee(String name, double price,boolean isAvailable,boolean isCold) {
        super(name,price, isAvailable);
        this.isCold = isCold;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override public String toString(){  
        return  "Coffee: " + this.getName();
    }
    
}
