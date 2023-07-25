package project.BaekjoonStatus.shared.solvedac.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SolvedAcServiceTest {
    @DisplayName("올바른 아이디로 검색 시 SolvedAcProblem를 반환한다.")
    @Test
    public void search_with_valid_problem_id_return_problem_info() throws Exception {
        //given
        SolvedAcService solvedAcService = new SolvedAcService();
        Long problemId = 1000L;

        //when
        SolvedAcProblem solvedAcProblem = solvedAcService.findById(problemId);

        //then
        assertThat(solvedAcProblem.getProblemId()).isEqualTo(problemId);
    }

    @DisplayName("올바른 문제 아이디가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("providerWrongProblemId")
    public void can_detect_wrong_problem_id_is_not_valid(Long problemId) throws Exception {
        //given
        SolvedAcService solvedAcService = new SolvedAcService();

        //when
        MyException myException = catchThrowableOfType(() -> solvedAcService.findById(problemId), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getMessage());
    }

    @DisplayName("2개 이상의 문제 아이디를 통해 정보를 가져올 수 있다.")
    @Test
    public void can_search_with_problem_ids() throws Exception {
        //given
        SolvedAcService solvedAcService = new SolvedAcService();
        List<Long> problemIds = List.of(1000L, 2000L, 3000L);

        //when
        List<SolvedAcProblem> problems = solvedAcService.findByIds(problemIds);

        //then
        assertThat(problems).hasSize(3);
        assertThat(problems)
                .extracting("problemId")
                .containsExactlyInAnyOrder(
                        1000L,
                        2000L,
                        3000L
                );
    }

    @DisplayName("잘못된 문제 아이디가 있을 시 해당 아이디를 제외하고 응답값이 반환한다.")
    @Test
    public void wrong_problem_ids_is_ignored() throws Exception {
        //given
        SolvedAcService solvedAcService = new SolvedAcService();
        List<Long> problemIds = List.of(1000L, -1L, 999999999L);

        //when
        List<SolvedAcProblem> problems = solvedAcService.findByIds(problemIds);

        //then
        assertThat(problems).hasSize(1);
        assertThat(problems)
                .extracting("problemId")
                .containsExactlyInAnyOrder(1000L);
    }

    private static Stream<Arguments> providerWrongProblemId() {
        return Stream.of(
                Arguments.of(-1L),
                Arguments.of(99999999L)
        );
    }
}
