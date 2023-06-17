package project.BaekjoonStatus.shared.dailyproblem.infra;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "DAILY_PROBLEM", indexes = {
        @Index(name = "idx__created_date", columnList = "created_date DESC")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyProblemEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "daily_problem_id")
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private ProblemEntity problem;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    public static DailyProblemEntity from(DailyProblem dailyProblem) {
        DailyProblemEntity dailyProblemEntity = new DailyProblemEntity();
        dailyProblemEntity.problem = ProblemEntity.from(dailyProblem.getProblem());
        dailyProblemEntity.createdDate = dailyProblem.getCreatedDate();

        return dailyProblemEntity;
    }

    public DailyProblem to() {
        return DailyProblem.builder()
                .id(this.id.toString())
                .problem(this.problem.to())
                .createdDate(this.createdDate)
                .build();
    }
}
