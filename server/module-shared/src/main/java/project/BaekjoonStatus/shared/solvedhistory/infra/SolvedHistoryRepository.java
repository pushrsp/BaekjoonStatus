package project.BaekjoonStatus.shared.solvedhistory.infra;


import project.BaekjoonStatus.shared.solvedhistory.domain.*;

import java.util.List;

public interface SolvedHistoryRepository {
    int saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedProblemCountByDate(String memberId, String year);
    List<CountByTier> findSolvedProblemCountByTier(String memberId);
    List<GroupByTag> findSolvedProblemCountByTag(String memberId);
    List<SolvedHistoryByUserId> findAllByUserId(String memberId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(String memberId);
    void deleteAllInBatch();
}
