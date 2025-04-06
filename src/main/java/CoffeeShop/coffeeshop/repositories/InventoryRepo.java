package CoffeeShop.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.models.Product;
@Repository
public interface InventoryRepo extends JpaRepository <Inventory,Long>{
    boolean deleteByProductName(Product product);   
}