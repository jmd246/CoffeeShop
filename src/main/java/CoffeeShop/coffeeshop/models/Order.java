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
import jakarta.persistence.OneToOne;
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
    private int quantity;
  
    @OneToOne
    @JoinColumn(name = "productId",unique = true)
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    public Order(User user, Product product, int quantity){
        this.product = product;
        this.user = user;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
        this.quantity = quantity;
    }
    public Order(){
        
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        if(quantity <= 0 ) quantity = 1;
        else{
           quantity = this.quantity;
        }
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
    public Product getProduct(){
        return product;
    }

  

}
