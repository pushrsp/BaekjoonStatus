package project.BaekjoonStatus.shared.solvedhistory.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.*;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.solvedhistory.service.port.SolvedHistoryRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static project.BaekjoonStatus.shared.problem.infra.QProblemEntity.problemEntity;
import static project.BaekjoonStatus.shared.solvedhistory.infra.QSolvedHistoryEntity.solvedHistoryEntity;
import static project.BaekjoonStatus.shared.tag.infra.QTagEntity.tagEntity;

@Repository
@Transactional(readOnly = true)
public class SolvedHistoryRepositoryImpl implements SolvedHistoryRepository {
    private static final String DATE_FORMAT_EXPRESSION = "DATE_FORMAT({0}, {1})";
    private static final String DATE_FORMAT = "%Y-%m-%d";
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
    public List<CountByDate> findSolvedCountGroupByDate(Long userId, String year) {
        StringTemplate dateFormat = getDateFormat(solvedHistoryEntity.createdDate, DATE_FORMAT);
        StringTemplate yearFormat = getDateFormat(solvedHistoryEntity.createdDate, YEAR_FORMAT);

        return queryFactory
                .select(Projections.bean(CountByDate.class, dateFormat.as("day"), solvedHistoryEntity.user.id.count().as("value")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.user.id.eq(userId).and(yearFormat.eq(year).and(solvedHistoryEntity.isBefore.eq(false))))
                .groupBy(solvedHistoryEntity.createdDate)
                .fetch();
    }

    @Override
    public List<CountByLevel> findSolvedCountGroupByLevel(Long userId) {
        return queryFactory
                .select(Projections.bean(CountByLevel.class, caseBuilder(), solvedHistoryEntity.user.id.count().as("count")))
                .from(solvedHistoryEntity)
                .where(solvedHistoryEntity.user.id.eq(userId))
                .groupBy(solvedHistoryEntity.problemLevel)
                .fetch();
    }

    @Override
    public List<CountByTag> findSolvedCountGroupByTag(Long userId) {
        return queryFactory
                .select(Projections.bean(CountByTag.class, tagEntity.tagName.as("tag"), solvedHistoryEntity.user.id.count().as("count")))
                .from(solvedHistoryEntity)
                .join(problemEntity).on(problemEntity.id.eq(solvedHistoryEntity.problem.id))
                .join(tagEntity).on(tagEntity.problem.id.eq(problemEntity.id))
                .where(solvedHistoryEntity.user.id.eq(userId))
                .groupBy(tagEntity.tagName)
                .having(tagEntity.tagName.in(TAG_IN))
                .fetch();
    }

    @Override //FIXME
    public List<SolvedHistoryEntity> findAllByUserId(Long userId, int offset, int limit) {
        return queryFactory.select(solvedHistoryEntity)
                .from(solvedHistoryEntity)
                .join(solvedHistoryEntity.problem, problemEntity).fetchJoin()
                .where(solvedHistoryEntity.user.id.eq(userId))
                .offset(offset)
                .limit(limit + 1)
                .orderBy(solvedHistoryEntity.problemLevel.desc(), solvedHistoryEntity.problem.id.asc())
                .fetch();
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
                .when(solvedHistoryEntity.problemLevel.between(25, 30))
                .then("ruby")
                .otherwise("unrated")
                .as("level");
    }

    private StringTemplate getDateFormat(DatePath path, String dateFormat) {
        return Expressions.stringTemplate(SolvedHistoryRepositoryImpl.DATE_FORMAT_EXPRESSION, path, dateFormat);
    }
}
