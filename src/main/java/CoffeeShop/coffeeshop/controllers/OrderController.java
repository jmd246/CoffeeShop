package CoffeeShop.coffeeshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.OrderDTO;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.services.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
    private OrderService service;
    public OrderController(OrderService service){
        this.service = service;
    }
    @PostMapping("/{productId}")
    public ResponseEntity<Order> postOrder(@PathVariable long productId, @RequestHeader long userId , @RequestBody OrderDTO orderInfo){
        return ResponseEntity.status(201).body(service.createOrder(userId, productId, orderInfo.getQuantity()));
    }
}
