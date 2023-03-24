package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.domain.problem.entity.QProblem.problem;
import static project.BaekjoonStatus.shared.domain.solvedhistory.entity.QSolvedHistory.solvedHistory;
import static project.BaekjoonStatus.shared.domain.tag.entity.QTag.tag;

@Repository
public class SolvedHistoryRepositoryImpl implements SolvedHistoryRepository {
    private static final String DATE_FORMAT_EXPRESSION = "DATE_FORMAT({0}, {1})";
    private static final String DATE_FORMAT = "%Y-%m-%d";
    private static final String YEAR_FORMAT = "%Y";
    private static final String[] TAG_IN = {"dp", "implementation", "graphs", "greedy", "data_structures"};

    private final JPAQueryFactory queryFactory;

    public SolvedHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CountByDate> findSolvedCountGroupByDate(UUID userId, String year) {
        StringTemplate dateFormat = getDateFormat(solvedHistory.createdDate, DATE_FORMAT);
        StringTemplate yearFormat = getDateFormat(solvedHistory.createdDate, YEAR_FORMAT);

        return queryFactory
                .select(Projections.bean(CountByDate.class, dateFormat.as("day"), solvedHistory.user.id.count().as("value")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId).and(yearFormat.eq(year).and(solvedHistory.isBefore.eq(true))))
                .groupBy(solvedHistory.createdDate)
                .fetch();
    }

    @Override
    public List<CountByLevel> findSolvedCountGroupByLevel(UUID userId) {
        return queryFactory
                .select(Projections.bean(CountByLevel.class, caseBuilder(), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId))
                .groupBy(solvedHistory.problemLevel)
                .fetch();
    }

    @Override
    public List<CountByTag> findSolvedCountGroupByTag(UUID userId) {
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
