package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

import javax.persistence.EntityManager;
import java.util.List;

import static project.BaekjoonStatus.shared.domain.problem.entity.QProblem.problem;
import static project.BaekjoonStatus.shared.domain.solvedhistory.entity.QSolvedHistory.solvedHistory;
import static project.BaekjoonStatus.shared.domain.tag.entity.QTag.tag;

@Repository
@Transactional(readOnly = true)
public class SolvedHistoryRepositoryImpl implements SolvedHistoryRepository {
    private static final String DATE_FORMAT_EXPRESSION = "DATE_FORMAT({0}, {1})";
    private static final String DATE_FORMAT = "%Y-%m-%d";
    private static final String YEAR_FORMAT = "%Y";
    private static final String[] TAG_IN = {"dp", "implementation", "graphs", "greedy", "data_structures"};

    private final JPAQueryFactory queryFactory;
    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;

    @Autowired
    public SolvedHistoryRepositoryImpl(EntityManager em, SolvedHistoryJpaRepository solvedHistoryJpaRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.solvedHistoryJpaRepository = solvedHistoryJpaRepository;
    }

    @Override
    @Transactional
    public List<SolvedHistory> saveAll(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAll(solvedHistories);
    }

    @Override
    @Transactional
    public List<CountByDate> findSolvedCountGroupByDate(Long userId, String year) {
        StringTemplate dateFormat = getDateFormat(solvedHistory.createdDate, DATE_FORMAT);
        StringTemplate yearFormat = getDateFormat(solvedHistory.createdDate, YEAR_FORMAT);

        return queryFactory
                .select(Projections.bean(CountByDate.class, dateFormat.as("day"), solvedHistory.user.id.count().as("value")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId).and(yearFormat.eq(year).and(solvedHistory.isBefore.eq(false))))
                .groupBy(solvedHistory.createdDate)
                .fetch();
    }

    @Override
    public List<CountByLevel> findSolvedCountGroupByLevel(Long userId) {
        return queryFactory
                .select(Projections.bean(CountByLevel.class, caseBuilder(), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId))
                .groupBy(solvedHistory.problemLevel)
                .fetch();
    }

    @Override
    public List<CountByTag> findSolvedCountGroupByTag(Long userId) {
        return queryFactory
                .select(Projections.bean(CountByTag.class, tag.tagName.as("tag"), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .join(problem).on(problem.id.eq(solvedHistory.problem.id))
                .join(tag).on(tag.problem.id.eq(problem.id))
                .where(solvedHistory.user.id.eq(userId))
                .groupBy(tag.tagName)
                .having(tag.tagName.in(TAG_IN))
                .fetch();
    }

    @Override
    public List<SolvedHistory> findAllByUserId(Long userId, int offset, int limit) {
        return queryFactory.select(solvedHistory)
                .from(solvedHistory)
                .join(solvedHistory.problem, problem).fetchJoin()
                .where(solvedHistory.user.id.eq(userId))
                .offset(offset)
                .limit(limit + 1)
                .orderBy(solvedHistory.problemLevel.desc(), solvedHistory.problem.id.asc())
                .fetch();
    }

    @Override
    public List<SolvedHistory> findAllByUserId(Long userId) {
        return solvedHistoryJpaRepository.findAllByUserId(userId);
    }

    private StringExpression caseBuilder() {
        return new CaseBuilder()
                .when(solvedHistory.problemLevel.between(1, 5))
                .then("bronze")
                .when(solvedHistory.problemLevel.between(6, 10))
                .then("silver")
                .when(solvedHistory.problemLevel.between(11, 15))
                .then("gold")
                .when(solvedHistory.problemLevel.between(16, 20))
                .then("platinum")
                .when(solvedHistory.problemLevel.between(21, 25))
                .then("diamond")
                .when(solvedHistory.problemLevel.between(25, 30))
                .then("ruby")
                .otherwise("unrated")
                .as("level");
    }

    private StringTemplate getDateFormat(DatePath path, String dateFormat) {
        return Expressions.stringTemplate(SolvedHistoryRepositoryImpl.DATE_FORMAT_EXPRESSION, path, dateFormat);
    }
}
