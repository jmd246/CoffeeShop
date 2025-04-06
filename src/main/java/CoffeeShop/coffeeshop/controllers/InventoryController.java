package CoffeeShop.coffeeshop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.InventoryDTO;
import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.services.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service){
        this.service = service;
    }
    @GetMapping("")
    ResponseEntity<List<Inventory>> fetchInventory(){
       return ResponseEntity.ok(service.getInventory());
    }
    @PatchMapping("/{id}")
    ResponseEntity<Boolean> updateQuantity(@PathVariable long id, @RequestBody InventoryDTO update){
        boolean updateSuccessfully = service.setQuantity(id, update.getQuantity());
        return ResponseEntity.ok(updateSuccessfully);
    } 
}
