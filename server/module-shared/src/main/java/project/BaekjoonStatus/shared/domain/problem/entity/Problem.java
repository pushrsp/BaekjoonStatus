package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "PROBLEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem implements Persistable<Long> {
    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "title", length = 1000)
    private String title;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Transient
    private boolean updated;

    private Problem(Long id, Integer level, String title, LocalDateTime createdTime) {
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

    public static Problem of(Long id, Integer level, String title, LocalDateTime createdTime) {
        return new Problem(id, level, title, createdTime);
    }

    public static Problem ofWithInfo(SolvedAcProblemResp info) {
        return Problem.of(info.getProblemId(), info.getLevel().intValue(), info.getTitleKo(), DateProvider.getDateTime());
    }

    public static List<Problem> ofWithInfos(List<SolvedAcProblemResp> infos) {
        return infos.stream()
                .map(Problem::ofWithInfo)
                .collect(Collectors.toList());
    }

    private void validateId(Long id) {
        Assert.notNull(id, "id를 입력해주세요.");
    }

    private void validateLevel(Integer level) {
        Assert.notNull(level, "레벨을 입력해주세요.");
    }

    private void validateTitle(String title) {
        Assert.notNull(title, "제목을 입력해주세요.");
    }

    private void validateCreatedTime(LocalDateTime createdTime) {
        Assert.notNull(createdTime, "생성날짜를 입력해주세요.");
    }

    @Override
    public boolean isNew() {
        return !updated;
    }
}
