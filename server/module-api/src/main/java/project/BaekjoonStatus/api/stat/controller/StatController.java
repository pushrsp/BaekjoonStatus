package project.BaekjoonStatus.api.stat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.common.argumentresolver.Auth;
import project.BaekjoonStatus.api.stat.controller.response.*;
import project.BaekjoonStatus.api.stat.service.StatServiceProxy;
import project.BaekjoonStatus.shared.common.controller.response.CommonResponse;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private static final int PAGE_SIZE = 10;

    private final StatServiceProxy statServiceProxy;

    @GetMapping("/daily")
    public CommonResponse findTodayProblems(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(TodayProblemsResponse.from(statServiceProxy.findTodayProblems(userId)))
                .build();
    }

    @GetMapping("/date")
    public CommonResponse findDailySolvedCount(@RequestParam String year, @Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByDateResponse.from(statServiceProxy.findSolvedCountGroupByDate(userId, year)))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse findSolvedCountByLevel(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByTierResponse.from(statServiceProxy.findSolvedCountGroupByLevel(userId)))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse findSolvedCountByTag(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(GroupByTagResponse.from(statServiceProxy.findSolvedCountGroupByTag(userId)))
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse findSolvedHistories(@Auth String userId, @RequestParam Integer offset) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(SolvedHistoryByUserIdResponse.from(statServiceProxy.findSolvedHistoriesByUserId(userId, offset), PAGE_SIZE))
                .build();
    }
}
