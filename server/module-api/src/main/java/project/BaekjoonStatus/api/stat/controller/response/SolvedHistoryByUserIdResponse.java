package project.BaekjoonStatus.api.stat.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistoryByMemberId;

import java.util.List;

@Data
@Builder
public class SolvedHistoryByUserIdResponse {
    private Boolean hasNext;
    private List<SolvedHistoryByMemberId> problems;

    public SolvedHistoryByUserIdResponse(Boolean hasNext, List<SolvedHistoryByMemberId> problems) {
        this.hasNext = hasNext;
        this.problems = problems;
    }

    public static SolvedHistoryByUserIdResponse from(List<SolvedHistoryByMemberId> problems, int pageSize) {
        boolean hasNext = problems.size() > pageSize;
        if(hasNext) {
            problems.remove(problems.size() - 1);
        }

        return SolvedHistoryByUserIdResponse.builder()
                .hasNext(hasNext)
                .problems(problems)
                .build();
    }
}
