package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByDateCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByLevelCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByTagCommand;

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

    public List<SolvedHistoryDto.SolvedCountByDate> getSolvedCountGroupByDate(GetSolvedCountGroupByDateCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByDate(command.getUUIDUserId(), command.getYear());
    }

    public List<SolvedHistoryDto.SolvedCountByLevel> getSolvedCountGroupByLevel(GetSolvedCountGroupByLevelCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByLevel(command.getUUIDUserId());
    }

    public List<SolvedHistoryDto.SolvedCountByTag> getSolvedCountGroupByTag(GetSolvedCountGroupByTagCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByTag(command.getUUIDUserId());
    }

    public List<SolvedHistoryDto.SolvedHistoryResp> findSolvedHistories(String userId, Integer offset) {
        return solvedHistoryRepository.findSolvedHistories(UUID.fromString(userId), offset, PAGE_SIZE);
    }
}
