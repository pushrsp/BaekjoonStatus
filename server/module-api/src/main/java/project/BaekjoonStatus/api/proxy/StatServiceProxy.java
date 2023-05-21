package project.BaekjoonStatus.api.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.BaekjoonStatus.api.cache.Cache;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.api.facade.StatService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatServiceProxy {
    private static final Cache<List<Problem>> DAILY_PROBLEMS = new Cache<>();
    private static final Cache<SolvedHistoriesByUserId> SOLVED_HISTORIES = new Cache<>();
    private static final Cache<List<CountByDate>> SOLVED_COUNT_GROUP_BY_DATE = new Cache<>();
    private static final Cache<List<CountByLevel>> SOLVED_COUNT_GROUP_BY_LEVEL = new Cache<>();
    private static final Cache<List<CountByTag>> SOLVED_COUNT_GROUP_BY_TAG = new Cache<>();


    private final StatService target;

    public List<Problem> getDailyProblems(String userId) {
        return DAILY_PROBLEMS.get(userId, target::getDailyProblems);
    }

    public List<CountByDate> getSolvedCountGroupByDate(String userId, String year) {
        if(year.isEmpty())
            year = String.valueOf(DateProvider.getDate().getYear());

        String finalYear = year;
        return SOLVED_COUNT_GROUP_BY_DATE.get(userId, () -> target.getSolvedCountGroupByDate(userId, finalYear));
    }

    public List<CountByLevel> getSolvedCountGroupByLevel(String userId) {
        return SOLVED_COUNT_GROUP_BY_LEVEL.get(userId, () -> target.getSolvedCountGroupByLevel(userId));
    }

    public List<CountByTag> getSolvedCountGroupByTag(String userId) {
        return SOLVED_COUNT_GROUP_BY_TAG.get(userId, () -> target.getSolvedCountGroupByTag(userId));
    }

    public SolvedHistoriesByUserId getSolvedHistoriesByUserId(String userId, Integer offset) {
        return SOLVED_HISTORIES.get(userId + "," + offset, () -> target.getSolvedHistoriesByUserId(userId, offset));
    }
}
