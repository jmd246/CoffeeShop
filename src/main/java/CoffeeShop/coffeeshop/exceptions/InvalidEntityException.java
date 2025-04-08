package CoffeeShop.coffeeshop.exceptions;

public class InvalidEntityException extends RuntimeException{
   public InvalidEntityException(String msg){
       super(msg);
   }
}
