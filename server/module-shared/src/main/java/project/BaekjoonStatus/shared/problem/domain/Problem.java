package project.BaekjoonStatus.shared.problem.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Problem implements Serializable {
    private final Long id;
    private final Integer level;
    private final String title;
    private final List<Tag> tags;
    private final LocalDateTime createdTime;

    @Builder
    private Problem(Long id, Integer level, String title, List<Tag> tags, LocalDateTime createdTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.tags = tags;
        this.createdTime = createdTime;
    }
}
