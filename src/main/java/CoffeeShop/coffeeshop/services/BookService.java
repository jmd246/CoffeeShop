package CoffeeShop.coffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidPriceException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Book;
import CoffeeShop.coffeeshop.repositories.BookRepo;

@Service
public class BookService {
    private final BookRepo bookRepo;
    public BookService(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    public Book addBook(Book book){
        if(book.getIsbn().length()< 8 ||  book.getIsbn().length()>32){
            throw new InvalidNameException("invalid isbn");
        }
        if(book.getAuthor().length()< 4 ||  book.getAuthor().length()>32){
            throw new InvalidNameException("invalid Author");
        }
        if(book.getName().length() < 4 || book.getName().length() > 32){
           throw new InvalidNameException("invalid name " + book.getName());
        }
        if(book.getPrice() <= 0){
            throw new InvalidPriceException("invalid price " + book.getPrice());
        }
        if(bookRepo.findByName(book.getName()).isPresent()){
           throw new DuplicateNameException("Coffee present with same name");
        }
        return bookRepo.save(book);
    }
    public List<Book> fetchBooks(){
        return bookRepo.findAll();
    }
    public Optional<Book> findById(long id){  
        return bookRepo.findById(id);  
    }
    public Optional<Book> findByTitle(String title){
        return bookRepo.findByName(title);
    }
    public String deleteBook(long id){
        if(bookRepo.findById(id).isEmpty()) throw new ResourceNotFoundException("Cant find book to delete");
        bookRepo.deleteById(id);
        return "Deleted book with id " + id + " succesffully"; 
    }

}
