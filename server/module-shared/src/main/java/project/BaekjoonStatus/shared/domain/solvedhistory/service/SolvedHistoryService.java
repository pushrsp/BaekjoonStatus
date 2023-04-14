package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(readOnly = true)
public class SolvedHistoryService {
    private static final int PAGE_SIZE = 10;

    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    @Transactional
    public List<SolvedHistory> bulkInsert(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAll(solvedHistories);
    }

    @Transactional
    public List<SolvedHistory> bulkInsertAndFlush(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAllAndFlush(solvedHistories);
    }

    @Transactional
    public List<SolvedHistory> saveAll(User user, List<Problem> problems, boolean isBefore) {
        return bulkInsert(SolvedHistory.create(user, problems, isBefore));
    }

    public List<SolvedHistory> findAllByUserId(String userId) {
        return solvedHistoryJpaRepository.findAllByUserId(UUID.fromString(userId));
    }

    public List<SolvedHistory> findAllByUserId(UUID userId) {
        return solvedHistoryJpaRepository.findAllByUserId(userId);
    }

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        return solvedHistoryRepository.findSolvedCountGroupByDate(UUID.fromString(userId), year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        return solvedHistoryRepository.findSolvedCountGroupByLevel(UUID.fromString(userId));
    }

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return solvedHistoryRepository.findSolvedCountGroupByTag(UUID.fromString(userId));
    }

    public Slice<SolvedHistory> findSolvedHistoriesByUserId(String userId, Integer offset) {
        Pageable pageable = PageRequest.of(offset, PAGE_SIZE, Sort.by(Sort.Order.desc("problem_level"), Sort.Order.asc("problem_id")));
        return solvedHistoryJpaRepository.findByUserId(UUID.fromString(userId), pageable);
    }
}
