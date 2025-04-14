package CoffeeShop.coffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.OrderUpdateRequest;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.models.Order;
import CoffeeShop.coffeeshop.models.OrderItem;
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
        Inventory record = inventoryRepo.findByProductId(productId); 
        Product product = record.getProduct();
        if(user.isPresent() && inventoryRepo.findByProductName(product.getName()).getQuantity() >= quantity){
            OrderItem item = new OrderItem(quantity,product);
            Order order = null;
            for(Order userOrder : orderRepo.findAllOrdersByUserId(userId)){
                if(userOrder.getStatus() == Order.OrderStatus.PENDING){
                    order = userOrder;
                }
            }
            //order = orderRepo.findOrderByUserId(userId);
            if(order == null){
               order = new Order(user.get(),item);
               item.setOrder(order);
            }
            else{
                boolean orderHasItem = false;
                for(OrderItem inCart : order.getOrderItems()){
                    if(inCart.getProduct().equals(product)){
                        orderHasItem = true;
                        inCart.setQuantity(quantity + inCart.getQuantity());
                    }
                }
                if(!orderHasItem){
                   order.getOrderItems().add(item);
                   item.setOrder(order);
                }
            }
            //adjust inventory
            record.setQuantity(record.getQuantity() - quantity);
            inventoryRepo.save(record);
            return orderRepo.save(order);
        }
        throw new ResourceNotFoundException("cant find user or product");
    }

    public Order updateOrder(long userId, OrderUpdateRequest updateRequest) {
        Inventory record = inventoryRepo.findByProductId(updateRequest.getProductId());
        Optional<User> user = userRepo.findById(userId);
        Product product = record.getProduct();
        int quantityChange = updateRequest.getQuantityChange();
        System.out.println("Inventory before update: " + record.getQuantity());
        System.out.println("Quantity change: " + quantityChange);
        System.out.println("New quantity: " + updateRequest.getNewQuantity());
        System.out.println("Product ID: " + updateRequest.getProductId());
    
        if (user.isPresent() && (record.getQuantity() - quantityChange >= 0)) {
            Order order = orderRepo.findById(updateRequest.getOrderId())
                                   .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    
            for (OrderItem item : order.getOrderItems()) {
                if (item.getProduct().equals(product)) {
                    item.setQuantity(updateRequest.getNewQuantity());
                    record.setQuantity(record.getQuantity() - quantityChange);
                }
            }
    
            inventoryRepo.save(record);
            System.out.println("Inventory updated to: " + record.getQuantity());
            return orderRepo.save(order);
        }
    
        throw new ResourceNotFoundException("Can't find user or product, or inventory would go negative");
    }
    
}
