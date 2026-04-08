import domain.exception.BookNotAvailableException;
import domain.model.Member;
import infrastructure.config.AppConfig;
import presentation.controller.LibraryController;
import presentation.dto.BookResponseDto;

import java.util.List;
import java.util.UUID;

// ============================================================================
//  TOGAF Library System — Demo
//  Architecture: Presentation → Application → Domain ← Data
//               (TOGAF: Technology | Application | Business | Data Domain)
// ============================================================================
public class Main {

    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {

        printBanner();

        // Assemble all dependencies via config (simulation of Spring IoC)
        LibraryController controller = new AppConfig().buildController();

        // -- Scenario 1: Member Registration ---------------------------------
        section("SCENARIO 1: Member Registration");

        Member olena = controller.registerMember("Olena Koval",  "olena@uni.edu");
        Member mykola = controller.registerMember("Mykola Bondar", "mykola@uni.edu");
        println(GREEN + "✓ Registered: " + RESET + olena);
        println(GREEN + "✓ Registered: " + RESET + mykola);

        // -- Scenario 2: Adding Books ----------------------------------------
        section("SCENARIO 2: Adding Books to Catalog");

        BookResponseDto cleanCode = controller.addBook(
                "978-0132350884", "Clean Code", "Robert C. Martin");

        BookResponseDto ddd = controller.addBook(
                "978-0321125217", "Domain-Driven Design", "Eric Evans");

        BookResponseDto togafBook = controller.addBook(
                "978-9087536794", "TOGAF 9.2 Foundation", "The Open Group");

        println(GREEN + "✓ Added: " + RESET + cleanCode);
        println(GREEN + "✓ Added: " + RESET + ddd);
        println(GREEN + "✓ Added: " + RESET + togafBook);

        // -- Scenario 3: View Catalog ----------------------------------------
        section("SCENARIO 3: View All Books (GET /api/books)");

        List<BookResponseDto> allBooks = controller.getAllBooks();
        allBooks.forEach(b -> println(CYAN + "  📚 " + RESET + b));

        // -- Scenario 4: Borrowing a Book ------------------------------------
        section("SCENARIO 4: Olena borrows \"Clean Code\"");

        UUID olenaId    = olena.getId();
        UUID cleanCodeId = UUID.fromString(cleanCode.getId());

        BookResponseDto borrowed = controller.borrowBook(cleanCodeId, olenaId);
        println(YELLOW + "→ Result: " + RESET + borrowed);

        // -- Scenario 5: Attempting to Borrow an Already Borrowed Book ------
        section("SCENARIO 5: Mykola tries to borrow the same book");

        try {
            controller.borrowBook(cleanCodeId, mykola.getId());
        } catch (BookNotAvailableException e) {
            println(RED + "⚠ Exception (expected): " + RESET + e.getMessage());
        }

        // -- Scenario 6: View Available Books --------------------------------
        section("SCENARIO 6: Available Books (GET /api/books/available)");

        List<BookResponseDto> available = controller.getAvailableBooks();
        println(GREEN + "Available books: " + available.size() + RESET);
        available.forEach(b -> println(GREEN + "  ✓ " + RESET + b));

        // -- Scenario 7: Mykola borrows DDD ----------------------------------
        section("SCENARIO 7: Mykola borrows \"Domain-Driven Design\"");

        UUID dddId = UUID.fromString(ddd.getId());
        BookResponseDto mykolaBorrowed = controller.borrowBook(dddId, mykola.getId());
        println(YELLOW + "→ Result: " + RESET + mykolaBorrowed);

        // -- Scenario 8: Olena returns a book --------------------------------
        section("SCENARIO 8: Olena returns \"Clean Code\"");

        BookResponseDto returned = controller.returnBook(cleanCodeId, olenaId);
        println(YELLOW + "→ Result: " + RESET + returned);

        // -- Scenario 9: Final Library State ---------------------------------
        section("SCENARIO 9: Final Catalog State");

        controller.getAllBooks().forEach(b -> {
            if (b.isAvailable()) {
                println(GREEN + "  ✓ " + RESET + b);
            } else {
                println(RED + "  ✗ " + RESET + b);
            }
        });

        println("");
        println(BLUE + "------------------ Member State ------------------" + RESET);
        controller.getAllMembers().forEach(m -> println(PURPLE + "  👤 " + RESET + m));

        // -- Scenario 10: Architectural Principle Demonstration -------------
        section("CONCLUSION: Key TOGAF Principles in Code");
        println(CYAN + "  Business Domain  → domain/model/Book.java       (business rules)" + RESET);
        println(CYAN + "  Application      → application/usecase/         (orchestration)" + RESET);
        println(CYAN + "  Data Domain      → data/adapter/                (persistence)" + RESET);
        println(CYAN + "  Technology       → presentation/controller/     (HTTP/API)" + RESET);
        println(YELLOW + "  DIP              → application/port/ (interface) ← data/adapter/ (impl)" + RESET);
        println("");
    }

    // -- Helper methods for pretty output ------------------------------------

    private static void printBanner() {
        System.out.println();
        System.out.println(PURPLE + BOLD + "================================================================" + RESET);
        System.out.println(PURPLE + BOLD + "          TOGAF Library System - Java Demo" + RESET);
        System.out.println(CYAN + "   Layered Architecture: Business | Data | App | Technology" + RESET);
        System.out.println(PURPLE + BOLD + "================================================================" + RESET);
        System.out.println();
    }

    private static void section(String title) {
        System.out.println();
        System.out.println(YELLOW + "---------------------------------------------------------------" + RESET);
        System.out.println(BOLD + WHITE + "| " + title + RESET);
        System.out.println(YELLOW + "---------------------------------------------------------------" + RESET);
    }

    private static void println(String msg) {
        System.out.println(msg);
    }
}