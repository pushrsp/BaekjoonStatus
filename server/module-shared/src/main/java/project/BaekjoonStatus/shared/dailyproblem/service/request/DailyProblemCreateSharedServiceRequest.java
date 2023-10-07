package project.BaekjoonStatus.shared.dailyproblem.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyProblemCreateSharedServiceRequest {
    private String problemId;
    private LocalDate createdDate;

    @Builder
    private DailyProblemCreateSharedServiceRequest(String problemId, LocalDate createdDate) {
        this.problemId = problemId;
        this.createdDate = createdDate;
    }

    public static DailyProblemCreateSharedServiceRequest of(Problem problem, LocalDate createdDate) {
        return DailyProblemCreateSharedServiceRequest.builder()
                .problemId(problem.getId())
                .createdDate(createdDate)
                .build();
    }

    public static List<DailyProblem> toDomainList(List<DailyProblemCreateSharedServiceRequest> requests) {
        return requests.stream()
                .map(DailyProblemCreateSharedServiceRequest::toDomain)
                .collect(Collectors.toList());
    }

    public DailyProblem toDomain() {
        return DailyProblem.builder()
                .problem(Problem.of(this.problemId))
                .createdDate(this.createdDate)
                .build();
    }
}
