package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.BaekjoonStatus.api.dto.SolvedHistoryDto.SolvedHistoriesReq;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solved-histories")
public class SolvedHistoryController {
    private final SolvedHistoryService solvedHistoryService;

    @GetMapping("")
    public CommonResponse getSolvedHistories(@RequestBody @Valid SolvedHistoriesReq body) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedHistoryService.findSolvedHistories(body.getUserId(), body.getOffset()))
                .build();
    }
}
