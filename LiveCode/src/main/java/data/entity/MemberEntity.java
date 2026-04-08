package data.entity;

import java.util.UUID;

// TOGAF: Data Architecture — JPA Entity (simulation)
public class MemberEntity {

    private UUID   id;
    private String name;
    private String email;
    private int    borrowedCount;

    public MemberEntity() {}

    public MemberEntity(UUID id, String name, String email, int borrowedCount) {
        this.id            = id;
        this.name          = name;
        this.email         = email;
        this.borrowedCount = borrowedCount;
    }

    public UUID   getId()                     { return id; }
    public void   setId(UUID id)              { this.id = id; }
    public String getName()                   { return name; }
    public void   setName(String name)        { this.name = name; }
    public String getEmail()                  { return email; }
    public void   setEmail(String email)      { this.email = email; }
    public int    getBorrowedCount()          { return borrowedCount; }
    public void   setBorrowedCount(int count) { this.borrowedCount = count; }
}