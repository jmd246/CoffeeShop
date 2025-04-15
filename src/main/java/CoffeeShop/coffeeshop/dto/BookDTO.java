package CoffeeShop.coffeeshop.dto;

public class BookDTO extends ProductDTO{
  private String author,isbn;


public BookDTO(String title, String author, String imgSrc,String isbn,boolean available, double price) {
    super(title,imgSrc ,price,available);
    this.author = author;                          
    this.isbn = isbn;
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