package project.BaekjoonStatus.shared.problem.infra;

import lombok.*;
import org.springframework.data.domain.Persistable;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemEntity implements Persistable<Long> {
    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "title", length = 1000)
    private String title;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TagEntity> tags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Transient
    private boolean updated;

    @Builder
    private ProblemEntity(Long id, Integer level, String title, List<TagEntity> tags, LocalDateTime createdTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.tags = tags;
        this.createdTime = createdTime;
    }

    public static ProblemEntity from(Problem problem) {
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.id = problem.getId();
        problemEntity.level = problem.getLevel();
        problemEntity.title = problem.getTitle();
        problemEntity.createdTime = DateProvider.getDateTime();
        problemEntity.updated = false;

        return problemEntity;
    }

    public Problem to() {
        return Problem.builder()
                .id(this.id)
                .level(this.level)
                .title(this.title)
                .createdTime(this.createdTime)
                .build();
    }

    @Override
    public boolean isNew() {
        return !updated;
    }
}
