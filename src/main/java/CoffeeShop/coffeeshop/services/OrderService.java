package CoffeeShop.coffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.models.Product;
import CoffeeShop.coffeeshop.models.User;
import CoffeeShop.coffeeshop.repositories.InventoryRepo;
import CoffeeShop.coffeeshop.repositories.OrderRepo;
import CoffeeShop.coffeeshop.repositories.UserRepo;

@Service

public class OrderService {
    private OrderRepo orderRepo;
    private InventoryRepo inventoryRepo;
    private UserRepo userRepo;
    public OrderService(OrderRepo orderRepo,InventoryRepo inventoryRepo, UserRepo userRepo){
        this.orderRepo = orderRepo;
        this.inventoryRepo = inventoryRepo;
        this.userRepo = userRepo;
    }
    public Order getOrder(long id){
        Optional<Order> order = orderRepo.findById(id);
        if(order.isPresent()) return  order.get();
        else{
             throw new ResourceNotFoundException("order with specified id not found");
        }
    }
    public Order getOrderByUserName(long userId){
        return orderRepo.findOrderByUserId(userId);
    }
    public List<Order> getOrders(long userId){
        List<Order> orders = orderRepo.findAllOrdersByUserId(userId);
        if(!orders.isEmpty()) return  orders;
        else{
             throw new ResourceNotFoundException("order with specified id not found");
        }
    }
    
    public Order createOrder(long userId,long productId, int quantity){
        Optional<User> user = userRepo.findById(userId);
        Product product = inventoryRepo.findByProductId(productId).getProduct();
        if(user.isPresent() && inventoryRepo.findByProductName(product.getName()).getQuantity() > quantity){
            Order order = new Order(user.get(),product,quantity);
            return orderRepo.save(order);
        }
        throw new ResourceNotFoundException("cant find user or product");
    }

}
