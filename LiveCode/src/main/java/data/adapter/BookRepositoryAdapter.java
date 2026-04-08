package data.adapter;

import application.port.BookRepositoryPort;
import data.entity.BookEntity;
import data.maper.EntityMapper;
import data.repository.InMemoryDatabase;
import domain.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// TOGAF: Data Architecture — Adapter
// Implements the PORT from the application layer.
// The application layer does not know that this is InMemory — it could be PostgreSQL, MongoDB...
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final InMemoryDatabase db;
    private final EntityMapper mapper;

    public BookRepositoryAdapter(InMemoryDatabase db, EntityMapper mapper) {
        this.db = db;
        this.mapper = mapper;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return db.findBookById(id).map(mapper::toDomainBook);
    }

    @Override
    public List<Book> findAll() {
        return db.findAllBooks().stream()
                .map(mapper::toDomainBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAvailable() {
        return db.findAvailableBooks().stream()
                .map(mapper::toDomainBook)
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity saved  = db.saveBook(entity);
        return mapper.toDomainBook(saved);
    }

    @Override
    public void deleteById(UUID id) {
        db.deleteBook(id);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return db.existsBookByIsbn(isbn);
    }
}