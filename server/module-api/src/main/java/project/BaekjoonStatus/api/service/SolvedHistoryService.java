package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryReadService;

import java.util.List;

import static project.BaekjoonStatus.api.dto.SolvedHistoryDto.*;
import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    private final SolvedHistoryReadService solvedHistoryReadService;

    public List<SolvedHistoryResp> getSolvedHistories(SolvedHistoriesReq data) {
        return solvedHistoryReadService.findSolvedHistories(data.getUserId(), data.getOffset());
    }
}
