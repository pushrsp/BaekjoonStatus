package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.argumentresolver.Auth;
import project.BaekjoonStatus.api.dto.StatDto;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.api.service.StatService;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private static final ConcurrentHashMap<LocalDateTime, Object> DAILY_PROBLEMS_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<LocalDateTime, Map<String, Object>> DAILY_SOLVED_COUNT_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<LocalDateTime, Map<String, Object>> LEVEL_SOLVED_COUNT_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<LocalDateTime, Map<String, Object>> TAG_SOLVED_COUNT_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<LocalDateTime, Map<String, Object>> SOLVED_HISTORIES_CACHE = new ConcurrentHashMap<>();

    private final StatService statService;

    private LocalDateTime getNextCacheKey() {
        LocalDateTime now = DateProvider.getDateTime();
        LocalDateTime next = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 4,10,0);
        if(now.isBefore(next))
            return next;

        return next.plusDays(1);
    }

    @GetMapping("/daily")
    public CommonResponse getDailyProblems() {
        LocalDateTime nextCacheKey = getNextCacheKey();

        List<Problem> dailyProblems;
        if(DAILY_PROBLEMS_CACHE.containsKey(nextCacheKey)) {
            dailyProblems =(List<Problem>) DAILY_PROBLEMS_CACHE.get(nextCacheKey);
        } else {
            DAILY_PROBLEMS_CACHE.entrySet().removeIf(entry -> entry.getKey().isBefore(nextCacheKey));
            dailyProblems = statService.getDailyProblems();
            DAILY_PROBLEMS_CACHE.put(nextCacheKey, dailyProblems);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(dailyProblems)
                .build();
    }

    @GetMapping("/date")
    public CommonResponse getDailySolvedCount(@RequestParam String year, @Auth String userId) {
        LocalDateTime nextCacheKey = getNextCacheKey();

        List<CountByDate> solvedCountGroupByDate;
        if(DAILY_SOLVED_COUNT_CACHE.containsKey(nextCacheKey)) {
            if(DAILY_SOLVED_COUNT_CACHE.get(nextCacheKey).containsKey(userId)) {
                solvedCountGroupByDate = (List<CountByDate>) DAILY_SOLVED_COUNT_CACHE.get(nextCacheKey).get(userId);
            } else {
                solvedCountGroupByDate = statService.getSolvedCountGroupByDate(userId, year);
                DAILY_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByDate);
            }
        } else {
            solvedCountGroupByDate = statService.getSolvedCountGroupByDate(userId, year);
            DAILY_SOLVED_COUNT_CACHE.entrySet().removeIf(entry -> entry.getKey().isBefore(nextCacheKey));
            DAILY_SOLVED_COUNT_CACHE.put(nextCacheKey, new HashMap<>());
            DAILY_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByDate);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedCountGroupByDate)
                .build();
    }

    @GetMapping("/level")
    public CommonResponse getSolvedCountByLevel(@Auth String userId) {
        LocalDateTime nextCacheKey = getNextCacheKey();

        List<CountByLevel> solvedCountGroupByLevel;
        if(LEVEL_SOLVED_COUNT_CACHE.containsKey(nextCacheKey)) {
            if(LEVEL_SOLVED_COUNT_CACHE.get(nextCacheKey).containsKey(userId)) {
                solvedCountGroupByLevel = (List<CountByLevel>) LEVEL_SOLVED_COUNT_CACHE.get(nextCacheKey).get(userId);
            } else {
                solvedCountGroupByLevel = statService.getSolvedCountGroupByLevel(userId);
                LEVEL_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByLevel);
            }
        } else {
            solvedCountGroupByLevel = statService.getSolvedCountGroupByLevel(userId);
            LEVEL_SOLVED_COUNT_CACHE.entrySet().removeIf(entry -> entry.getKey().isBefore(nextCacheKey));
            LEVEL_SOLVED_COUNT_CACHE.put(nextCacheKey, new HashMap<>());
            LEVEL_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByLevel);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedCountGroupByLevel)
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse getSolvedCountByTag(@Auth String userId) {
        LocalDateTime nextCacheKey = getNextCacheKey();

        List<CountByTag> solvedCountGroupByTag;
        if(TAG_SOLVED_COUNT_CACHE.containsKey(nextCacheKey)) {
            if(TAG_SOLVED_COUNT_CACHE.get(nextCacheKey).containsKey(userId)) {
                solvedCountGroupByTag = (List<CountByTag>) TAG_SOLVED_COUNT_CACHE.get(nextCacheKey).get(userId);
            } else {
                solvedCountGroupByTag = statService.getSolvedCountGroupByTag(userId);
                TAG_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByTag);
            }
        } else {
            solvedCountGroupByTag = statService.getSolvedCountGroupByTag(userId);
            TAG_SOLVED_COUNT_CACHE.entrySet().removeIf(entry -> entry.getKey().isBefore(nextCacheKey));
            TAG_SOLVED_COUNT_CACHE.put(nextCacheKey, new HashMap<>());
            TAG_SOLVED_COUNT_CACHE.get(nextCacheKey).put(userId, solvedCountGroupByTag);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedCountGroupByTag)
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse getSolvedHistories(@Auth String userId, @RequestParam Integer offset) {
        LocalDateTime nextCacheKey = getNextCacheKey();

        SolvedHistoriesByUserId solvedHistoriesByUserId;
        if(SOLVED_HISTORIES_CACHE.containsKey(nextCacheKey)) {
            if(SOLVED_HISTORIES_CACHE.get(nextCacheKey).containsKey(userId + "/" + offset)) {
                solvedHistoriesByUserId = (SolvedHistoriesByUserId) SOLVED_HISTORIES_CACHE.get(nextCacheKey).get(userId + "/" + offset);
            } else {
                solvedHistoriesByUserId = statService.getSolvedHistoriesByUserId(userId, offset);
                SOLVED_HISTORIES_CACHE.get(nextCacheKey).put(userId + "/" + offset, solvedHistoriesByUserId);
            }
        } else {
            solvedHistoriesByUserId = statService.getSolvedHistoriesByUserId(userId, offset);
            SOLVED_HISTORIES_CACHE.entrySet().removeIf(entry -> entry.getKey().isBefore(nextCacheKey));
            SOLVED_HISTORIES_CACHE.put(nextCacheKey, new HashMap<>());
            SOLVED_HISTORIES_CACHE.get(nextCacheKey).put(userId + "/" + offset, solvedHistoriesByUserId);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedHistoriesByUserId)
                .build();
    }
}
