package CoffeeShop.coffeeshop.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.UserDTO;
import CoffeeShop.coffeeshop.exceptions.InvalidEntityException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.repositories.UserRepo;
import CoffeeShop.coffeeshop.util.PasswordUtil;

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
            PasswordUtil.hashPassword( user.getPassword())
        );
        return repo.save(newUser);
    }
    public User findByEmail(UserDTO loginInfo){
        return repo.findByEmail(loginInfo.getEmail());
    }
    public User findById(long id){
        Optional<User> user = repo.findById(id);
        if(user.isPresent()){
           return repo.findById(id).get();
        }
        throw new ResourceNotFoundException("Cant find user with id " + id);
    }
    public User authenticateUser(UserDTO loginInfo){
        //check input for missing values
        if(loginInfo.getEmail() == null || loginInfo.getPassword() == null)
        throw new InvalidEntityException("Missing email or password");
        //check if user in system has same email as what was inputed
        User authenticationInfo = repo.findByEmail(loginInfo.getEmail());
        if(authenticationInfo == null)
        throw new ResourceNotFoundException("No user with such email in system");
        //verify the password if match return the user
        if(PasswordUtil.verifyPassword(loginInfo.getPassword(),authenticationInfo.getPassword())){
            return authenticationInfo;
        }
        throw new ResourceNotFoundException("Invalid email or password");
    }

    public User update(long id, UserDTO updateInfo){
        Optional<User> user = repo.findById(id);
        if(user.isPresent()){
            user.get().setName(updateInfo.getName());
            user.get().setPassword(PasswordUtil.hashPassword(updateInfo.getPassword()));
            user.get().setEmail(updateInfo.getEmail());
            return repo.save(user.get());
        }
        throw new ResourceNotFoundException("User with id" + id + " not found");
    }
}
