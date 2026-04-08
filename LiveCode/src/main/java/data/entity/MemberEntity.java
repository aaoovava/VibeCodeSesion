package data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// TOGAF: Data Architecture — JPA Entity (simulation)
@Getter
@Setter
public class MemberEntity {

    private UUID   id;
    private String name;
    private String email;
    private int    borrowedCount;

    public MemberEntity() {}

    public MemberEntity(UUID id, String name, String email, int borrowedCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedCount = borrowedCount;
    }
}