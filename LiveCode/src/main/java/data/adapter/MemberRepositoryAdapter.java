package data.adapter;

import application.port.MemberRepositoryPort;
import data.entity.MemberEntity;
import data.maper.EntityMapper;
import data.repository.InMemoryDatabase;
import domain.model.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// TOGAF: Data Architecture — Adapter for Members
public class MemberRepositoryAdapter implements MemberRepositoryPort {

    private final InMemoryDatabase db;
    private final EntityMapper     mapper;

    public MemberRepositoryAdapter(InMemoryDatabase db, EntityMapper mapper) {
        this.db     = db;
        this.mapper = mapper;
    }

    @Override
    public Optional<Member> findById(UUID id) {
        return db.findMemberById(id).map(mapper::toDomainMember);
    }

    @Override
    public List<Member> findAll() {
        return db.findAllMembers().stream()
                .map(mapper::toDomainMember)
                .collect(Collectors.toList());
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = mapper.toEntity(member);
        MemberEntity saved  = db.saveMember(entity);
        return mapper.toDomainMember(saved);
    }
}