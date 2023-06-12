package project.BaekjoonStatus.shared.solvedhistory.infra;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.infra.UserEntity;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class SolvedHistoryEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "solved_history_id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Type(type = "uuid-char")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private ProblemEntity problem;

    @Column(name = "is_before", nullable = false)
    private Boolean isBefore;

    @Column(name = "problem_level", nullable = false)
    private Integer problemLevel;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private SolvedHistoryEntity(UserEntity user, ProblemEntity problem, Boolean isBefore, LocalDate createdDate, LocalDateTime createdTime) {
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problem.getLevel();
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    private SolvedHistoryEntity(UserEntity user, ProblemEntity problem, Boolean isBefore, LocalDate createdDate) {
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problem.getLevel();
        this.createdDate = createdDate;
        this.createdTime = DateProvider.getDateTime();
    }

    public static SolvedHistoryEntity from(SolvedHistory solvedHistory) {
        SolvedHistoryEntity solvedHistoryEntity = new SolvedHistoryEntity();
        solvedHistoryEntity.user = UserEntity.from(solvedHistory.getUser());
        solvedHistoryEntity.problem = ProblemEntity.from(solvedHistory.getProblem());
        solvedHistoryEntity.isBefore = solvedHistory.getIsBefore();
        solvedHistoryEntity.problemLevel = solvedHistory.getProblemLevel();
        solvedHistoryEntity.createdDate = solvedHistory.getCreatedDate();
        solvedHistoryEntity.createdTime = solvedHistory.getCreatedTime();

        return solvedHistoryEntity;
    }

    public SolvedHistory to() {
        return SolvedHistory.builder()
                .id(this.id.toString())
                .user(this.user.to())
                .problem(this.problem.to())
                .isBefore(this.isBefore)
                .problemLevel(this.problemLevel)
                .createdDate(this.createdDate)
                .createdTime(this.createdTime)
                .build();
    }

    public static SolvedHistoryEntity ofWithUserAndProblem(UserEntity user, ProblemEntity problem, boolean isBefore) {
        return new SolvedHistoryEntity(user, problem, isBefore, DateProvider.getDate());
    }

    public static SolvedHistoryEntity ofWithUserAndProblem(UserEntity user, ProblemEntity problem, boolean isBefore, LocalDate createdDate, LocalDateTime createdTime) {
        return new SolvedHistoryEntity(user, problem, isBefore, createdDate, createdTime);
    }

    public static List<SolvedHistoryEntity> ofWithUserAndProblems(UserEntity user, List<ProblemEntity> problems, boolean isBefore) {
        return problems.stream()
                .map(p -> SolvedHistoryEntity.ofWithUserAndProblem(user, p, isBefore))
                .collect(Collectors.toList());
    }

}
