package project.BaekjoonStatus.shared.dailyproblem.infra;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "DAILY_PROBLEM", indexes = {
        @Index(name = "idx__created_date", columnList = "created_date")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyProblemEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "solved_history_id")
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private ProblemEntity problem;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    public DailyProblemEntity(ProblemEntity problem, LocalDate createdDate) {
        validateProblem(problem);
        validateCreatedDate(createdDate);

        this.problem = problem;
        this.createdDate = createdDate;
    }

    private void validateProblem(ProblemEntity problem) {
        Assert.notNull(problem, "문제를 입력해주세요.");
    }

    private void validateCreatedDate(LocalDate createdDate) {
        Assert.notNull(createdDate, "생성 날짜를 입력해주세요.");
    }

    public static DailyProblemEntity ofWithProblem(ProblemEntity problem) {
        return new DailyProblemEntity(problem, DateProvider.getDate());
    }
}
