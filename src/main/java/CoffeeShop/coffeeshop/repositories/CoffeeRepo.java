package CoffeeShop.coffeeshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoffeeShop.coffeeshop.models.Coffee;
@Repository
public interface CoffeeRepo extends JpaRepository<Coffee,Long>{
    public Optional<Coffee> findCoffeeByName(String name);
}
