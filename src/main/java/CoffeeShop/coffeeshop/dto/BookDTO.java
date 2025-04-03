package CoffeeShop.coffeeshop.dto;

public class BookDTO{
  private String title,author,isbn;
  private boolean available;
  private double price;





public BookDTO(String title, String author, String isbn,boolean available, double price) {
    this.title = title;
    this.author = author;                          
    this.isbn = isbn;
    this.available = available;
    this.price = price;
}
public double getPrice() {
    return price;
}
  public void setPrice(double price) {
    this.price = price;
  }
public boolean isAvailable() {
    return available;
}

public void setAvailable(boolean available) {
    this.available = available;
}
public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getAuthor() {
    return author;
}

public void setAuthor(String author) {
    this.author = author;
}

public String getIsbn() {
    return isbn;
}

public void setIsbn(String isbn) {
    this.isbn = isbn;
}  
}