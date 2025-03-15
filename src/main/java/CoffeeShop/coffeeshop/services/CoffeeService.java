package CoffeeShop.coffeeshop.services;

import java.util.List;


import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidPriceException;
import CoffeeShop.coffeeshop.models.Coffee;
import CoffeeShop.coffeeshop.repositories.CoffeeRepo;

@Service
public class CoffeeService {
    private final CoffeeRepo repo;
    public CoffeeService(CoffeeRepo repo){
        this.repo = repo;
    }
    public List<Coffee> fetchAllCoffee(){
        return repo.findAll();
    }
    public Coffee addCoffee(Coffee coffee){
        if(coffee.getName().length()< 4 || coffee.getName().length() > 25){
           throw new InvalidNameException("invalid name " + coffee.getName());
        }
        if(coffee.getPrice() <= 0){
            throw new InvalidPriceException("invalid price " + coffee.getPrice());

        }
        if(repo.findCoffeeByName(coffee.getName()).isPresent()){
           throw new DuplicateNameException("Coffee present with same name");
        }
        return repo.save(coffee);
    }
}
