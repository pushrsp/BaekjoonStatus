package project.BaekjoonStatus.shared.solvedhistory.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.solvedhistory.domain.*;
import project.BaekjoonStatus.shared.solvedhistory.service.port.SolvedHistoryRepository;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static project.BaekjoonStatus.shared.solvedhistory.infra.QSolvedHistoryEntity.solvedHistoryEntity;

@Repository
@Transactional(readOnly = true)
public class SolvedHistoryRepositoryImpl implements SolvedHistoryRepository {
    private static final String DATE_FORMAT_EXPRESSION = "DATE_FORMAT({0}, {1})";
    private static final String YEAR_FORMAT = "%Y";
    private static final String[] TAG_IN = {"dp", "implementation", "graphs", "greedy", "data_structures"};

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
    public void saveAll(List<SolvedHistory> solvedHistories) {
        String sql = """
                INSERT INTO SOLVED_HISTORY (solved_history_id, created_date, created_time, is_before, problem_level, problem_id, user_id)
                VALUES (:solved_history_id, :created_date, :created_time, :is_before, :problem_level, :problem_id, :user_id)
                """;

        SqlParameterSource[] params = solvedHistories.stream()
                .map(this::generateParams)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private SqlParameterSource generateParams(SolvedHistory solvedHistory) {
        return new MapSqlParameterSource()
                .addValue("solved_history_id", UUID.randomUUID().toString())
                .addValue("created_date", solvedHistory.getCreatedDate())
                .addValue("created_time", solvedHistory.getCreatedTime())
                .addValue("is_before", solvedHistory.getIsBefore())
                .addValue("problem_level", solvedHistory.getProblemLevel())
                .addValue("problem_id", solvedHistory.getProblem().getId())
                .addValue("user_id", solvedHistory.getUser().getId());
    }

    @Override
    @Transactional
    public List<GroupByDate> findSolvedCountGroupByDate(Long userId, String year) {
        StringTemplate yearFormat = getDateFormat(solvedHistoryEntity.createdDate, YEAR_FORMAT);

        return queryFactory
                .select(Projections.constructor(GroupByDate.class, solvedHistoryEntity.createdDate.as("day"), solvedHistoryEntity.user.id.count().as("count")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.user.id.eq(userId).and(solvedHistoryEntity.isBefore.eq(false)).and(yearFormat.eq(year)))
                .groupBy(solvedHistoryEntity.createdDate)
                .fetch();
    }

    @Override
    public List<GroupByTier> findSolvedCountGroupByLevel(Long userId) {
        return queryFactory
                .select(Projections.constructor(GroupByTier.class, caseBuilder(), solvedHistoryEntity.user.id.count().as("count")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.user.id.eq(userId))
                .groupBy(solvedHistoryEntity.problemLevel)
                .fetch();
    }

    @Override
    public List<GroupByTag> findSolvedCountGroupByTag(Long userId) {
        String sql =
                """
                SELECT t.tag_name as tagName, count(sh.user_id) as count FROM SOLVED_HISTORY sh force index (idx__user_id)
                JOIN PROBLEM p ON p.problem_id = sh.problem_id
                JOIN TAG t ON t.problem_id = p.problem_id
                WHERE sh.user_id = :userId AND t.tag_name in (:tagNames)
                GROUP BY t.tag_name
                """;


        RowMapper<GroupByTag> rowMapper = (ResultSet rs, int rowNum) -> GroupByTag.builder()
                                                        .tag(rs.getString("tagName"))
                                                        .count(rs.getInt("count"))
                                                        .build();

        return namedParameterJdbcTemplate.query(sql, generateParams(userId), rowMapper);
    }

    private SqlParameterSource generateParams(Long userId) {
        return new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("tagNames", Arrays.stream(TAG_IN).toList());
    }

    @Override
    public List<SolvedHistoryByUserId> findAllByUserId(Long userId, int offset, int limit) {
        String sql =
                """
                SELECT p.problem_id as problemId, p.title as title, p.level as problemLevel
                FROM PROBLEM p
                JOIN (
                    SELECT sh.problem_id as problem_id
                    FROM SOLVED_HISTORY sh
                    WHERE sh.user_id = :userId
                    ORDER BY sh.problem_level DESC, sh.problem_id ASC
                    LIMIT :limit
                    OFFSET :offset
                ) as temp ON p.problem_id = temp.problem_id
                """;

        RowMapper<SolvedHistoryByUserId> rowMapper = (ResultSet rs, int rowNum) -> SolvedHistoryByUserId.builder()
                .problemId(rs.getLong("problemId"))
                .title(rs.getString("title"))
                .problemLevel(rs.getInt("problemLevel"))
                .build();

        return namedParameterJdbcTemplate.query(sql, generateParams(userId, limit, offset), rowMapper);
    }

    private SqlParameterSource generateParams(Long userId, int limit, int offset) {
        return new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("limit", limit)
                .addValue("offset", offset);
    }

    @Override
    public List<SolvedHistory> findAllByUserId(Long userId) {
        return solvedHistoryJpaRepository.findAllByUserId(userId)
                .stream()
                .map(SolvedHistoryEntity::to)
                .collect(Collectors.toList());
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
        return Expressions.stringTemplate(DATE_FORMAT_EXPRESSION, path, dateFormat);
    }
}
