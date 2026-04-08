package data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// TOGAF: Data Architecture — JPA Entity (simulation)
// Separate from domain.model.Book — this is important!
// In a real project, there would be @Entity, @Id, @Column annotations here
@Getter
@Setter
public class BookEntity {
    private UUID id;
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private String borrowedBy;

    public BookEntity() {}

    public BookEntity(UUID id, String isbn, String title,
                      String author, boolean available, String borrowedBy) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
        this.borrowedBy = borrowedBy;
    }
}