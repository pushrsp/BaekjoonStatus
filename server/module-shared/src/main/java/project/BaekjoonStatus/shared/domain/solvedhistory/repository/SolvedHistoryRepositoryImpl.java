package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.domain.problemtag.entity.QProblemTag;
import project.BaekjoonStatus.shared.domain.tag.entity.QTag;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByTag;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.domain.problem.entity.QProblem.problem;
import static project.BaekjoonStatus.shared.domain.problemtag.entity.QProblemTag.problemTag;
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
    public List<SolvedHistoryResp> findSolvedHistories(UUID userId, int offset, int limit) {
        return queryFactory
                .select(Projections.bean(SolvedHistoryResp.class, problem.id.as("problemId"), problem.title.as("title"), solvedHistory.problemLevel.as("level")))
                .from(solvedHistory)
                .join(problem).on(solvedHistory.problem.id.eq(problem.id))
                .where(solvedHistory.user.id.eq(userId))
                .orderBy(solvedHistory.problemLevel.desc())
                .limit(limit)
                .offset((long) offset * limit)
                .fetch();
    }

    @Override
    public List<SolvedCountByDate> getSolvedCountGroupByDate(UUID userId, String year) {
        StringTemplate dateFormat = getDateFormat(solvedHistory.createdDate, DATE_FORMAT);
        StringTemplate yearFormat = getDateFormat(solvedHistory.createdDate, YEAR_FORMAT);

        return queryFactory
                .select(Projections.bean(SolvedCountByDate.class, dateFormat.as("date"), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId).and(yearFormat.eq(year).and(solvedHistory.isBefore.eq(false))))
                .groupBy(solvedHistory.createdDate)
                .fetch();
    }

    @Override
    public List<SolvedCountByLevel> getSolvedCountGroupByLevel(UUID userId) {
        return queryFactory
                .select(Projections.bean(SolvedCountByLevel.class, solvedHistory.problemLevel.as("level"), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .where(solvedHistory.user.id.eq(userId))
                .groupBy(solvedHistory.problemLevel)
                .fetch();
    }

    @Override
    public List<SolvedCountByTag> getSolvedCountGroupByTag(UUID userId) {
        return queryFactory
                .select(Projections.bean(SolvedCountByTag.class, tag.name.as("tag"), solvedHistory.user.id.count().as("count")))
                .from(solvedHistory)
                .join(problem).on(problem.id.eq(solvedHistory.problem.id))
                .join(problemTag).on(problemTag.problem.id.eq(problem.id))
                .join(tag).on(tag.id.eq(problemTag.tag.id))
                .where(solvedHistory.user.id.eq(userId))
                .groupBy(tag.name)
                .having(tag.name.in(TAG_IN))
                .fetch();

    }

    private StringTemplate getDateFormat(DatePath path, String dateFormat) {
        return Expressions.stringTemplate(SolvedHistoryRepositoryImpl.DATE_FORMAT_EXPRESSION, path, dateFormat);
    }
}
