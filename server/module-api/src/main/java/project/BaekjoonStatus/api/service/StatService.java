package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.SolvedCountByDateDto;
import project.BaekjoonStatus.api.dto.StatDto.SolvedCountDto;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryReadService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByDate;
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
    private final SolvedHistoryReadService solvedHistoryReadService;

    public Map<String, Long> getSolvedCountGroupByDate(SolvedCountDto data) {
        GetSolvedCountGroupByDateCommand command = GetSolvedCountGroupByDateCommand.builder()
                .userId(data.getUserId())
                .year(String.valueOf(LocalDate.now(ZoneId.of("UTC")).getYear()))
                .build();

        return SolvedCountByDateDto.of(solvedHistoryReadService.getSolvedCountGroupByDate(command)).getResult();
    }

    public List<SolvedCountByLevel> getSolvedCountGroupByLevel(SolvedCountDto data) {
        GetSolvedCountGroupByLevelCommand command = GetSolvedCountGroupByLevelCommand.builder()
                .userId(data.getUserId())
                .build();

        return solvedHistoryReadService.getSolvedCountGroupByLevel(command);
    }

    public List<SolvedCountByTag> getSolvedCountGroupByTag(SolvedCountDto data) {
        GetSolvedCountGroupByTagCommand command = GetSolvedCountGroupByTagCommand.builder()
                .userId(data.getUserId())
                .build();

        return solvedHistoryReadService.getSolvedCountGroupByTag(command);
    }
}
