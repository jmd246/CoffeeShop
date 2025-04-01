package CoffeeShop.coffeeshop.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String  email;
    private String password;

    
  
    //private List<Book> readingList =  new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    public List<Order> getOrders() {
        return orders;
    }
    public User(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password=password;
    }
    public User() {
    }
    public String getPassword() {
        return password;
    }
    public long getId(){
        return userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override public String toString(){
        return "User: " + name + " email " + email ;
    }
 
}
