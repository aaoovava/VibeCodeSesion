package data.entity;

import java.util.UUID;

// TOGAF: Data Architecture — JPA Entity (simulation)
// Separate from domain.model.Book — this is important!
// In a real project, there would be @Entity, @Id, @Column annotations here
public class BookEntity {

    private UUID   id;
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private String borrowedBy;

    public BookEntity() {}

    public BookEntity(UUID id, String isbn, String title,
                      String author, boolean available, String borrowedBy) {
        this.id         = id;
        this.isbn       = isbn;
        this.title      = title;
        this.author     = author;
        this.available  = available;
        this.borrowedBy = borrowedBy;
    }

    // Getters & Setters
    public UUID    getId()          { return id; }
    public void    setId(UUID id)   { this.id = id; }

    public String  getIsbn()              { return isbn; }
    public void    setIsbn(String isbn)   { this.isbn = isbn; }

    public String  getTitle()             { return title; }
    public void    setTitle(String t)     { this.title = t; }

    public String  getAuthor()            { return author; }
    public void    setAuthor(String a)    { this.author = a; }

    public boolean isAvailable()              { return available; }
    public void    setAvailable(boolean av)   { this.available = av; }

    public String  getBorrowedBy()            { return borrowedBy; }
    public void    setBorrowedBy(String name) { this.borrowedBy = name; }
}