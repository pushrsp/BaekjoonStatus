package project.BaekjoonStatus.shared.dailyproblem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class DailyProblemTest {
    @DisplayName("DailyProblem도메은 Problem도메인, 생성 날짜를 통해 만들 수 있다.")
    @Test
    public void can_create_daily_problem_domain_with_problem_domain_created_date() throws Exception {
        //given
        LocalDate now = LocalDate.of(2023, 8, 3);
        Problem problem = createProblem(1L);

        //when
        DailyProblem dailyProblem = DailyProblem.from(problem, now);

        //then
        assertThat(dailyProblem.getCreatedDate()).isEqualTo(now);
        assertThat(dailyProblem.getProblem()).isNotNull();
    }

    private Problem createProblem(Long id) {
        return Problem.builder()
                .id(id)
                .title("test")
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
