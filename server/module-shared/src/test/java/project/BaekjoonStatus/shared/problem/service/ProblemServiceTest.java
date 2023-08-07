package project.BaekjoonStatus.shared.problem.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemServiceTest extends IntegrationTestSupport {
    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("Problem도메인을 통해 Problem를 생성할 수 있다.")
    @Test
    public void can_create_problem_from_ProblemDomain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 11, 21);
        Problem problemDomain = createProblemDomain(1000L, "test", now);

        //when
        Problem problem = problemService.save(problemDomain);

        //then
        assertThat(problem.getCreatedTime()).isEqualTo(problemDomain.getCreatedTime());
        assertThat(problem.getId()).isEqualTo(problemDomain.getId());
    }

    private Problem createProblemDomain(Long id, String title, LocalDateTime createdTime) {
        return Problem.builder()
                .id(id)
                .level(1)
                .title(title)
                .createdTime(createdTime)
                .build();
    }
}
