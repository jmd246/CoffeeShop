package CoffeeShop.coffeeshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CoffeeShop.coffeeshop.dto.BookDTO;
import CoffeeShop.coffeeshop.exceptions.DuplicateNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidNameException;
import CoffeeShop.coffeeshop.exceptions.InvalidPriceException;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Book;
import CoffeeShop.coffeeshop.models.Inventory;
import CoffeeShop.coffeeshop.repositories.BookRepo;
import CoffeeShop.coffeeshop.repositories.InventoryRepo;

@Service
public class BookService {
    private final BookRepo bookRepo;
    private final InventoryRepo inventoryRepo;
    public BookService(BookRepo bookRepo,InventoryRepo inventoryRepo){
        this.bookRepo = bookRepo;
        this.inventoryRepo = inventoryRepo;
    }

    public Book addBook(BookDTO book){
        if(book.getAuthor()==null || book.getIsbn() == null || book.getName() == null){
            throw new InvalidNameException("invalid book entry");
        }
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
        Book persistedBook = bookRepo.save(
            new Book(book.getName(),
            book.getImgSrc(),
            book.getAuthor(),
            book.getIsbn(),
            book.getPrice(),
            book.isAvailable()));
        inventoryRepo.save(new Inventory(persistedBook,10));
        return persistedBook;
    }
    public List<Book> addBooks(List<BookDTO> books){
        List<Book> persistedBooks = new ArrayList<>();
        for(BookDTO book : books){
            persistedBooks.add(addBook(book));
        }
        return persistedBooks;
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
        inventoryRepo.deleteByProductName(bookRepo.findById(id).get());
        bookRepo.deleteById(id);
        return "Deleted book with id " + id + " succesffully"; 
    }

}
