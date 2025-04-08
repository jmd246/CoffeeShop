package CoffeeShop.coffeeshop.controllers;



import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.UserDTO;
import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidEntityException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService service;
    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/register")
    public  ResponseEntity<User> register(@RequestBody  UserDTO user) {
        if(user.getEmail() == null || user.getPassword() == null || user.getName() == "null"){
            throw new InvalidEntityException("User info incomplete");
        }
        if(service.findByEmail(user)!=null) 
        throw new DuplicateNameException("duplicate user");
        else if(user.getName().length()< 4 || user.getName().length() >50)
        throw new InvalidNameException("invalid size");
        else if(user.getPassword().length()<4||user.getPassword().length()>25)
        throw new InvalidNameException("invalid size");
        else if(user.getEmail().length()<6||user.getEmail().length()>100)
        throw new InvalidNameException("invalid size");
        return  ResponseEntity.status(201).body(service.register(user));
    }
    @PostMapping("/login")
    public  ResponseEntity<User> login(@RequestBody  UserDTO user) {
        User loggedUser = service.authenticateUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", String.valueOf(loggedUser.getId()));
        return ResponseEntity.ok().headers(headers).body(loggedUser);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> orders(@RequestHeader long userId){
        return ResponseEntity.ok(service.findById(userId).getOrders());
    }
    @PatchMapping("update/{id}")
    ResponseEntity<User> updateUser(@RequestBody UserDTO updateInfo, @PathVariable long id){
      
        return ResponseEntity.ok(service.update(id,updateInfo));
    }
    
}
