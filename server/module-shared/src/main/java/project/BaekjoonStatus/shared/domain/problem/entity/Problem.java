package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private int level;

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

    public Problem(Long id, int level, String title, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public void setProblemTag(ProblemTag problemTag) {
        this.problemTags.add(problemTag);
        problemTag.setProblem(this);
    }

    /* 생성 메서드 */
    public static Problem create(Long problemId, int level, String title, List<ProblemTag> problemTags) {
        ZoneId utc = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(utc);

        Problem problem = new Problem(problemId,level, title, now, now);
        for (ProblemTag problemTag : problemTags)
            problem.setProblemTag(problemTag);

        return problem;
    }
}
