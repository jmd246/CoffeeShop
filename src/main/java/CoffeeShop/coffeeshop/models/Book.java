package CoffeeShop.coffeeshop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books" )
public class Book  extends  Product{
  
    private String author;
    private String isbn;
    
 
    public Book(){}
    public Book(String name, String imgSrc,String author, String isbn,double price ,boolean available) {
        super(name,imgSrc,price,available);
        this.author = author;
        this.isbn = isbn;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
   
    //id title author isbn available
   
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
   
    @Override public String toString(){
        return "Book titled: " + this.getName();
    }
}
