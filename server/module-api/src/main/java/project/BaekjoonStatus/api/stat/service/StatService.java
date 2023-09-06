package project.BaekjoonStatus.api.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.stat.service.annotation.RedisCacheable;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.solvedhistory.domain.CountByDate;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTag;
import project.BaekjoonStatus.shared.solvedhistory.domain.CountByTier;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistoryByMemberId;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.TagService;

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

    private final DateService dateService;

    @RedisCacheable(key = "getTodayProblems")
    public List<DailyProblem> findTodayProblems() {
        return dailyProblemService.findAllByCreatedDate(dateService);
    }

    @RedisCacheable(key = "getSolvedCountGroupByDate", paramNames = {"userId"})
    public List<CountByDate> findSolvedCountGroupByDate(String userId, String year) {
        return solvedHistoryService.findSolvedCountGroupByDate(userId, year);
    }

    @RedisCacheable(key = "getSolvedCountGroupByLevel", paramNames = {"memberId"})
    public List<CountByTier> findSolvedCountGroupByLevel(String memberId) {
        return CountByTier.toMap(solvedHistoryService.findSolvedCountGroupByLevel(memberId))
                .entrySet()
                .stream()
                .map(s -> CountByTier.from(s.getKey(), s.getValue()))
                .collect(Collectors.toList());
    }

    @RedisCacheable(key = "getSolvedCountGroupByTag", paramNames = {"userId"})
    public List<GroupByTag> findSolvedCountGroupByTag(String userId) {
        return solvedHistoryService.findSolvedCountGroupByTag(userId);
    }

    @RedisCacheable(key = "getSolvedHistoriesByUserId", paramNames = {"userId", "offset"})
    public List<SolvedHistoryByMemberId> findSolvedHistoriesByUserId(String userId, int offset) {
        List<SolvedHistoryByMemberId> histories = solvedHistoryService.findAllByMemberId(userId, offset * PAGE_SIZE, PAGE_SIZE + 1);
        Map<String, List<Tag>> map = Tag.toMap(tagService.findAllByProblemIdsIn(histories.stream()
                                                                                    .map(SolvedHistoryByMemberId::getProblemId)
                                                                                    .collect(Collectors.toList())));

        addTags(histories, map);
        return histories;
    }

    private void addTags(List<SolvedHistoryByMemberId> histories, Map<String, List<Tag>> map) {
        histories.stream()
                .filter(h -> map.containsKey(h.getProblemId()))
                .forEach(h -> h.addTags(map.get(h.getProblemId())));
    }
}
