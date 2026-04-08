package domain.exception;

// TOGAF: Business Architecture — Domain Exception
public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String title, String borrowedBy) {
        super("Book \"" + title + "\" is already borrowed by: " + borrowedBy);
    }
}