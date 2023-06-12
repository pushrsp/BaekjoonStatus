package project.BaekjoonStatus.api.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.stat.controller.request.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.stat.controller.request.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.solvedhistory.infra.SolvedHistoryEntity;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatService {
    private static final int PAGE_SIZE = 10;

    private final DailyProblemService dailyProblemService;
    private final SolvedHistoryService solvedHistoryService;
    private final TagService tagService;

    public List<DailyProblem> findTodayProblems() {
        return dailyProblemService.findTodayProblems(DateProvider.getDate().minusDays(1));
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

        List<SolvedHistoryEntity> histories = solvedHistoryService.findAllByUserId(userId, offset, PAGE_SIZE);
        List<Tag> tags = tagService.findAllByProblemIdIn(histories.stream().map(h -> h.getProblem().getId()).toList());

        return SolvedHistoriesByUserId.of(histories, null, PAGE_SIZE);
    }
}
