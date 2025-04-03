package CoffeeShop.coffeeshop.models;


import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books" )
public class Book  extends  Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String isbn;
    
 
    public Book(){}
    public Book(String name, String author, String isbn,double price ,boolean available) {
        super(name,price,available);
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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
