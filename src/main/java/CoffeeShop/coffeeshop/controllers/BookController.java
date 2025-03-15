package CoffeeShop.coffeeshop.controllers;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<List<Book>> fetchBooks(){
        return ResponseEntity.ok(service.fetchBooks());
    }
    @GetMapping("/id/{id}")
    ResponseEntity<Book> fetchBookById(@PathVariable long id){
        Optional<Book> book = service.findById(id);
        if(book.isPresent()){
           ResponseEntity.ok(book.get());
        }
        throw new ResourceNotFoundException("");
    }
    @GetMapping("/title/{title}")
    ResponseEntity<Book> fetchBookByTitle(@PathVariable String title){
        Optional<Book> book = service.findByTitle(title);
        if(book.isEmpty()){
           throw new ResourceNotFoundException("Cant find book with title "+ title);
        }
        return ResponseEntity.ok(book.get());
    }
    @PostMapping("/add")
    ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.status(201).body(service.addBook(book));
    }
    @PutMapping("/update")
    ResponseEntity<Book> updateBook(@RequestBody Book updatedBook){
       //validate request
       if(updatedBook.getId()==null){
          throw new ResourceNotFoundException("missing id");  
       }
       return service.findById(updatedBook.getId()).map(book->{
            book.setAuthor(updatedBook.getAuthor());
            book.setAvailable(updatedBook.getAvailable());
            book.setIsbn(updatedBook.getIsbn());
            book.setTitle(updatedBook.getTitle());
            return ResponseEntity.ok(service.addBook(book));
        }).orElseThrow(()->new ResourceNotFoundException("Cant find book with id " + updatedBook.getId() ));
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteBook(@PathVariable long id){
        return ResponseEntity.ok(service.deleteBook(id));
    }
}
