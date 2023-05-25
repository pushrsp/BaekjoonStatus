package project.BaekjoonStatus.shared.domain.solvedhistory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.util.DateProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "SOLVED_HISTORY", indexes = {
        @Index(name = "idx__user_id__problem_level", columnList = "user_id, problem_level"),
        @Index(name = "idx__user_id__created_date", columnList = "user_id, created_date"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedHistory {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "solved_history_id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Type(type = "uuid-char")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @Column(name = "is_before", nullable = false)
    private Boolean isBefore;

    @Column(name = "problem_level", nullable = false)
    private Integer problemLevel;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private SolvedHistory(User user, Problem problem, Boolean isBefore, LocalDate createdDate, LocalDateTime createdTime) {
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problem.getLevel();
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    private SolvedHistory(User user, Problem problem, Boolean isBefore, LocalDate createdDate) {
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problem.getLevel();
        this.createdDate = createdDate;
        this.createdTime = DateProvider.getDateTime();
    }

    public static SolvedHistory ofWithUserAndProblem(User user, Problem problem, boolean isBefore) {
        return new SolvedHistory(user, problem, isBefore, DateProvider.getDate());
    }

    public static SolvedHistory ofWithUserAndProblem(User user, Problem problem, boolean isBefore, LocalDate createdDate, LocalDateTime createdTime) {
        return new SolvedHistory(user, problem, isBefore, createdDate, createdTime);
    }

    public static List<SolvedHistory> ofWithUserAndProblems(User user, List<Problem> problems, boolean isBefore) {
        return problems.stream()
                .map(p -> SolvedHistory.ofWithUserAndProblem(user, p, isBefore))
                .collect(Collectors.toList());
    }

}
