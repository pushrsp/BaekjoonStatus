package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.domain.problem.entity.QProblem.problem;
import static project.BaekjoonStatus.shared.domain.solvedhistory.entity.QSolvedHistory.solvedHistory;

@Repository
public class SolvedHistoryRepositoryImpl implements SolvedHistoryRepository {
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
}
