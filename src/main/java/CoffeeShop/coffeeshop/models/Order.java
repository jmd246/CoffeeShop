package CoffeeShop.coffeeshop.models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "user_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private double total,subTotal;
  
    @OneToMany
    private List<Book> bookCart;
    @OneToMany
    private List<Coffee> coffeeCart;

    
    public Order(long userId){
        total=subTotal=0;
        coffeeCart = new ArrayList<>();
        bookCart = new ArrayList<>();
    }
    public long getOrderId() {
        return orderId;
    }
  
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addToCoffeeCart(Coffee product){
        coffeeCart.add(product);
    }
    public void addToBookCart(Book product){
        bookCart.add(product);
    }
    public double calculateTaxes(){
        return  subTotal    *   0.07;
    }
    public double calculateCoffeeSubTotal(){
        return coffeeCart.stream().mapToDouble(Purchaseable::getPrice).sum();
    }
    public double calculateBookSubTotal(){
        return bookCart.stream().mapToDouble(Purchaseable::getPrice).sum();
    }

}
