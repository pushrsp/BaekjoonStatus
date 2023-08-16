package project.BaekjoonStatus.shared.member.infra;

import project.BaekjoonStatus.shared.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(String memberId);
    Optional<Member> findByUsername(String username);
    List<Member> findAllGreaterThanMemberId(String memberId, Integer limit);
    void deleteAllInBatch();
}
