package project.BaekjoonStatus.shared.problem.infra;

import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TagEntity> tags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Transient
    private boolean updated;

    private ProblemEntity(Long id, Integer level, String title, LocalDateTime createdTime) {
        validateId(id);
        validateLevel(level);
        validateTitle(title);
        validateCreatedTime(createdTime);

        this.id = id;
        this.level = level;
        this.title = title;
        this.createdTime = createdTime;
        this.updated = false;
    }

    public static ProblemEntity of(Long id, Integer level, String title, LocalDateTime createdTime) {
        return new ProblemEntity(id, level, title, createdTime);
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
                .tags(this.tags.stream().map(TagEntity::to).collect(Collectors.toList()))
                .createdTime(this.createdTime)
                .build();
    }

    public static ProblemEntity ofWithInfo(SolvedAcProblemResponse info) {
        return ProblemEntity.of(info.getProblemId(), info.getLevel().intValue(), info.getTitleKo(), DateProvider.getDateTime());
    }

    public static List<ProblemEntity> ofWithInfos(List<SolvedAcProblemResponse> infos) {
        return infos.stream()
                .map(ProblemEntity::ofWithInfo)
                .collect(Collectors.toList());
    }

    private void validateId(Long id) {
        Assert.notNull(id, "id를 입력해주세요.");
    }

    private void validateLevel(Integer level) {
        Assert.notNull(level, "레벨을 입력해주세요.");
        Assert.isTrue(0 <= level && level <= 30, "레벨은 0이상 30이하의 값만 설정할 수 있습니다.");
    }

    private void validateTitle(String title) {
        Assert.notNull(title, "제목을 입력해주세요.");
        Assert.hasText(title, "제목을 입력해주세요.");
    }

    private void validateCreatedTime(LocalDateTime createdTime) {
        Assert.notNull(createdTime, "생성날짜를 입력해주세요.");
    }

    @Override
    public boolean isNew() {
        return !updated;
    }
}
