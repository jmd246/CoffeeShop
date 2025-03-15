package CoffeeShop.coffeeshop.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CoffeeShop.coffeeshop.models.Book;
@Repository
public interface BookRepo extends JpaRepository<Book,Long>{
   public Optional<Book> findByTitle(String title);
}