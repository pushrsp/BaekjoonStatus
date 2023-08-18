package project.BaekjoonStatus.shared.problem.service.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    public static boolean hasDuplicateId(List<ProblemCreateSharedServiceRequest> requests) {
        long count = requests.stream()
                .map(ProblemCreateSharedServiceRequest::getId)
                .distinct()
                .count();

        return requests.size() != count;
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
