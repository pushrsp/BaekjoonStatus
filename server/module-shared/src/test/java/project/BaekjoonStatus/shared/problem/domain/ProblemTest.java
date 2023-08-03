package project.BaekjoonStatus.shared.problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ProblemTest {
    @DisplayName("Problem도메인은 builder로만 만들 수 있다.")
    @Test
    public void can_create_problem_domain_with_only_builder() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 3, 22, 23);

        //when
        Problem problem = Problem.builder()
                .id(1L)
                .title("test")
                .level(1)
                .createdTime(now)
                .build();

        //then
        assertThat(problem.getId()).isNotNull();
        assertThat(problem.getCreatedTime()).isEqualTo(now);
    }
}
