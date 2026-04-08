package data.repository;

import data.entity.BookEntity;
import data.entity.MemberEntity;

import java.util.*;

// TOGAF: Data Architecture — In-Memory "JPA Repository"
// Simulates Spring Data JPA without an actual database connection
// In a real project: interface BookJpaRepository extends JpaRepository<BookEntity, UUID>
public class InMemoryDatabase {

    // Simulates the books table
    private final Map<UUID, BookEntity> booksTable = new LinkedHashMap<>();
    // Simulates the members table
    private final Map<UUID, MemberEntity> membersTable = new LinkedHashMap<>();

    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();

    private InMemoryDatabase() {}

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    // -- Books CRUD ---------------------------------------------------------

    public Optional<BookEntity> findBookById(UUID id) {
        System.out.println("  [DB] SELECT * FROM books WHERE id = '" + id.toString().substring(0,8) + "...'");
        return Optional.ofNullable(booksTable.get(id));
    }

    public List<BookEntity> findAllBooks() {
        System.out.println("  [DB] SELECT * FROM books");
        return new ArrayList<>(booksTable.values());
    }

    public List<BookEntity> findAvailableBooks() {
        System.out.println("  [DB] SELECT * FROM books WHERE available = true");
        return booksTable.values().stream()
                .filter(BookEntity::isAvailable)
                .collect(java.util.stream.Collectors.toList());
    }

    public BookEntity saveBook(BookEntity entity) {
        boolean isNew = !booksTable.containsKey(entity.getId());
        booksTable.put(entity.getId(), entity);
        if (isNew) {
            System.out.println("[DB] INSERT INTO books VALUES ('" + entity.getTitle() + "', ...)");
        } else {
            System.out.println("[DB] UPDATE books SET available=" + entity.isAvailable()
                    + ", borrowed_by='" + entity.getBorrowedBy() + "' WHERE id=...");
        }
        return entity;
    }

    public void deleteBook(UUID id) {
        System.out.println("[DB] DELETE FROM books WHERE id = '" + id.toString().substring(0,8) + "...'");
        booksTable.remove(id);
    }

    public boolean existsBookByIsbn(String isbn) {
        System.out.println("[DB] SELECT COUNT(*) FROM books WHERE isbn = '" + isbn + "'");
        return booksTable.values().stream()
                .anyMatch(e -> e.getIsbn().equals(isbn));
    }

    // -- Members CRUD -------------------------------------------------------

    public Optional<MemberEntity> findMemberById(UUID id) {
        System.out.println("[DB] SELECT * FROM members WHERE id = '" + id.toString().substring(0,8) + "...'");
        return Optional.ofNullable(membersTable.get(id));
    }

    public List<MemberEntity> findAllMembers() {
        System.out.println("[DB] SELECT * FROM members");
        return new ArrayList<>(membersTable.values());
    }

    public MemberEntity saveMember(MemberEntity entity) {
        boolean isNew = !membersTable.containsKey(entity.getId());
        membersTable.put(entity.getId(), entity);
        if (isNew) {
            System.out.println("[DB] INSERT INTO members VALUES ('" + entity.getName() + "', ...)");
        } else {
            System.out.println("[DB] UPDATE members SET borrowed_count=" + entity.getBorrowedCount()
                    + " WHERE id=...");
        }
        return entity;
    }
}