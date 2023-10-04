package project.BaekjoonStatus.api.stat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.common.argumentresolver.Auth;
import project.BaekjoonStatus.api.stat.controller.response.*;
import project.BaekjoonStatus.api.stat.service.StatService;
import project.BaekjoonStatus.shared.common.response.CommonResponse;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.service.DateService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private static final int PAGE_SIZE = 10;

    private final StatService statService;
    private final DateService dateService;

    @GetMapping("/daily")
    public CommonResponse findTodayProblems() {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(TodayProblemsResponse.from(statService.findTodayProblems()))
                .build();
    }

    @GetMapping("/date")
    public CommonResponse findDailySolvedCount(@RequestParam String year, @Auth String memberId) {
        if(year.isEmpty()) {
            year = String.valueOf(dateService.getDate().getYear());
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByDateResponse.from(statService.findSolvedCountGroupByDate(memberId, year)))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse findSolvedCountByLevel(@Auth String memberId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByTierResponse.from(statService.findSolvedCountGroupByLevel(memberId)))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse findSolvedCountByTag(@Auth String memberId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByTagResponse.from(statService.findSolvedCountGroupByTag(memberId)))
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse findSolvedHistories(@Auth String memberId, @RequestParam Integer offset) {
        if(Objects.isNull(offset)) {
            offset = 0;
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(SolvedHistoryByUserIdResponse.from(statService.findSolvedHistoriesByUserId(memberId, offset), PAGE_SIZE))
                .build();
    }
}
