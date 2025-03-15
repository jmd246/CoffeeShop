package CoffeeShop.coffeeshop.exceptions;

public class InvalidPriceException extends RuntimeException{
   public InvalidPriceException(String msg){
       super(msg);
   }
}
