package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByDateCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByLevelCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByTagCommand;

import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

@Service
@RequiredArgsConstructor
public class SolvedHistoryReadService {
    private static final int PAGE_SIZE = 10;

    private final SolvedHistoryRepository solvedHistoryRepository;

    public List<SolvedCountByDate> getSolvedCountGroupByDate(GetSolvedCountGroupByDateCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByDate(command.getUUIDUserId(), command.getYear());
    }

    public List<SolvedCountByLevel> getSolvedCountGroupByLevel(GetSolvedCountGroupByLevelCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByLevel(command.getUUIDUserId());
    }

    public List<SolvedCountByTag> getSolvedCountGroupByTag(GetSolvedCountGroupByTagCommand command) {
        return solvedHistoryRepository.getSolvedCountGroupByTag(command.getUUIDUserId());
    }

    public List<SolvedHistoryResp> findSolvedHistories(String userId, Integer offset) {
        return solvedHistoryRepository.findSolvedHistories(UUID.fromString(userId), offset, PAGE_SIZE);
    }
}
