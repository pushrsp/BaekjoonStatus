package project.BaekjoonStatus.shared.domain.solvedhistory.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SOLVED_HISTORY")
@Data
public class SolvedHistory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "solved_history_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(name = "is_before", nullable = false)
    private boolean isBefore;

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

}
