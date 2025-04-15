package CoffeeShop.coffeeshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.CoffeeDTO;
import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidPriceException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Book;
import CoffeeShop.coffeeshop.models.Coffee;
import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.repositories.CoffeeRepo;
import CoffeeShop.coffeeshop.repositories.InventoryRepo;

@Service
public class CoffeeService {
    private final CoffeeRepo repo;
    private final InventoryRepo inventoryRepo;
    public CoffeeService(CoffeeRepo repo,InventoryRepo inventoryRepo){
        this.repo = repo;
        this.inventoryRepo = inventoryRepo;
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
    public Coffee addCoffee(CoffeeDTO coffee){
        if(coffee.getName().length()< 4 || coffee.getName().length() > 25){
           throw new InvalidNameException("invalid name " + coffee.getName());
        }
        if(coffee.getPrice() <= 0){
            throw new InvalidPriceException("invalid price " + coffee.getPrice());
        }
        Optional<Coffee> existingCoffee = repo.findByName(coffee.getName());
        if(existingCoffee.isPresent()){
           throw new DuplicateNameException("Coffee present with same name");
        }
        Coffee persistedCoffee = repo.save(new Coffee(
            coffee.getName(),coffee.getImgSrc(),coffee.getPrice(),coffee.isAvailable(),coffee.isCold()
        ));
        inventoryRepo.save(new Inventory(persistedCoffee,10));
        return persistedCoffee;
    }
    public List<Coffee> addCoffees(List<CoffeeDTO> coffees){
        List<Coffee> persistedCoffees = new ArrayList<>();
        for(CoffeeDTO coffee : coffees){
            persistedCoffees.add(addCoffee(coffee));
        }
        return persistedCoffees;
    }
    public Coffee updateCoffee(Long id, CoffeeDTO coffeeUpdate){
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
            inventoryRepo.deleteByProductName(coffee.get());
            repo.delete(coffee.get());
            return "deleted coffee"+ coffee.get().toString() +" succesfully";
        }
        throw new ResourceNotFoundException("failed to find coffee");
    }
  
}
