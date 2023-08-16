package project.BaekjoonStatus.shared.member.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.member.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static project.BaekjoonStatus.shared.member.infra.QMemberEntity.memberEntity;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository userJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(EntityManager em, MemberJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member save(Member user) {
        return userJpaRepository.save(MemberEntity.from(user)).to();
    }

    @Override
    public Optional<Member> findById(Long userId) {
        return userJpaRepository.findById(userId).map(MemberEntity::to);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(MemberEntity::to);
    }

    @Override
    public List<Member> findAllByGreaterThanUserId(Long userId, int limit) {
        return queryFactory
                .select(Projections.constructor(Member.class, memberEntity.id.as("userId"), memberEntity.username, memberEntity.baekjoonUsername))
                .from(memberEntity)
                .where(memberEntity.id.gt(userId))
                .limit(limit)
                .fetch();
    }

    @Override
    public void deleteAllInBatch() {
        userJpaRepository.deleteAllInBatch();
    }
}
