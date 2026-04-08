package data.maper;

import data.entity.BookEntity;
import data.entity.MemberEntity;
import domain.model.Book;
import domain.model.Member;
import domain.valueobject.ISBN;

// TOGAF: Data Architecture — Entity ↔ Domain Mapper
// Isolates the internal DB structure from the business model
public class EntityMapper {

    // BookEntity → Book (Domain)
    public Book toDomainBook(BookEntity entity) {
        Book book = new Book(
                entity.getId(),
                new ISBN(entity.getIsbn()),
                entity.getTitle(),
                entity.getAuthor()
        );
        // If the book is borrowed — restore the state
        if (!entity.isAvailable()) {
            book.borrow(entity.getBorrowedBy());
        }
        return book;
    }

    // Book (Domain) → BookEntity
    public BookEntity toEntity(Book book) {
        return new BookEntity(
                book.getId(),
                book.getIsbn().getValue(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable(),
                book.getBorrowedBy()
        );
    }

    // MemberEntity → Member (Domain)
    public Member toDomainMember(MemberEntity entity) {
        Member member = new Member(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
        // Restore the borrowed books counter
        for (int i = 0; i < entity.getBorrowedCount(); i++) {
            member.incrementBorrowed();
        }
        return member;
    }

    // Member (Domain) → MemberEntity
    public MemberEntity toEntity(Member member) {
        return new MemberEntity(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getBorrowedCount()
        );
    }
}