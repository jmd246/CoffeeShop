package CoffeeShop.coffeeshop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.CoffeeDTO;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Book;
import CoffeeShop.coffeeshop.models.Coffee;
import CoffeeShop.coffeeshop.services.CoffeeService;

@RestController
@CrossOrigin(origins = "http://localhost:5500", exposedHeaders = "userId")
@RequestMapping("coffee")
public class CoffeeController {

    private final CoffeeService service;
    public CoffeeController(CoffeeService service){
        this.service = service;
    }
    //fetching data mappings
    @GetMapping("id/{id}")
    public ResponseEntity<Coffee> fetchCoffee(@PathVariable long id){
        return  ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Coffee>> fetchAllCoffee(){
        return ResponseEntity.ok(service.fetchAllCoffee());
    }
    @GetMapping("/{temp}/all")
    public ResponseEntity<List<Coffee>> getCoffeesByTemp(@PathVariable String temp ){
        temp =  temp.toLowerCase();
        boolean isCold = false;
        if(temp.equals("cold")||temp.equals("ice")){
            isCold=true;
        }
        else if(temp.equals("hot")||temp.equals("warm")){
            isCold  =  false;
        }
        else{
            throw new ResourceNotFoundException("nothing in between");
        }
        return ResponseEntity.ok(service.fetchAllCoffeeByTemp(isCold));
    }
    // add coffees
    @PostMapping("/add")
    public ResponseEntity<Coffee> addCoffee(@RequestBody CoffeeDTO coffee){
        Coffee newCoffee =  new Coffee(
            coffee.getName(),
            coffee.getPrice(),
            coffee.isAvailable(),
            coffee.isCold()
        );
        return ResponseEntity.status(201).body(service.addCoffee(newCoffee));
    }
    @PostMapping("/addlist")
    public ResponseEntity<List<Coffee>> addCoffees(@RequestBody List<Coffee> coffees){
        return ResponseEntity.status(201).body(service.addCoffees(coffees));
    }
    //update
    @PatchMapping("/update/{id}")
    public ResponseEntity<Coffee> updateCoffee(@RequestBody CoffeeDTO coffee, @PathVariable long id){
        Coffee coffeeUpdate = new Coffee(
            coffee.getName(),
            coffee.getPrice(),
            coffee.isAvailable(),
            coffee.isCold()
        );
        return ResponseEntity.ok(service.updateCoffee(id,coffeeUpdate));
    }
    //delete
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCoffee(@PathVariable long id){
        return ResponseEntity.ok(service.delete(id));
    }
}
