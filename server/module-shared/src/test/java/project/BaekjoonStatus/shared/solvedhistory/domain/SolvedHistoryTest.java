package project.BaekjoonStatus.shared.solvedhistory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SolvedHistoryTest {
    @DisplayName("SolvedHistory도메인은 User와 Problem을 기준으로 만들어 진다.")
    @Test
    public void can_create_list_of_solved_history() throws Exception {
        //given
        User user = createUser("test", "test");

        Long[] problemIds = {1000L, 2000L, 3000L};
        List<Problem> problems = createProblems(problemIds);

        boolean isBefore = false;

        LocalDate nowDate = LocalDate.of(2023, 8, 3);
        LocalDateTime nowTime = LocalDateTime.of(2023, 8, 3, 2, 2);

        //when
        List<SolvedHistory> solvedHistories = SolvedHistory.from(user, problems, isBefore, nowDate, nowTime);

        //then
        assertThat(solvedHistories).hasSize(problemIds.length);
        assertThat(solvedHistories).extracting("user", "problem").isNotNull();
    }

    private List<Problem> createProblems(Long ...problemIds) {
        List<Problem> problems = new ArrayList<>();
        for(Long problemId: problemIds) {
            problems.add(createProblem(problemId));
        }

        return problems;
    }

    private Problem createProblem(Long problemId) {
        return Problem.builder()
                .id(problemId)
                .title("test")
                .build();
    }

    private User createUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("test")
                .isPrivate(true)
                .createdTime(DateProvider.getDateTime())
                .modifiedTime(DateProvider.getDateTime())
                .build();
    }
}
