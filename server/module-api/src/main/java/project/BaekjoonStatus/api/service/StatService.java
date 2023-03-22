package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.SolvedCountByDateDto;
import project.BaekjoonStatus.api.dto.StatDto.SolvedCountDto;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByTag;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByDateCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByLevelCommand;
import project.BaekjoonStatus.shared.dto.command.GetSolvedCountGroupByTagCommand;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatService {
    private final SolvedHistoryService solvedHistoryService;

    public Map<String, Long> getSolvedCountGroupByDate(SolvedCountDto data) {
        GetSolvedCountGroupByDateCommand command = GetSolvedCountGroupByDateCommand.builder()
                .userId(data.getUserId())
                .year(String.valueOf(LocalDate.now(ZoneId.of("UTC")).getYear()))
                .build();

        return SolvedCountByDateDto.of(solvedHistoryService.getSolvedCountGroupByDate(command)).getResult();
    }

    public List<SolvedCountByLevel> getSolvedCountGroupByLevel(SolvedCountDto data) {
        GetSolvedCountGroupByLevelCommand command = GetSolvedCountGroupByLevelCommand.builder()
                .userId(data.getUserId())
                .build();

        return solvedHistoryService.getSolvedCountGroupByLevel(command);
    }

    public List<SolvedCountByTag> getSolvedCountGroupByTag(SolvedCountDto data) {
        GetSolvedCountGroupByTagCommand command = GetSolvedCountGroupByTagCommand.builder()
                .userId(data.getUserId())
                .build();

        return solvedHistoryService.getSolvedCountGroupByTag(command);
    }
}
