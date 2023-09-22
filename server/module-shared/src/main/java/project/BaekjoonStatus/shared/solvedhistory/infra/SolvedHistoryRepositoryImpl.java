package project.BaekjoonStatus.shared.solvedhistory.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.common.repository.BaseRepository;
import project.BaekjoonStatus.shared.solvedhistory.domain.*;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static project.BaekjoonStatus.shared.solvedhistory.infra.QSolvedHistoryEntity.solvedHistoryEntity;

@Repository
public class SolvedHistoryRepositoryImpl extends BaseRepository implements SolvedHistoryRepository {
    private static final String[] TAG_IN = {"dp", "implementation", "graphs", "greedy", "data_structures"};

    @Value("${database.date-format}")
    private String dateFormatExpression;

    @Value("${database.year-format}")
    private String yearFormatExpression;

    private final JPAQueryFactory queryFactory;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;

    @Autowired
    public SolvedHistoryRepositoryImpl(EntityManager em, SolvedHistoryJpaRepository solvedHistoryJpaRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.queryFactory = new JPAQueryFactory(em);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.solvedHistoryJpaRepository = solvedHistoryJpaRepository;
    }

    @Override
    @Transactional
    public int saveAll(List<SolvedHistory> solvedHistories) {
        String sql = " INSERT INTO SOLVED_HISTORY (solved_history_id, created_date, created_time, is_before, problem_level, problem_id, member_id) " +
                "VALUES (:solved_history_id, :created_date, :created_time, :is_before, :problem_level, :problem_id, :member_id) ";

        SqlParameterSource[] params = solvedHistories.stream()
                .map(this::generateParams)
                .toArray(SqlParameterSource[]::new);

        return namedParameterJdbcTemplate.batchUpdate(sql, params).length;
    }

    private SqlParameterSource generateParams(SolvedHistory solvedHistory) {
        return new MapSqlParameterSource()
                .addValue("solved_history_id", UUID.randomUUID().toString())
                .addValue("created_date", solvedHistory.getCreatedDate())
                .addValue("created_time", solvedHistory.getCreatedTime())
                .addValue("is_before", solvedHistory.getIsBefore())
                .addValue("problem_level", solvedHistory.getProblemLevel())
                .addValue("problem_id", solvedHistory.getProblem().getId())
                .addValue("member_id", solvedHistory.getMember().getId());
    }

    @Override
    public List<CountByDate> findSolvedProblemCountByDate(String memberId, String year) {
        StringTemplate yearFormat = getDateFormat(solvedHistoryEntity.createdDate, yearFormatExpression);

        return queryFactory
                .select(Projections.constructor(CountByDate.class, solvedHistoryEntity.createdDate.as("day"), solvedHistoryEntity.member.id.count().as("count")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.member.id.eq(parseLong(memberId)).and(solvedHistoryEntity.isBefore.eq(false)).and(yearFormat.eq(year)))
                .groupBy(solvedHistoryEntity.createdDate)
                .fetch();
    }

    @Override
    public List<CountByTier> findSolvedProblemCountByTier(String memberId) {
        return queryFactory
                .select(Projections.constructor(CountByTier.class, caseBuilder(), solvedHistoryEntity.member.id.count().as("count")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.member.id.eq(parseLong(memberId)))
                .groupBy(solvedHistoryEntity.problemLevel)
                .fetch();
    }

    @Override
    public List<GroupByTag> findSolvedProblemCountByTag(String memberId) {
        String sql = "SELECT t.tag_name as tagName, count(sh.member_id) as count " +
                "FROM SOLVED_HISTORY sh JOIN PROBLEM p ON p.problem_id = sh.problem_id " +
                "JOIN TAG t ON t.problem_id = p.problem_id " +
                "WHERE sh.member_id = :memberId AND t.tag_name in (:tagNames) " +
                "GROUP BY t.tag_name ";


        RowMapper<GroupByTag> rowMapper = (ResultSet rs, int rowNum) -> GroupByTag.builder()
                                                        .tag(rs.getString("tagName"))
                                                        .count(rs.getInt("count"))
                                                        .build();

        return namedParameterJdbcTemplate.query(sql, generateParams(memberId), rowMapper);
    }

    private SqlParameterSource generateParams(String memberId) {
        return new MapSqlParameterSource()
                .addValue("memberId", parseLong(memberId))
                .addValue("tagNames", Arrays.stream(TAG_IN).collect(Collectors.toList()));
    }

    @Override
    public List<SolvedHistoryByMemberId> findAllByMemberId(String memberId, int offset, int limit) {
        String sql = "SELECT p.problem_id as problemId, p.title as title, p.level as problemLevel " +
                "FROM PROBLEM p " +
                "JOIN (SELECT sh.problem_id as problem_id FROM SOLVED_HISTORY sh WHERE sh.member_id = :memberId ORDER BY sh.problem_level DESC, sh.problem_id ASC LIMIT :limit OFFSET :offset) as temp " +
                "ON p.problem_id = temp.problem_id";

        RowMapper<SolvedHistoryByMemberId> rowMapper = (ResultSet rs, int rowNum) -> SolvedHistoryByMemberId.builder()
                .problemId(String.valueOf(rs.getLong("problemId")))
                .title(rs.getString("title"))
                .problemLevel(rs.getInt("problemLevel"))
                .build();

        return namedParameterJdbcTemplate.query(sql, generateParams(memberId, limit, offset), rowMapper);
    }

    private SqlParameterSource generateParams(String memberId, int limit, int offset) {
        return new MapSqlParameterSource()
                .addValue("memberId", parseLong(memberId))
                .addValue("limit", limit)
                .addValue("offset", offset);
    }

    @Override
    public List<SolvedHistory> findAllByMemberId(String memberId) {
        return solvedHistoryJpaRepository.findAllByUserId(parseLong(memberId))
                .stream()
                .map(SolvedHistoryEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInBatch() {
        solvedHistoryJpaRepository.deleteAllInBatch();
    }

    private StringExpression caseBuilder() {
        return new CaseBuilder()
                .when(solvedHistoryEntity.problemLevel.between(1, 5))
                .then("bronze")
                .when(solvedHistoryEntity.problemLevel.between(6, 10))
                .then("silver")
                .when(solvedHistoryEntity.problemLevel.between(11, 15))
                .then("gold")
                .when(solvedHistoryEntity.problemLevel.between(16, 20))
                .then("platinum")
                .when(solvedHistoryEntity.problemLevel.between(21, 25))
                .then("diamond")
                .when(solvedHistoryEntity.problemLevel.between(26, 30))
                .then("ruby")
                .otherwise("unrated")
                .as("tier");
    }

    private StringTemplate getDateFormat(DatePath path, String dateFormat) {
        return Expressions.stringTemplate(dateFormatExpression, path, dateFormat);
    }
}
