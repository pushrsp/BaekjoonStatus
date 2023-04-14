package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Problem(SolvedAcProblemResp info, LocalDateTime createdTime) {
        this.id = info.getProblemId();
        this.level = info.getLevel().intValue();
        this.title = info.getTitleKo();
        this.createdTime = createdTime;
    }

    /* 생성 메서드 */
    public static Problem create(SolvedAcProblemResp info) {
        LocalDateTime now = DateProvider.getDateTime();

        return new Problem(info, now);
    }

    public static List<Problem> create(List<SolvedAcProblemResp> infos) {
        List<Problem> problems = new ArrayList<>();
        for (SolvedAcProblemResp info : infos)
            problems.add(Problem.create(info));

        return problems;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
