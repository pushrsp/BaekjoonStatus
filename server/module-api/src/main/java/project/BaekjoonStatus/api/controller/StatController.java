package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.BaekjoonStatus.api.dto.StatDto.SolvedCountDto;
import project.BaekjoonStatus.api.service.StatService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController {
    private final StatService statService;

    @GetMapping("/daily")
    public CommonResponse getDailySolvedCount(@RequestBody @Valid SolvedCountDto body) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByDate(body))
                .build();
    }

    @GetMapping("/level")
    public CommonResponse getSolvedCountByLevel(@RequestBody @Valid SolvedCountDto body) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByLevel(body))
                .build();
    }

    @GetMapping("/tag")
    public CommonResponse getSolvedCountByTag(@RequestBody @Valid SolvedCountDto body) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(statService.getSolvedCountGroupByTag(body))
                .build();
    }
}
