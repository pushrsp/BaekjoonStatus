package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.argumentresolver.Auth;
import project.BaekjoonStatus.api.proxy.StatServiceProxy;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private final StatServiceProxy statServiceProxy;

    @GetMapping("/daily")
    public CommonResponse getDailyProblems(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statServiceProxy.getDailyProblems(userId))
                .build();
    }

    @GetMapping("/date")
    public CommonResponse getDailySolvedCount(@RequestParam String year, @Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statServiceProxy.getSolvedCountGroupByDate(userId, year))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse getSolvedCountByLevel(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statServiceProxy.getSolvedCountGroupByLevel(userId))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse getSolvedCountByTag(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statServiceProxy.getSolvedCountGroupByTag(userId))
                .build();
    }

    @GetMapping("/solved-histories")
    public CommonResponse getSolvedHistories(@Auth String userId, @RequestParam Integer offset) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statServiceProxy.getSolvedHistoriesByUserId(userId, offset))
                .build();
    }
}
