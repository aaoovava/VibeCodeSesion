package application.port;

import domain.model.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TOGAF: Application Architecture — Port
public interface MemberRepositoryPort {

    Optional<Member> findById(UUID id);

    List<Member> findAll();

    Member save(Member member);
}