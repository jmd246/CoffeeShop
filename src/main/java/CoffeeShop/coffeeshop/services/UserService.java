package CoffeeShop.coffeeshop.services;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.UserDTO;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.repositories.UserRepo;

@Service
public class UserService {
    private final UserRepo  repo;
    public UserService(UserRepo repo) {
        this.repo = repo;
    }
    public User save(UserDTO user){
        User newUser = new User(
            user.getName(),
            user.getEmail(),
            user.getPassword()
        );
        return repo.save(newUser);
    }
    public User findByEmailAndPassword(UserDTO user){
        return  repo.findByEmailAndPassword(user.getEmail(),user.getPassword());
    }
    
    
}
