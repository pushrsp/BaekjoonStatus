package project.BaekjoonStatus.shared.domain.dailyproblem.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.util.DateProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "DAILY_PROBLEM", indexes = {
        @Index(name = "idx__created_date", columnList = "created_date")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyProblem {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "solved_history_id")
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    public DailyProblem(Problem problem, LocalDate createdDate) {
        this.problem = problem;
        this.createdDate = createdDate;
    }
}
