package project.BaekjoonStatus.shared.member.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (ResultSet rs, int rowNum) -> Member.builder()
            .id(String.valueOf(rs.getLong("member_id")))
            .username(rs.getString("username"))
            .baekjoonUsername(rs.getString("baekjoon_username"))
            .build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member user) {
        return memberJpaRepository.save(MemberEntity.from(user)).to();
    }

    @Override
    public Optional<Member> findById(Long userId) {
        return memberJpaRepository.findById(userId).map(MemberEntity::to);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username).map(MemberEntity::to);
    }

    @Override
    public List<Member> findAllGreaterThanMemberId(Long memberId, Integer limit) {
        String sql = "select m.member_id, m.username, m.baekjoon_username from MEMBER m " +
                "where m.member_id > :memberId " +
                "limit :limit";

        return namedParameterJdbcTemplate.query(sql, generateParams(memberId, limit), MEMBER_ROW_MAPPER);
    }

    private MapSqlParameterSource generateParams(Long memberId, Integer limit) {
        return new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("limit", limit);
    }

    @Override
    public void deleteAllInBatch() {
        memberJpaRepository.deleteAllInBatch();
    }
}
