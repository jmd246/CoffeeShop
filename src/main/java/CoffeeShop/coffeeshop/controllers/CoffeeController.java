package CoffeeShop.coffeeshop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.CoffeeDTO;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Coffee;
import CoffeeShop.coffeeshop.services.CoffeeService;

@RestController
@RequestMapping("coffee")
public class CoffeeController {

    private final CoffeeService service;
    public CoffeeController(CoffeeService service){
        this.service = service;
    }
    @GetMapping("id/{id}")
    ResponseEntity<Coffee> fetchCoffee(@PathVariable long id){
        return  ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/all")
    ResponseEntity<List<Coffee>> fetchAllCoffee(){
        return ResponseEntity.ok(service.fetchAllCoffee());
    }
    @GetMapping("/{temp}/all")
    ResponseEntity<List<Coffee>> getCoffeesByTemp(@PathVariable String temp ){
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
    @PostMapping("/add")
    ResponseEntity<Coffee> addCoffee(@RequestBody CoffeeDTO coffee){
        Coffee newCoffee =  new Coffee(
            coffee.getName(),
            coffee.getPrice(),
            coffee.isCold()
        );
        return ResponseEntity.status(201).body(service.addCoffee(newCoffee));
    }
}
