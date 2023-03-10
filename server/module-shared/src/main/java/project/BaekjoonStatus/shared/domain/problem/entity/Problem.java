package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<ProblemTag> problemTags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(name = "modified_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    private void setProblemTag(ProblemTag problemTag) {
        this.problemTags.add(problemTag);
        problemTag.setProblem(this);
    }

    private Problem(SolvedAcProblemResp info, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = info.getProblemId();
        this.level = info.getLevel().intValue();
        this.title = info.getTitleKo();
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    /* 생성 메서드 */
    public static Problem create(SolvedAcProblemResp info) {
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneId);

        return new Problem(info, now, now);
    }

    public static List<Problem> create(List<SolvedAcProblemResp> problemInfos) {
        List<Problem> problems = new ArrayList<>();
        for (SolvedAcProblemResp info : problemInfos)
            problems.add(Problem.create(info));

        return problems;
    }
}
