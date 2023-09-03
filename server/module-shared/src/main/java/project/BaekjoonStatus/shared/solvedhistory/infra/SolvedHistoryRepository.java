package project.BaekjoonStatus.shared.solvedhistory.infra;


import project.BaekjoonStatus.shared.solvedhistory.domain.*;

import java.util.List;

public interface SolvedHistoryRepository {
    int saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedProblemCountByDate(String memberId, String year);
    List<CountByTier> findSolvedProblemCountByTier(String memberId);
    List<GroupByTag> findSolvedProblemCountByTag(Long memberId);
    List<SolvedHistoryByUserId> findAllByUserId(Long memberId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(Long memberId);
    void deleteAllInBatch();
}
