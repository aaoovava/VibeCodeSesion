package domain.model;

import domain.exception.BookNotAvailableException;
import domain.valueobject.ISBN;
import lombok.Getter;

import java.util.UUID;

// TOGAF: Business Architecture — Domain Entity
// Key principle: business rules live here, not in services!
@Getter
public class Book {

    private final UUID id;
    private final ISBN isbn;
    private final String title;
    private final String author;
    private boolean available;
    private String borrowedBy; // reader's name or null

    public Book(UUID id, ISBN isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrowedBy = null;
    }

    // -- Business rules ------------------------------------------------------

    /** Borrow a book to a reader */
    public void borrow(String memberName) {
        if (!available) {
            throw new BookNotAvailableException(title, borrowedBy);
        }
        this.available  = false;
        this.borrowedBy = memberName;
    }

    /** Return a book to the library */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException(
                    "Book \"" + title + "\" is already in the library!"
            );
        }
        this.available  = true;
        this.borrowedBy = null;
    }

    // -- Getters -------------------------------------------------------------

    @Override
    public String toString() {
        String status = available
                ? "Available"
                : "Borrowed by: " + borrowedBy;
        return String.format("Book{id=%s, title='%s', author='%s', isbn=%s, status='%s'}",
                id.toString().substring(0, 8), title, author, isbn, status);
    }
}