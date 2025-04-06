package CoffeeShop.coffeeshop.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.repositories.InventoryRepo;

@Service
public class InventoryService {
    private InventoryRepo repo;
    public InventoryService(InventoryRepo repo){
        this.repo = repo;
    }
    public List<Inventory> getInventory(){
        List<Inventory> inventoryList = repo.findAll();
        return inventoryList;
    }
    /* 
    public boolean setQuantity(long prodId, int quantity){
        repo.findById(null)
    }
    public boolean decrementQuantity(){}
    public boolean incrementQuantity(){}
    */
}
