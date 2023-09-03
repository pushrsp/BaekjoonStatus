package project.BaekjoonStatus.shared.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.solvedhistory.domain.*;
import project.BaekjoonStatus.shared.solvedhistory.infra.SolvedHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    private final SolvedHistoryRepository solvedHistoryRepository;

    @Transactional
    public void saveAll(List<SolvedHistory> solvedHistories) {
        solvedHistoryRepository.saveAll(solvedHistories);
    }

    @Transactional(readOnly = true)
    public List<SolvedHistory> findAllByUserId(Long userId) {
        return solvedHistoryRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<CountByDate> findSolvedCountGroupByDate(String memberId, String year) {
        return solvedHistoryRepository.findSolvedProblemCountByDate(memberId, year);
    }

    @Transactional(readOnly = true)
    public List<CountByTier> findSolvedCountGroupByLevel(String memberId) {
        return solvedHistoryRepository.findSolvedProblemCountByTier(memberId);
    }

    @Transactional(readOnly = true)
    public List<GroupByTag> findSolvedCountGroupByTag(Long memberId) {
        return solvedHistoryRepository.findSolvedProblemCountByTag(memberId);
    }

    @Transactional(readOnly = true)
    public List<SolvedHistoryByUserId> findAllByUserId(Long memberId, int offset, int pageSize) {
        return solvedHistoryRepository.findAllByUserId(memberId, offset, pageSize);
    }
}
