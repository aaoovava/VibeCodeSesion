package application.port;

import domain.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TOGAF: Application Architecture — Port (output interface)
// PRINCIPLE: The Application layer defines the interface,
// the Data layer implements it. This is the Dependency Inversion Principle (DIP).
public interface BookRepositoryPort {

    Optional<Book> findById(UUID id);

    List<Book> findAll();

    List<Book> findAvailable();

    Book save(Book book);

    void deleteById(UUID id);

    boolean existsByIsbn(String isbn);
}