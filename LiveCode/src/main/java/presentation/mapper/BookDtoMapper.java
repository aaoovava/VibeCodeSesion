package presentation.mapper;

import domain.model.Book;
import presentation.dto.BookResponseDto;

import java.util.List;
import java.util.stream.Collectors;

// TOGAF: Technology Domain — DTO Mapper
// The controller receives DTOs, never domain objects directly
public class BookDtoMapper {

    public BookResponseDto toDto(Book book) {
        return new BookResponseDto(
                book.getId().toString(),
                book.getIsbn().getValue(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable(),
                book.getBorrowedBy()
        );
    }

    public List<BookResponseDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}