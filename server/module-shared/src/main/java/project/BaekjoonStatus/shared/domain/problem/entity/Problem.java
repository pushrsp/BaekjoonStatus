package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.dto.command.ProblemCommand;

import javax.persistence.*;
import java.time.LocalDate;
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

    private Problem(ProblemCommand problemCommand, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = problemCommand.getProblemId();
        this.level = problemCommand.getLevel();
        this.title = problemCommand.getTitle();
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    /* 생성 메서드 */
    public static Problem create(ProblemCommand problemCommand, List<ProblemTag> problemTags) {
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneId);

        return new Problem(problemCommand, now, now);
    }

    public static List<Problem> create(List<ProblemCommand> problemCommands) {
        List<Problem> problems = new ArrayList<>();
        for (ProblemCommand problemCommand : problemCommands)
            problems.add(Problem.create(problemCommand, ProblemTag.create(problemCommand.getTags())));

        return problems;
    }
}
