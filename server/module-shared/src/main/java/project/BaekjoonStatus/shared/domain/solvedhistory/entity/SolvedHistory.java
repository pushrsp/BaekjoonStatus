package project.BaekjoonStatus.shared.domain.solvedhistory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SOLVED_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedHistory {
    @Id
    @GenericGenerator(
            name = "SequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "100")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Type(type = "uuid-char")
    private User user;

    @OneToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @Column(name = "is_before", nullable = false)
    private boolean isBefore;

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private SolvedHistory(User user, Problem problem, boolean isBefore) {
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.createdTime = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public static SolvedHistory create(User user, Problem problem, boolean isBefore) {
        return new SolvedHistory(user, problem, isBefore);
    }

    public static List<SolvedHistory> create(User user, List<Problem> problems, boolean isBefore) {
        List<SolvedHistory> solvedHistories = new ArrayList<>();
        for (Problem problem : problems)
            solvedHistories.add(SolvedHistory.create(user, problem, isBefore));

        return solvedHistories;
    }

}
