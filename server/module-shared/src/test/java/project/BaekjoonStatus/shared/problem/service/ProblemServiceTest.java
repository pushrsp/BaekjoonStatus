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
import java.util.*;
import java.util.stream.Collectors;

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

    @DisplayName("여러개의 Problem도메인을 통해 여러개의 Problem을 생성할 수 있다.")
    @Test
    public void can_create_list_of_problem_from_list_of_ProblemDomain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 11, 21);
        Long[] ids = {1000L, 1001L, 2000L, 3000L, 4000L, 5000L, 6000L};
        List<Problem> problemDomains = createProblemDomains(ids, now);

        //when
        problemService.saveAll(problemDomains);

        //then
        List<Problem> problems = problemService.findAllByIdsIn(Arrays.stream(ids).collect(Collectors.toList()));

        assertThat(problems).hasSize(ids.length);
        assertThat(problems).extracting("id").containsExactlyInAnyOrder(ids);
    }

    @DisplayName("ProblemId를 통해 Problem을 찾을 수 있다.")
    @Test
    public void can_find_problem_by_id() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 11, 21);
        Long[] ids = {1000L, 1001L, 2000L, 3000L, 4000L, 5000L, 6000L};
        List<Problem> problemDomains = createProblemDomains(ids, now);

        problemService.saveAll(problemDomains);

        //when
        Optional<Problem> p1 = problemService.findById(ids[0]);
        Optional<Problem> p2 = problemService.findById(0L);

        //then
        assertThat(p1.isPresent()).isTrue();
        assertThat(p1.get().getId()).isEqualTo(ids[0]);

        assertThat(p2.isPresent()).isFalse();
    }

    private List<Problem> createProblemDomains(Long[] ids, LocalDateTime createdTime) {
        List<Problem> ret = new ArrayList<>();
        for (Long id : ids) {
            ret.add(createProblemDomain(id, "title " + id, createdTime));
        }

        return ret;
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
