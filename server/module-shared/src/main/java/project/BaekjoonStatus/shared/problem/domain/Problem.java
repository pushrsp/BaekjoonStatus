package project.BaekjoonStatus.shared.problem.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Problem {
    private final Long id;
    private final Integer level;
    private final String title;
    private final List<Integer> tags;
    private final LocalDateTime createdTime;

    @Builder
    public Problem(Long id, Integer level, String title, List<Integer> tags, LocalDateTime createdTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.tags = tags;
        this.createdTime = createdTime;
    }
}