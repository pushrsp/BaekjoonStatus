package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.argumentresolver.Auth;
import project.BaekjoonStatus.api.dto.StatDto.DailyCountDto;
import project.BaekjoonStatus.api.service.StatService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private final StatService statService;

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
        System.out.println(offset);
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedHistoriesByUserId(userId, offset))
                .build();
    }
}
