package CoffeeShop.coffeeshop.controllers;



import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.UserDTO;
import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidEntityException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5500", exposedHeaders = {"userId", "username"})
@RequestMapping("user")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/register")
    public  ResponseEntity<User> register(@RequestBody  UserDTO user) {
        if(user.getEmail() == null || user.getPassword() == null || user.getName() == null){
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
        headers.add("userId", String.valueOf(loggedUser.getId()));
        headers.add("username", loggedUser.getName());
        headers.add("Access-Control-Expose-Headers", "userId, username");
        return ResponseEntity.ok().headers(headers).body(loggedUser);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> orders(@RequestHeader long userId){
        return ResponseEntity.ok(service.findById(userId).getOrders());
    }
    @PatchMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO updateInfo, @RequestHeader long userId){
      
        return ResponseEntity.ok(service.update(userId,updateInfo));
    }
    
}
