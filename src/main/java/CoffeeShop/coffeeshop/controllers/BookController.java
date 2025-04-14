package CoffeeShop.coffeeshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoffeeShop.coffeeshop.dto.BookDTO;
import CoffeeShop.coffeeshop.exceptions.ResourceNotFoundException;
import CoffeeShop.coffeeshop.models.Book;
import CoffeeShop.coffeeshop.services.BookService;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService service;
    public BookController(BookService service){
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Book>> fetchBooks(){
        return ResponseEntity.ok(service.fetchBooks());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Book> fetchBookById(@PathVariable long id){
        Optional<Book> book = service.findById(id);
        if(book.isPresent()){
           ResponseEntity.ok(book.get());
        }
        throw new ResourceNotFoundException("");
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> fetchBookByTitle(@PathVariable String title){
        Optional<Book> book = service.findByTitle(title);
        if(book.isEmpty()){
           throw new ResourceNotFoundException("Cant find book with title "+ title);
        }
        return ResponseEntity.ok(book.get());
    }
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.status(201).body(service.addBook(book));
    }
    @PostMapping("/addlist")
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book> books){
        return ResponseEntity.status(201).body(service.addBooks(books));
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long  id,@RequestBody BookDTO updatedBook){
       return service.findById(id).map(book->{
            book.setAvailable(updatedBook.isAvailable());
            if(updatedBook.getAuthor().length()>4){
                book.setAuthor(updatedBook.getAuthor());
            }
            if(updatedBook.getIsbn().length()>5){
                book.setIsbn(updatedBook.getIsbn());
            }
            if(updatedBook.getTitle().length()>4){
                book.setName(updatedBook.getTitle());
            }
            return ResponseEntity.ok(service.addBook(book));
        }).orElseThrow(()->new ResourceNotFoundException("Cant find book with id " + id ));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id){
        return ResponseEntity.ok(service.deleteBook(id));
    }
}
