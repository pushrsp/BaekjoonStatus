package project.BaekjoonStatus.shared.problem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;
import project.BaekjoonStatus.shared.problem.service.request.ProblemCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ProblemServiceTest extends IntegrationTestSupport {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemService problemService;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("ProblemCreateSharedServiceRequest를 통해 problem을 저장할 수 있다.")
    @Test
    public void can_save_problem_by_ProblemCreateSharedServiceRequest() throws Exception {
        //given
        ProblemCreateSharedServiceRequest request = createProblemCreateSharedServiceRequest("1000");

        //when
        Problem problem = problemService.save(request);

        //then
        assertThat(problem.getId()).isEqualTo(request.getId());
        assertThat(problem.getLevel()).isEqualTo(request.getLevel());
        assertThat(problem.getTitle()).isEqualTo(request.getTitle());
        assertThat(problem.getCreatedTime()).isEqualTo(request.getCreatedTime());
    }

    @DisplayName("여러개의 ProblemCreateSharedServiceRequest를 통해 다수의 problem을 동시에 저장할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideIds")
    public void can_save_problems_by_list_of_ProblemCreateSharedServiceRequest(List<String> ids) throws Exception {
        //given
        List<ProblemCreateSharedServiceRequest> requests = ids.stream()
                .map(this::createProblemCreateSharedServiceRequest)
                .collect(Collectors.toList());

        //when
        int size = problemService.saveAll(requests);

        //then
        assertThat(size).isEqualTo(ids.size());
    }

    @DisplayName("problem_id를 통해 problem을 찾을 수 있다.")
    @Test
    public void can_find_problem_by_problem_id() throws Exception {
        //given
        ProblemCreateSharedServiceRequest request = createProblemCreateSharedServiceRequest("20001");
        problemService.save(request);

        //when
        Optional<Problem> optionalProblem = problemService.findById(request.getId());

        //then
        assertThat(optionalProblem.isPresent()).isTrue();
        assertThat(optionalProblem.get().getId()).isEqualTo(request.getId());
    }

    @DisplayName("여러개의 problem_id를 통해 다수의 problem을 동시에 찾을 수 있다.")
    @Test
    public void can_find_problems_by_problem_ids() throws Exception {
        //given
        List<ProblemCreateSharedServiceRequest> requests = createProblemCreateSharedServiceRequests(20);
        problemService.saveAll(requests);

        List<String> ids = requests.stream()
                .map(ProblemCreateSharedServiceRequest::getId)
                .collect(Collectors.toList());

        //when
        List<Problem> problems = problemService.findAllByIdsIn(ids);

        //then
        assertThat(problems).hasSize(requests.size());
    }

    @DisplayName("아직 저장되지 않은 problem을 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideSize")
    public void can_find_problem_that_is_not_saved_yet(int expected) throws Exception {
        //given
        List<ProblemCreateSharedServiceRequest> requests = createProblemCreateSharedServiceRequests(20);
        problemService.saveAll(requests);

        List<String> ids = requests.stream()
                .map(ProblemCreateSharedServiceRequest::getId)
                .collect(Collectors.toList());

        for (int i = 1; i <= expected; i++) {
            ids.add(String.valueOf(requests.size() + i));
        }

        //when
        List<String> problems = problemService.findAllByNotExistedIds(ids);

        //then
        assertThat(problems).hasSize(expected);
    }

    private static Stream<Arguments> provideSize() {
        return Stream.of(
                Arguments.of(3),
                Arguments.of(10),
                Arguments.of(5)
        );
    }

    private static Stream<Arguments> provideIds() {
        return Stream.of(
                Arguments.of(List.of("1000", "10000", "2000")),
                Arguments.of(List.of("1000", "10020", "2000"))
        );
    }

    private List<ProblemCreateSharedServiceRequest> createProblemCreateSharedServiceRequests(int size) {
        List<ProblemCreateSharedServiceRequest> requests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            requests.add(createProblemCreateSharedServiceRequest(String.valueOf(i)));
        }

        return requests;
    }

    private ProblemCreateSharedServiceRequest createProblemCreateSharedServiceRequest(String id) {
        return ProblemCreateSharedServiceRequest.builder()
                .id(id)
                .level(1)
                .title("title")
                .createdTime(LocalDateTime.now())
                .build();
    }
}
