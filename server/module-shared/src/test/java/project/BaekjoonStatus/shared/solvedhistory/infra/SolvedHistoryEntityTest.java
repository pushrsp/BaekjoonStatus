package project.BaekjoonStatus.shared.solvedhistory.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDateTime;

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
