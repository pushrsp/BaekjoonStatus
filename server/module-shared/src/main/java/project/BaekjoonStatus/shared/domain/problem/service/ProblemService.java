package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProblemService {
    public Problem create(Long id, int level, String title) {
        return new Problem(id, level, title, DateProvider.getDateTime());
    }

    public Problem create(SolvedAcProblemResp info) {
        return create(info.getProblemId(), info.getLevel().intValue(), info.getTitleKo());
    }

    public List<Problem> create(List<SolvedAcProblemResp> infos) {
        List<Problem> ret = new ArrayList<>();
        for (SolvedAcProblemResp info : infos)
            ret.add(create(info));

        return ret;
    }
}
