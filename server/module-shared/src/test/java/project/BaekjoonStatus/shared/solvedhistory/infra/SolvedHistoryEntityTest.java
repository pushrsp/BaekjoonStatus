package project.BaekjoonStatus.shared.solvedhistory.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.infra.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SolvedHistoryEntityTest {
    @DisplayName("SolvedHistory도메인으로부터 SolvedHistory엔티티를 생성할 수 있다.")
    @Test
    public void can_create_solved_history_entity_from_solved_history_domain() throws Exception {
        //given
        User userDomain = createUserDomain();
        Problem problemDomain = createProblemDomain();
        SolvedHistory solvedHistoryDomain = createSolvedHistoryDomain(userDomain, problemDomain);

        //when
        SolvedHistoryEntity solvedHistoryEntity = SolvedHistoryEntity.from(solvedHistoryDomain);

        //then
        assertThat(solvedHistoryEntity.getUser().getId()).isEqualTo(solvedHistoryDomain.getUser().getId());
        assertThat(solvedHistoryEntity.getProblem().getId()).isEqualTo(solvedHistoryDomain.getProblem().getId());
        assertThat(solvedHistoryEntity.getCreatedDate()).isEqualTo(solvedHistoryDomain.getCreatedDate());
        assertThat(solvedHistoryEntity.getCreatedTime()).isEqualTo(solvedHistoryDomain.getCreatedTime());
    }

    @DisplayName("DailyProblem엔티티는 DailyProblem도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_daily_problem_domain_from_daily_problem_entity() throws Exception {
        //given
        UserEntity userEntity = createUserEntity();
        ProblemEntity problemEntity = createProblemEntity();
        SolvedHistoryEntity solvedHistoryEntity = createSolvedHistoryEntity(userEntity, problemEntity);

        //when
        SolvedHistory solvedHistoryDomain = solvedHistoryEntity.to();

        //then
        assertThat(solvedHistoryDomain.getId()).isEqualTo(solvedHistoryEntity.getId().toString());
        assertThat(solvedHistoryDomain.getUser().getId()).isEqualTo(solvedHistoryEntity.getUser().getId());
        assertThat(solvedHistoryDomain.getProblem().getId()).isEqualTo(solvedHistoryEntity.getProblem().getId());
    }

    private SolvedHistoryEntity createSolvedHistoryEntity(UserEntity user, ProblemEntity problem) {
        return SolvedHistoryEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .problem(problem)
                .isBefore(true)
                .problemLevel(1)
                .createdTime(LocalDateTime.now())
                .createdDate(LocalDate.now())
                .build();
    }

    private ProblemEntity createProblemEntity() {
        return ProblemEntity.builder()
                .id(1L)
                .level(1)
                .title("test")
                .createdTime(LocalDateTime.now())
                .build();
    }

    private UserEntity createUserEntity() {
        LocalDateTime now = LocalDateTime.now();
        return UserEntity.builder()
                .id(1L)
                .username("username")
                .password("password")
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }

    private SolvedHistory createSolvedHistoryDomain(User user, Problem problem) {
        LocalDateTime now = LocalDateTime.now();
        return SolvedHistory.builder()
                .user(user)
                .problem(problem)
                .isBefore(true)
                .problemLevel(1)
                .createdTime(now)
                .createdDate(now.toLocalDate())
                .build();
    }

    private User createUserDomain() {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .username("test")
                .password("test")
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }

    private Problem createProblemDomain() {
        return Problem.builder()
                .id(1L)
                .title("test")
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
