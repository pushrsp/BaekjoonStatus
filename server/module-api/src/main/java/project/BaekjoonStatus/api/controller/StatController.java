package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.argumentresolver.Auth;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId;
import project.BaekjoonStatus.api.dto.StatDto.SolvedHistoriesByUserId.Problem;
import project.BaekjoonStatus.api.service.StatService;
import project.BaekjoonStatus.api.template.cache.CacheTemplate;
import project.BaekjoonStatus.api.template.cache.MapCacheTemplate;
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
    private static final CacheTemplate<List<Problem>> DAILY_PROBLEMS_CACHE = new CacheTemplate<>();
    private static final MapCacheTemplate<String, List<CountByDate>> DAILY_SOLVED_COUNT_CACHE = new MapCacheTemplate<>();
    private static final MapCacheTemplate<String, List<CountByLevel>> LEVEL_SOLVED_COUNT_CACHE = new MapCacheTemplate<>();
    private static final MapCacheTemplate<String, List<CountByTag>> TAG_SOLVED_COUNT_CACHE = new MapCacheTemplate<>();
    private static final MapCacheTemplate<String, SolvedHistoriesByUserId> SOLVED_HISTORIES_CACHE = new MapCacheTemplate<>();

    private final StatService statService;

    @GetMapping("/daily")
    public CommonResponse getDailyProblems() {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(DAILY_PROBLEMS_CACHE.get(getNextCacheKey(), statService::getDailyProblems))
                .build();
    }

    @GetMapping("/date")
    public CommonResponse getDailySolvedCount(@RequestParam String year, @Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(DAILY_SOLVED_COUNT_CACHE.get(getNextCacheKey(), userId, () -> statService.getSolvedCountGroupByDate(userId, year)))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse getSolvedCountByLevel(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(LEVEL_SOLVED_COUNT_CACHE.get(getNextCacheKey(), userId, () -> statService.getSolvedCountGroupByLevel(userId)))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse getSolvedCountByTag(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(TAG_SOLVED_COUNT_CACHE.get(getNextCacheKey(), userId, () -> statService.getSolvedCountGroupByTag(userId)))
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse getSolvedHistories(@Auth String userId, @RequestParam Integer offset) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(SOLVED_HISTORIES_CACHE.get(getNextCacheKey(), userId + "/" + offset, () -> statService.getSolvedHistoriesByUserId(userId, offset)))
                .build();
    }

    private LocalDateTime getNextCacheKey() {
        LocalDateTime now = DateProvider.getDateTime();
        LocalDateTime next = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 5,10,0);
        if(now.isBefore(next))
            return next;

        return next.plusDays(1);
    }
}
