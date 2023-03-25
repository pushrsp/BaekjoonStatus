package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.argumentresolver.Auth;
import project.BaekjoonStatus.api.service.StatService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private final StatService statService;

    @GetMapping("/daily")
    public CommonResponse getDailyProblems() {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getDailyProblems())
                .build();
    }

    @GetMapping("/date")
    public CommonResponse getDailySolvedCount(@RequestParam String year, @Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByDate(userId, year))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse getSolvedCountByLevel(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByLevel(userId))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse getSolvedCountByTag(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByTag(userId))
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse getSolvedHistories(@Auth String userId, @RequestParam Integer offset) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedHistoriesByUserId(userId, offset))
                .build();
    }
}
