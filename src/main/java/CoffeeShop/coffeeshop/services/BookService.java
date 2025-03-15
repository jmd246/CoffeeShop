package CoffeeShop.coffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
        return bookRepo.save(book);
    }
    public List<Book> fetchBooks(){
        return bookRepo.findAll();
    }
    public Optional<Book> findById(long id){  
        return bookRepo.findById(id);  
    }
    public Optional<Book> findByTitle(String title){
        return bookRepo.findByTitle(title);
    }
    public String deleteBook(long id){
        if(bookRepo.findById(id).isEmpty()) throw new ResourceNotFoundException("Cant find book to delete");
        bookRepo.deleteById(id);
        return "Deleted book with id " + id + " succesffully"; 
    }

}
