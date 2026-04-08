package presentation.dto;

// TOGAF: Technology/Application Domain — Data Transfer Object
// DTO isolates the internal domain model from the external API
public class BookResponseDto {

    private String  id;
    private String  isbn;
    private String  title;
    private String  author;
    private boolean available;
    private String  borrowedBy;

    public BookResponseDto() {}

    public BookResponseDto(String id, String isbn, String title,
                           String author, boolean available, String borrowedBy) {
        this.id         = id;
        this.isbn       = isbn;
        this.title      = title;
        this.author     = author;
        this.available  = available;
        this.borrowedBy = borrowedBy;
    }

    // Getters
    public String  getId()          { return id; }
    public String  getIsbn()        { return isbn; }
    public String  getTitle()       { return title; }
    public String  getAuthor()      { return author; }
    public boolean isAvailable()    { return available; }
    public String  getBorrowedBy()  { return borrowedBy; }

    @Override
    public String toString() {
        String status = available ? "Available" : "Borrowed by: " + borrowedBy;
        return String.format("BookDto{id='%s', title='%s', author='%s', isbn='%s', status='%s'}",
                id.substring(0, 8), title, author, isbn, status);
    }
}