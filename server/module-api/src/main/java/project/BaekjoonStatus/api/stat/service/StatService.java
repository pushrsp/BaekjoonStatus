package project.BaekjoonStatus.api.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.stat.service.annotation.RedisCacheable;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByDate;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTag;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTier;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistoryByUserId;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {
    private static final int PAGE_SIZE = 10;

    private final DailyProblemService dailyProblemService;
    private final SolvedHistoryService solvedHistoryService;
    private final TagService tagService;

    @RedisCacheable(key = "getTodayProblems")
    public List<DailyProblem> findTodayProblems() {
        return dailyProblemService.findTodayProblems(DateProvider.getToday(DateProvider.getDateTime()));
    }

    @RedisCacheable(key = "getSolvedCountGroupByDate", paramNames = {"userId"})
    public List<GroupByDate> findSolvedCountGroupByDate(Long userId, String year) {
        return solvedHistoryService.findSolvedCountGroupByDate(userId, year);
    }

    @RedisCacheable(key = "getSolvedCountGroupByLevel", paramNames = {"userId"})
    public List<GroupByTier> findSolvedCountGroupByLevel(Long userId) {
        return GroupByTier.toMap(solvedHistoryService.findSolvedCountGroupByLevel(userId))
                .entrySet()
                .stream()
                .map(s -> GroupByTier.from(s.getKey(), s.getValue()))
                .collect(Collectors.toList());
    }

    @RedisCacheable(key = "getSolvedCountGroupByTag", paramNames = {"userId"})
    public List<GroupByTag> findSolvedCountGroupByTag(Long userId) {
        return solvedHistoryService.findSolvedCountGroupByTag(userId);
    }

    @RedisCacheable(key = "getSolvedHistoriesByUserId", paramNames = {"userId", "offset"})
    public List<SolvedHistoryByUserId> findSolvedHistoriesByUserId(Long userId, int offset) {
        List<SolvedHistoryByUserId> histories = solvedHistoryService.findAllByUserId(userId, offset * PAGE_SIZE, PAGE_SIZE + 1);
        Map<Long, List<Tag>> map = Tag.toMap(tagService.findByProblemIdsIn(histories.stream()
                                                                                    .map(SolvedHistoryByUserId::getProblemId)
                                                                                    .collect(Collectors.toList())));

        addTags(histories, map);
        return histories;
    }

    private void addTags(List<SolvedHistoryByUserId> histories, Map<Long, List<Tag>> map) {
        histories.stream()
                .filter(h -> map.containsKey(h.getProblemId()))
                .forEach(h -> h.addTags(map.get(h.getProblemId())));
    }
}
