package project.BaekjoonStatus.shared.problem.service.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ProblemCreateSharedServiceRequest {
    private String id;
    private Integer level;
    private String title;
    private LocalDateTime createdTime;

    @Builder
    private ProblemCreateSharedServiceRequest(String id, Integer level, String title, LocalDateTime createdTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.createdTime = createdTime;
    }

    public static List<ProblemCreateSharedServiceRequest> from(List<Problem> problems) {
        return problems.stream()
                .map(ProblemCreateSharedServiceRequest::from)
                .collect(Collectors.toList());
    }

    public static ProblemCreateSharedServiceRequest from(Problem problem) {
        return ProblemCreateSharedServiceRequest.builder()
                .id(problem.getId())
                .level(problem.getLevel())
                .title(problem.getTitle())
                .createdTime(problem.getCreatedTime())
                .build();
    }

    public static ProblemCreateSharedServiceRequest from(SolvedAcProblem solvedAcProblem, LocalDateTime createdTime) {
        return ProblemCreateSharedServiceRequest.builder()
                .id(String.valueOf(solvedAcProblem.getProblemId()))
                .level(solvedAcProblem.getLevel().intValue())
                .title(solvedAcProblem.getTitleKo())
                .createdTime(createdTime)
                .build();
    }

    public static boolean hasDuplicateId(List<ProblemCreateSharedServiceRequest> requests) {
        long count = requests.stream()
                .map(ProblemCreateSharedServiceRequest::getId)
                .distinct()
                .count();

        return requests.size() != count;
    }

    public static List<Problem> toDomainList(List<ProblemCreateSharedServiceRequest> requests) {
        return requests.stream()
                .map(ProblemCreateSharedServiceRequest::toDomain)
                .collect(Collectors.toList());
    }

    private void verifyId() {
        if(!StringUtils.hasText(this.id))
            throw new IllegalStateException("id를 입력해주세요.");
    }

    private void verifyLevel() {
        if(Objects.isNull(this.level))
            throw new IllegalStateException("level을 입력해주세요.");

        if(level < 0 || level > 70)
            throw new IllegalStateException("level은 0~70 사이의 범위만 가져야 합니다.");
    }

    private void verifyTitle() {
        if(!StringUtils.hasText(this.title))
            throw new IllegalStateException("title을 입력해주세요.");
    }

    private void verifyCreatedTime() {
        if(Objects.isNull(this.createdTime))
            throw new IllegalStateException("createdTime을 입력해주세요.");
    }

    private void verify() {
        verifyId();
        verifyLevel();
        verifyTitle();
        verifyCreatedTime();
    }

    public Problem toDomain() {
        verify();

        return Problem.builder()
                .id(this.id)
                .level(this.level)
                .title(this.title)
                .createdTime(this.createdTime)
                .build();
    }
}
