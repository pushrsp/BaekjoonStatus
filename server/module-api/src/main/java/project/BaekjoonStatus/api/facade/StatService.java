package project.BaekjoonStatus.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.repository.DailyProblemRepository;
import project.BaekjoonStatus.shared.domain.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagRepository;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatService {
    private static final int PAGE_SIZE = 10;

    private final DailyProblemService dailyProblemService;

    private final TagRepository tagRepository;
    private final DailyProblemRepository dailyProblemRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    public List<Problem> getDailyProblems() {
        return dailyProblemService.findTodayProblems(DateProvider.getDate().minusDays(1)).stream()
                .map(DailyProblem::getProblem)
                .map(Problem::of)
                .toList();
    }

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        return solvedHistoryRepository.findSolvedCountGroupByDate(userId, year);
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        List<CountByLevel> solvedCountGroupByLevel = solvedHistoryRepository.findSolvedCountGroupByLevel(userId);
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

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return solvedHistoryRepository.findSolvedCountGroupByTag(userId);
    }

    public SolvedHistoriesByUserId getSolvedHistoriesByUserId(String userId, Integer offset) {
        if(offset > 0)
            offset *= PAGE_SIZE;

        List<SolvedHistory> histories = solvedHistoryRepository.findAllByUserId(userId, offset, PAGE_SIZE);
        List<Tag> tags = tagRepository.findAllByProblemIdIn(histories.stream().map(h -> h.getProblem().getId()).toList());

        return SolvedHistoriesByUserId.of(histories, tags, PAGE_SIZE);
    }
}
