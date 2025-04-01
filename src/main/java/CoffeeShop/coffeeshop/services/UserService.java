package CoffeeShop.coffeeshop.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.UserDTO;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.repositories.UserRepo;

@Service
public class UserService {
    private final UserRepo  repo;
    public UserService(UserRepo repo) {
        this.repo = repo;
    }
    public User register(UserDTO user){
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
    public User update(long id, UserDTO updateInfo){
        Optional<User> user = repo.findById(id);
        if(user.isPresent()){
            user.get().setName(updateInfo.getName());
            user.get().setPassword(updateInfo.getPassword());
            user.get().setEmail(updateInfo.getEmail());
            return repo.save(user.get());
        }
        throw new ResourceNotFoundException("User with id" + id + " not found");
    }
}
