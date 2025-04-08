package CoffeeShop.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoffeeShop.coffeeshop.models.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    public User findByEmail(String email);
}
