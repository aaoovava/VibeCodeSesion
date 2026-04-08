package presentation.dto;

import lombok.Getter;

// TOGAF: Technology/Application Domain — Data Transfer Object
// DTO isolates the internal domain model from the external API
@Getter
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
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
        this.borrowedBy = borrowedBy;
    }
    @Override
    public String toString() {
        String status = available ? "Available" : "Borrowed by: " + borrowedBy;
        return String.format("BookDto{id='%s', title='%s', author='%s', isbn='%s', status='%s'}",
                id.substring(0, 8), title, author, isbn, status);
    }
}