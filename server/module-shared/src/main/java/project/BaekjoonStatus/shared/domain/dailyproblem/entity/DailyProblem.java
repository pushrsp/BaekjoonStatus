package project.BaekjoonStatus.shared.domain.dailyproblem.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "DAILY_PROBLEM")
@Data
public class DailyProblem {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "daily_problem_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;
}
