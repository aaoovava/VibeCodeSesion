package application.usecase;

import application.port.BookRepositoryPort;
import application.port.MemberRepositoryPort;
import domain.model.Book;
import domain.model.Member;
import domain.valueobject.ISBN;

import java.util.List;
import java.util.UUID;

// TOGAF: Application Architecture — Use Cases (orchestrators)
// The service does not contain business logic — it only coordinates
public class LibraryService {

    // Depend on INTERFACES (ports), not on implementations!
    private final BookRepositoryPort bookRepo;
    private final MemberRepositoryPort memberRepo;

    public LibraryService(BookRepositoryPort bookRepo,
                          MemberRepositoryPort memberRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
    }

    // -- Use Case 1: Add a book -------------------------------------------------

    public Book addBook(String isbnValue, String title, String author) {
        if (bookRepo.existsByIsbn(isbnValue)) {
            throw new IllegalArgumentException(
                    "Book with ISBN " + isbnValue + " already exists in the library!"
            );
        }
        Book book = new Book(UUID.randomUUID(), new ISBN(isbnValue), title, author);
        Book saved = bookRepo.save(book);
        System.out.println("[LibraryService] Book added: " + saved.getTitle());
        return saved;
    }

    // -- Use Case 2: Borrow a book ----------------------------------------------

    public Book borrowBook(UUID bookId, UUID memberId) {
        // 1. Find the book (may throw an exception if not found)
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Book with ID " + bookId + " not found!"
                ));

        // 2. Find the member
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Member with ID " + memberId + " not found!"
                ));

        // 3. Business logic — in the domain, not here!
        member.incrementBorrowed();   // limit check in Member
        book.borrow(member.getName()); // availability check in Book

        // 4. Save the state
        bookRepo.save(book);
        memberRepo.save(member);

        System.out.println("[LibraryService] \"" + member.getName()
                + "\" borrowed book: \"" + book.getTitle() + "\"");
        return book;
    }

    // -- Use Case 3: Return a book ----------------------------------------------

    public Book returnBook(UUID bookId, UUID memberId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Book with ID " + bookId + " not found!"
                ));

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Member with ID " + memberId + " not found!"
                ));

        // Business logic in the domain
        book.returnBook();
        member.decrementBorrowed();

        bookRepo.save(book);
        memberRepo.save(member);

        System.out.println("[LibraryService] \"" + member.getName()
                + "\" returned book: \"" + book.getTitle() + "\"");
        return book;
    }

    // -- Use Case 4: View all books ---------------------------------------------

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    // -- Use Case 5: View available books ---------------------------------------

    public List<Book> getAvailableBooks() {
        return bookRepo.findAvailable();
    }

    // -- Use Case 6: View all members -------------------------------------------

    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    // -- Use Case 7: Register a member ------------------------------------------

    public Member registerMember(String name, String email) {
        Member member = new Member(UUID.randomUUID(), name, email);
        Member saved = memberRepo.save(member);
        System.out.println("[LibraryService] Registered member: " + saved.getName());
        return saved;
    }
}