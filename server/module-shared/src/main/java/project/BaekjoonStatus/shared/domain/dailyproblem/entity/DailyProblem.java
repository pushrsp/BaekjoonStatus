package project.BaekjoonStatus.shared.domain.dailyproblem.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "DAILY_PROBLEM")
@Getter
public class DailyProblem {
    @Id
    @GeneratedValue
    @Column(name = "daily_problem_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;
}
