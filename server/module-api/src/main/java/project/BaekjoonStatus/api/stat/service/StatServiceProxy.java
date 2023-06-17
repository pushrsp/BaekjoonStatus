package project.BaekjoonStatus.api.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.BaekjoonStatus.api.stat.service.cache.Cache;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByDate;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTag;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTier;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistoryByUserId;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatServiceProxy {
    private static final Cache<List<DailyProblem>> DAILY_PROBLEMS = new Cache<>();
    private static final Cache<List<SolvedHistoryByUserId>> SOLVED_HISTORIES = new Cache<>();
    private static final Cache<List<GroupByDate>> SOLVED_COUNT_GROUP_BY_DATE = new Cache<>();
    private static final Cache<List<GroupByTier>> SOLVED_COUNT_GROUP_BY_LEVEL = new Cache<>();
    private static final Cache<List<GroupByTag>> SOLVED_COUNT_GROUP_BY_TAG = new Cache<>();


    private final StatService target;

    public List<DailyProblem> findTodayProblems(String userId) {
        return DAILY_PROBLEMS.get(userId, target::getTodayProblems);
    }

    public List<GroupByDate> findSolvedCountGroupByDate(String userId, String year) {
        if(year.isEmpty())
            year = String.valueOf(DateProvider.getDate().getYear());

        String finalYear = year;
        return SOLVED_COUNT_GROUP_BY_DATE.get(userId, () -> target.getSolvedCountGroupByDate(Long.parseLong(userId), finalYear));
    }

    public List<GroupByTier> findSolvedCountGroupByLevel(String userId) {
        return SOLVED_COUNT_GROUP_BY_LEVEL.get(userId, () -> target.getSolvedCountGroupByLevel(Long.parseLong(userId)));
    }

    public List<GroupByTag> findSolvedCountGroupByTag(String userId) {
        return SOLVED_COUNT_GROUP_BY_TAG.get(userId, () -> target.getSolvedCountGroupByTag(Long.parseLong(userId)));
    }

    public List<SolvedHistoryByUserId> findSolvedHistoriesByUserId(String userId, int offset) {
        return SOLVED_HISTORIES.get(userId + "," + offset, () -> target.getSolvedHistoriesByUserId(Long.parseLong(userId), offset));
    }
}
