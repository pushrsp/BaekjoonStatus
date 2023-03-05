package project.BaekjoonStatus.shared.domain.solvedhistory.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SOLVED_HISTORY")
@Data
public class SolvedHistory {

    @Id
    @GeneratedValue
    @Column(name = "solved_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT")
    private User user;

    @OneToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @Column(name = "is_before", nullable = false)
    private boolean isBefore;

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

}
