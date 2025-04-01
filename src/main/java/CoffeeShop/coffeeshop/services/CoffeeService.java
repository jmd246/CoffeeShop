package CoffeeShop.coffeeshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidPriceException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
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
    public  List<Coffee> fetchAllCoffeeByTemp(boolean isCold){
        List<Coffee> coffees =  new ArrayList<>();
        repo.findAll().forEach(coffee->{
            if(coffee.isCold()==isCold){
                coffees.add(coffee);
            } 
        });
        return coffees;
    }
    public Coffee getById(Long id){
        Optional<Coffee> coffee =  repo.findById(id);
        if(coffee.isPresent())return coffee.get();
        throw new ResourceNotFoundException("nothing to show here");
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
    public Coffee updateCoffee(Long id, Coffee coffeeUpdate){
       Optional<Coffee> coffee = repo.findById(id);
       if(coffee.isPresent()){
          coffee.get().setName(coffeeUpdate.getName());
          coffee.get().setPrice(coffeeUpdate.getPrice());
          coffee.get().setCold(coffeeUpdate.isCold());
          return repo.save(coffee.get());
       }
       return null;
    }
    public String delete(long id){
        Optional<Coffee> coffee = repo.findById(id);
        if(coffee.isPresent()){
            repo.delete(coffee.get());
            return "deleted coffee"+ coffee.get().toString() +"succesfully";
        }
        throw new ResourceNotFoundException("failed to find coffee");
    }
  
}
