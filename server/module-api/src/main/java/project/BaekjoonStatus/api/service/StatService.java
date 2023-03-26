package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {
    private final SolvedHistoryService solvedHistoryService;
    private final DailyProblemService dailyProblemService;

    public List<Problem> getDailyProblems() {
        return dailyProblemService.findDailyProblems().stream()
                .map(DailyProblem::getProblem)
                .map(Problem::of)
                .collect(Collectors.toList());
    }

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        if(year.isEmpty())
            year = String.valueOf(DateProvider.getDate().getYear());

       return solvedHistoryService.getSolvedCountGroupByDate(userId, year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        List<CountByLevel> solvedCountGroupByLevel = solvedHistoryService.getSolvedCountGroupByLevel(userId);
        Map<String, Long> map = new HashMap<>();

        for (CountByLevel countByLevel : solvedCountGroupByLevel) {
            if(map.containsKey(countByLevel.getLevel()))
                map.replace(countByLevel.getLevel(), map.get(countByLevel.getLevel()) + countByLevel.getCount());
            else
                map.put(countByLevel.getLevel(), countByLevel.getCount());
        }

        return map.keySet().stream()
                .map((k) -> new CountByLevel(k, map.get(k)))
                .collect(Collectors.toList());
    }

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return solvedHistoryService.getSolvedCountGroupByTag(userId);
    }

    public SolvedHistoriesByUserId getSolvedHistoriesByUserId(String userId, Integer offset) {
        Slice<SolvedHistory> solvedHistories = solvedHistoryService.findSolvedHistoriesByUserId(userId, offset);
        return SolvedHistoriesByUserId.of(solvedHistories);
    }
}
