package project.BaekjoonStatus.shared.solvedac.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SolvedAcServiceTest {
    private SolvedAcService solvedAcService;

    @BeforeEach
    void setUp() {
        solvedAcService = new SolvedAcService();
    }

    @DisplayName("problem_id를 통해 문제 정보를 얻어올 수 있다.")
    @Test
    public void can_find_problem_info_by_problem_id() throws Exception {
        //given
        Long problemId = 1000L;

        //when
        SolvedAcProblem solvedAcProblem = solvedAcService.findById(problemId);

        //then
        assertThat(solvedAcProblem.getProblemId()).isEqualTo(problemId);
    }

    @DisplayName("주어진 problem_id를 가진 문제의 존재 여부를 감지 할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideWrongProblemId")
    public void can_detect_problem_id_that_is_not_existed(Long wrongId) throws Exception {
        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> solvedAcService.findById(wrongId), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("해당 id를 가진 문제가 존재하지 않습니다.");
    }

    @DisplayName("여러개의 problem_id를 통해 문제에 대한 정보들을 동시에 찾아올 수 있다.")
    @Test
    public void can_find_problem_infos_by_problem_ids() throws Exception {
        //given
        List<String> problemIds = Stream.of(1000L, 2000L, 1002L, 3000L, 10000L)
                .map(String::valueOf)
                .collect(Collectors.toList());

        //when
        List<SolvedAcProblem> problems = solvedAcService.findByIds(problemIds);

        //then
        assertThat(problems).hasSize(problemIds.size());
    }

    @DisplayName("올바르지 않은 problem_id는 무시된다.")
    @ParameterizedTest
    @MethodSource("provideWrongProblemIds")
    public void can_ignore_wrong_problem_id(List<String> wrongProblemIds) throws Exception {
        //when
        List<SolvedAcProblem> problems = solvedAcService.findByIds(wrongProblemIds);

        //then
        assertThat(problems).isEmpty();
    }

    private static Stream<Arguments> provideWrongProblemId() {
        return Stream.of(
                Arguments.of(-1L),
                Arguments.of(100000L)
        );
    }

    private static Stream<Arguments> provideWrongProblemIds() {
        return Stream.of(
                Arguments.of(List.of("-1", "1000000")),
                Arguments.of(List.of("-1", "-1"))
        );
    }
}
