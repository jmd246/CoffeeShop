package CoffeeShop.coffeeshop.models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    public enum OrderStatus{
        PENDING,DELIVERED,COMPLETED 
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

  
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    List<OrderItem> items = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    public Order(User user, OrderItem product){
        items.add(product);
        this.user = user;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
    }
    public Order(){
        
    }
 
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    private LocalDate orderDate, deliveryDate;


    
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
   
    public long getOrderId() {
        return orderId;
    }
  
    public double getSubTotal() {
        double subTotal = 0;
        for(OrderItem item : items){
            subTotal += item.getQuantity() * item.getProduct().getPrice();
        }
        return subTotal;
    }

    public double getTotal() {
        return this.getSubTotal() * 1.07 ; 
    }

    
    public List<OrderItem> getOrderItems(){
        return items;
    }

  

}
