package CoffeeShop.coffeeshop.models;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryId;
    @OneToOne
    @JoinColumn(name = "productId",unique = true)
    private Product product;
    private int quantity;
    private LocalDate dateModifed;
 
   
    public Inventory(){}
    public Inventory(Product product, int quantity){
        if(quantity < 0) quantity = 0;
        this.quantity = quantity;
        this.product = product;
        dateModifed = LocalDate.now();
    }
    public Product getProduct(){
        return product;
    }
    public String getDateModifed() {
        return dateModifed.toString();
    }
    public void setDateModifed(LocalDate dateModifed) {
        this.dateModifed = dateModifed;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public long getInventoryId(){
        return inventoryId;
    }
}
