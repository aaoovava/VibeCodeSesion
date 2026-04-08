package presentation.controller;

import application.usecase.LibraryService;
import domain.model.Book;
import domain.model.Member;
import presentation.dto.BookResponseDto;
import presentation.mapper.BookDtoMapper;

import java.util.List;
import java.util.UUID;

// TOGAF: Technology/Application Domain — Controller
// Simulates a REST Controller (@RestController in Spring Boot)
// The Controller knows NOTHING about the DB, JPA, or business rules!
public class LibraryController {

    private final LibraryService service;
    private final BookDtoMapper  mapper;

    public LibraryController(LibraryService service, BookDtoMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    // POST /api/books
    public BookResponseDto addBook(String isbn, String title, String author) {
        System.out.println("[Controller] POST /api/books");
        Book book = service.addBook(isbn, title, author);
        return mapper.toDto(book);
    }

    // GET /api/books
    public List<BookResponseDto> getAllBooks() {
        System.out.println("[Controller] GET /api/books");
        return mapper.toDtoList(service.getAllBooks());
    }

    // GET /api/books/available
    public List<BookResponseDto> getAvailableBooks() {
        System.out.println("[Controller] GET /api/books/available");
        return mapper.toDtoList(service.getAvailableBooks());
    }

    // POST /api/books/{bookId}/borrow?memberId=...
    public BookResponseDto borrowBook(UUID bookId, UUID memberId) {
        System.out.println("[Controller] POST /api/books/" + bookId.toString().substring(0,8) + ".../borrow");
        Book book = service.borrowBook(bookId, memberId);
        return mapper.toDto(book);
    }

    // POST /api/books/{bookId}/return?memberId=...
    public BookResponseDto returnBook(UUID bookId, UUID memberId) {
        System.out.println("[Controller] POST /api/books/" + bookId.toString().substring(0,8) + ".../return");
        Book book = service.returnBook(bookId, memberId);
        return mapper.toDto(book);
    }

    // POST /api/members
    public Member registerMember(String name, String email) {
        System.out.println("[Controller] POST /api/members");
        return service.registerMember(name, email);
    }

    // GET /api/members
    public List<Member> getAllMembers() {
        System.out.println("[Controller] GET /api/members");
        return service.getAllMembers();
    }
}