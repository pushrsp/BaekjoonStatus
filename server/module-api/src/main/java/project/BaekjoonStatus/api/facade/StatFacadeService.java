package project.BaekjoonStatus.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatFacadeService {
    private static final int PAGE_SIZE = 10;

    private final DailyProblemService dailyProblemService;
    private final SolvedHistoryService solvedHistoryService;
    private final TagService tagService;

    public List<Problem> getDailyProblems() {
        return dailyProblemService.findTodayProblems(DateProvider.getDate().minusDays(1)).stream()
                .map(DailyProblem::getProblem)
                .map(Problem::of)
                .toList();
    }

    public List<CountByDate> getSolvedCountGroupByDate(Long userId, String year) {
        return solvedHistoryService.findSolvedCountGroupByDate(userId, year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(Long userId) {
        List<CountByLevel> solvedCountGroupByLevel = solvedHistoryService.findSolvedCountGroupByLevel(userId);
        Map<String, Long> map = new HashMap<>();

        for (CountByLevel countByLevel : solvedCountGroupByLevel) {
            if(map.containsKey(countByLevel.getLevel()))
                map.replace(countByLevel.getLevel(), map.get(countByLevel.getLevel()) + countByLevel.getCount());
            else
                map.put(countByLevel.getLevel(), countByLevel.getCount());
        }

        return map.keySet().stream()
                .map((k) -> new CountByLevel(k, map.get(k)))
                .toList();
    }

    public List<CountByTag> getSolvedCountGroupByTag(Long userId) {
        return solvedHistoryService.findSolvedCountGroupByTag(userId);
    }

    public SolvedHistoriesByUserId getSolvedHistoriesByUserId(Long userId, int offset) {
        if(offset > 0)
            offset *= PAGE_SIZE;

        List<SolvedHistory> histories = solvedHistoryService.findAllByUserId(userId, offset, PAGE_SIZE);
        List<Tag> tags = tagService.findAllByProblemIdIn(histories.stream().map(h -> h.getProblem().getId()).toList());

        return SolvedHistoriesByUserId.of(histories, tags, PAGE_SIZE);
    }
}