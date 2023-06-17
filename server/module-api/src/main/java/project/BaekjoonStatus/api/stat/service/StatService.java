package project.BaekjoonStatus.api.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public List<DailyProblem> getTodayProblems() {
        return dailyProblemService.findTodayProblems(DateProvider.getToday());
    }

    public List<GroupByDate> getSolvedCountGroupByDate(Long userId, String year) {
        return solvedHistoryService.findSolvedCountGroupByDate(userId, year);
    }

    public List<GroupByTier> getSolvedCountGroupByLevel(Long userId) {
        return GroupByTier.toMap(solvedHistoryService.findSolvedCountGroupByLevel(userId))
                .entrySet()
                .stream()
                .map(s -> GroupByTier.from(s.getKey(), s.getValue()))
                .collect(Collectors.toList());
    }

    public List<GroupByTag> getSolvedCountGroupByTag(Long userId) {
        return solvedHistoryService.findSolvedCountGroupByTag(userId);
    }

    public List<SolvedHistoryByUserId> getSolvedHistoriesByUserId(Long userId, int offset) {
        if(offset > 0)
            offset *= PAGE_SIZE;

        List<SolvedHistoryByUserId> histories = solvedHistoryService.findAllByUserId(userId, offset, PAGE_SIZE + 1);
        Map<Long, List<Tag>> map = Tag.toMap(tagService.findByProblemIdsIn(histories.stream().map(SolvedHistoryByUserId::getProblemId).collect(Collectors.toList())));

        for (SolvedHistoryByUserId history : histories) {
            if(map.containsKey(history.getProblemId())) {
                history.addTags(map.get(history.getProblemId()));
            }
        }

        return histories;
    }
}
