package project.BaekjoonStatus.shared.member.infra;

import project.BaekjoonStatus.shared.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member user);
    Optional<Member> findById(Long userId);
    Optional<Member> findByUsername(String username);
    List<Member> findAllByGreaterThanUserId(Long userId, int limit);
    void deleteAllInBatch();
}
