package CoffeeShop.coffeeshop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.models.Coffee;
import CoffeeShop.coffeeshop.services.CoffeeService;

@RestController
@RequestMapping("coffee")
public class CoffeeController {

    private final CoffeeService service;
    public CoffeeController(CoffeeService service){
        this.service = service;
    }
    @GetMapping("/all")
    ResponseEntity<List<Coffee>> fetchAllCoffee(){
        return ResponseEntity.ok(service.fetchAllCoffee());
    }
    @PostMapping("/add")
    ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffee){
        return ResponseEntity.status(201).body(service.addCoffee(coffee));
    }
}
