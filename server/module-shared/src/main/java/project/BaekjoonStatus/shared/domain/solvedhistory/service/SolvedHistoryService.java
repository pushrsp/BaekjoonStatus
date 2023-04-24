package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolvedHistoryService {

    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    public SolvedHistory create(User user, Problem problem, boolean isBefore) {
        return new SolvedHistory(user, problem, isBefore, DateProvider.getDate(), DateProvider.getDateTime());
    }

    public SolvedHistory create(User user, Problem problem, boolean isBefore, LocalDate createDate, LocalDateTime createdTime) {
        return new SolvedHistory(user, problem, isBefore, createDate, createdTime);
    }

    public List<SolvedHistory> createWithProblems(User user, List<Problem> problems, boolean isBefore) {
        List<SolvedHistory> ret = new ArrayList<>();
        for (Problem problem : problems)
            ret.add(create(user, problem, isBefore));

        return ret;
    }

    @Transactional
    public List<SolvedHistory> bulkInsert(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAll(solvedHistories);
    }

    @Transactional
    public List<SolvedHistory> saveAll(User user, List<Problem> problems, boolean isBefore) {
        return bulkInsert(SolvedHistory.create(user, problems, isBefore));
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

    public List<SolvedHistory> findSolvedHistoriesByUserId(String userId, Integer offset, Integer limit) {
        return solvedHistoryRepository.findAllByUserId(UUID.fromString(userId), offset, limit);
    }
}
