package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    private static final int PAGE_SIZE = 10;

    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    public List<SolvedHistory> bulkInsert(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAll(solvedHistories);
    }

    public List<SolvedHistory> saveAll(User user, List<Problem> problems, boolean isBefore) {
        return bulkInsert(SolvedHistory.create(user, problems, isBefore));
    }

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        return solvedHistoryRepository.getSolvedCountGroupByDate(UUID.fromString(userId), year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        return solvedHistoryRepository.getSolvedCountGroupByLevel(UUID.fromString(userId));
    }

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return solvedHistoryRepository.getSolvedCountGroupByTag(UUID.fromString(userId));
    }

    public List<SolvedHistoryByUserId> findSolvedHistoriesByUserId(String userId, Integer offset) {
        return solvedHistoryRepository.findSolvedHistories(UUID.fromString(userId), offset, PAGE_SIZE);
    }
}
