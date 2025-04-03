package CoffeeShop.coffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.repositories.OrderRepo;

@Service

public class OrderService {
    private OrderRepo repo;
    public OrderService(OrderRepo repo){
        this.repo = repo;
    }
    public Order getOrder(long id){
        Optional<Order> order = repo.findById(id);
        if(order.isPresent()) return  order.get();
        else{
             throw new ResourceNotFoundException("order with specified id not found");
        }
    }
    public List<Order> getOrders(List<Long> id){
        List<Order> orders = repo.findAllById(id);
        if(!orders.isEmpty()) return  orders;
        else{
             throw new ResourceNotFoundException("order with specified id not found");
        }
    }
}
