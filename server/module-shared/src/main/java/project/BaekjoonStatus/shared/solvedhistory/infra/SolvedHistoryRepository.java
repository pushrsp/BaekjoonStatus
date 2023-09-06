package project.BaekjoonStatus.shared.solvedhistory.infra;


import project.BaekjoonStatus.shared.solvedhistory.domain.*;

import java.util.List;

public interface SolvedHistoryRepository {
    int saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedProblemCountByDate(String memberId, String year);
    List<CountByTier> findSolvedProblemCountByTier(String memberId);
    List<GroupByTag> findSolvedProblemCountByTag(String memberId);
    List<SolvedHistoryByMemberId> findAllByMemberId(String memberId, int offset, int limit);
    List<SolvedHistory> findAllByMemberId(String memberId);
    void deleteAllInBatch();
}
