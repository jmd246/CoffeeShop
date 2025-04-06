package CoffeeShop.coffeeshop.models;
import java.time.LocalDate;
import java.util.List;
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

    private enum OrderStatus{
        PENDING,DELIVERED,COMPLETED 
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private double total,subTotal;
  
    @OneToMany
    private List<Product> cart;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    public Order(User user, List<Product> products, LocalDate orderDate,LocalDate deliveryDate, OrderStatus status){
        this.cart = products;
        this.user = user;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
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

   
    public double calculateTaxes(){
        return  subTotal    *   0.07;
    }
    public double calculateSubTotal(){
        return cart.stream().mapToDouble(Purchaseable::getPrice).sum();
    }

}
