package CoffeeShop.coffeeshop.exceptions;

public class DuplicateNameException extends RuntimeException{
    public DuplicateNameException(String msg){
        super(msg);
    }
}
