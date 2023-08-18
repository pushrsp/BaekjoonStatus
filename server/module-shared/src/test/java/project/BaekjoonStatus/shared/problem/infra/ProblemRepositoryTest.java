package project.BaekjoonStatus.shared.problem.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ProblemRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("여러개의 problem을 동시에 저장할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideSize")
    public void can_save_list_of_problems(int givenSize) throws Exception {
        //given
        List<Problem> problems = createProblems(givenSize);

        //when
        int size = problemRepository.saveAll(problems);

        //then
        assertThat(size).isEqualTo(givenSize);
    }

    private static Stream<Arguments> provideSize() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(3),
                Arguments.of(7)
        );
    }

    @DisplayName("problem을 저장할 수 있다.")
    @Test
    public void can_save_problem() throws Exception {
        //given
        Problem problem = createProblem(1000L, "test");

        //when
        Problem result = problemRepository.save(problem);

        //then
        assertThat(result.getId()).isEqualTo(problem.getId());
        assertThat(result.getLevel()).isEqualTo(problem.getLevel());
        assertThat(result.getTitle()).isEqualTo(problem.getTitle());
        assertThat(result.getCreatedTime()).isEqualTo(problem.getCreatedTime());
    }

    @DisplayName("여러개의 problem을 여러개의 problem_id를 통해 동시에 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideProblemIds")
    public void can_find_list_of_problems_by_list_of_problem_id(List<Long> problemIds) throws Exception {
        //given
        List<Problem> problems = createProblems(problemIds);
        problemRepository.saveAll(problems);

        //when
        List<Problem> result = problemRepository.findAllByIdsIn(problemIds);

        //then
        assertThat(result).hasSize(problemIds.size());
    }

    @DisplayName("problem_id를 통해 problem을 찾을 수 있다.")
    @Test
    public void can_find_problem_by_problem_id() throws Exception {
        //given
        Problem problem = createProblem(1000L, "title");
        problemRepository.save(problem);

        //when
        Optional<Problem> optionalProblem = problemRepository.findById(problem.getId());

        //then
        assertThat(optionalProblem.isPresent()).isTrue();
        assertThat(optionalProblem.get().getId()).isEqualTo(problem.getId());
    }

    private static Stream<Arguments> provideProblemIds() {
        return Stream.of(
                Arguments.of(List.of(1000L)),
                Arguments.of(List.of(1000L, 1001L, 2002L, 2000L, 3000L)),
                Arguments.of(List.of(1000L, 5000L, 100L, 2555L, 1222L, 3432L, 2132L, 999L))
        );
    }

    private List<Problem> createProblems(List<Long> problemIds) {
        return problemIds.stream()
                .map(id -> createProblem(id, "title: " + id))
                .collect(Collectors.toList());
    }

    private List<Problem> createProblems(int size) {
        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            problems.add(createProblem((long) i,"test " + i));
        }

        return problems;
    }

    private Problem createProblem(Long id, String title) {
        return Problem.builder()
                .id(id)
                .title(title)
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
