package project.BaekjoonStatus.shared.solvedhistory.infra;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import project.BaekjoonStatus.shared.member.infra.MemberEntity;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "SOLVED_HISTORY",
        indexes = {
        @Index(name = "idx__user_id", columnList = "user_id"),
        @Index(name = "idx__user_id__problem_level__problem_id", columnList = "user_id, problem_level DESC, problem_id ASC"),
        @Index(name = "idx__user_id__is_before__created_date", columnList = "user_id, is_before, created_date DESC"),
        }
)
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
    private MemberEntity user;

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

    @Builder
    private SolvedHistoryEntity(UUID id, MemberEntity user, ProblemEntity problem, Boolean isBefore, Integer problemLevel, LocalDate createdDate, LocalDateTime createdTime) {
        this.id = id;
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problemLevel;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    public static SolvedHistoryEntity from(SolvedHistory solvedHistory) {
        return SolvedHistoryEntity.builder()
                .user(MemberEntity.from(solvedHistory.getUser()))
                .problem(ProblemEntity.from(solvedHistory.getProblem()))
                .isBefore(solvedHistory.getIsBefore())
                .problemLevel(solvedHistory.getProblemLevel())
                .createdTime(solvedHistory.getCreatedTime())
                .createdDate(solvedHistory.getCreatedDate())
                .build();
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
}
