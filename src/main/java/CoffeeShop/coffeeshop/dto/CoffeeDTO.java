package CoffeeShop.coffeeshop.dto;

public class CoffeeDTO extends ProductDTO{
   private boolean isCold;
   
  
    public CoffeeDTO(boolean isCold, boolean  isAvailable,String name, String imgSrc,double price) {
        super(name,imgSrc ,price, isAvailable);
        this.isCold = isCold;
    }

    public boolean isCold() {
        return isCold;
    }
    public void setCold(boolean cold) {
        this.isCold = cold;
    }
    
}
