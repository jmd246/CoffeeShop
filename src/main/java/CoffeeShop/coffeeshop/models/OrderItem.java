package CoffeeShop.coffeeshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;


    private int quantity;
    


 

    public OrderItem(int quantity,Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public OrderItem() {
    }
   
    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public long getId(){
        return id;
    }
}
