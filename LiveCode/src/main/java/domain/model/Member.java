package domain.model;
import java.util.UUID;

// TOGAF: Business Architecture — Domain Entity
public class Member {

    private final UUID   id;
    private final String name;
    private final String email;
    private int borrowedCount;

    public Member(UUID id, String name, String email) {
        this.id            = id;
        this.name          = name;
        this.email         = email;
        this.borrowedCount = 0;
    }

    // Business rule: limit on the number of borrowed books
    public void incrementBorrowed() {
        if (borrowedCount >= 5) {
            throw new IllegalStateException(
                    "Member \"" + name + "\" already has the maximum of 5 books!"
            );
        }
        borrowedCount++;
    }

    public void decrementBorrowed() {
        if (borrowedCount > 0) borrowedCount--;
    }

    public UUID   getId()           { return id; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public int    getBorrowedCount(){ return borrowedCount; }

    @Override
    public String toString() {
        return String.format("Member{id=%s, name='%s', email='%s', borrowed=%d/5}",
                id.toString().substring(0, 8), name, email, borrowedCount);
    }
}