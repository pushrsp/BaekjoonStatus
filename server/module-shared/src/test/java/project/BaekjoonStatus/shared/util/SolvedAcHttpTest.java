package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.dto.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.dto.SolvedAcUserResp;
import project.BaekjoonStatus.shared.exception.SolvedAcProblemNotFound;
import project.BaekjoonStatus.shared.exception.SolvedAcUserNotFound;

class SolvedAcHttpTest {

    private SolvedAcHttp solvedAcHttp;

    @BeforeEach
    public void beforeEach() {
        solvedAcHttp = new SolvedAcHttp();
    }

    @DisplayName("주어진 백준 아이디는 항상 같아야 한다.")
    @Test
    public void 백준_유저_인증() throws Exception {
        //given
        String baekjoonUsername = "pushrsp";

        //when
        SolvedAcUserResp baekjoonUser = solvedAcHttp.getBaekjoonUser(baekjoonUsername);

        //then
        Assertions.assertEquals(baekjoonUsername, baekjoonUser.getHandle());
    }

    @DisplayName("유저가 없을 경우 SolvedAcNotFound 에러가 나와야 한다.")
    @Test
    public void 백준_유저_없을_경우() throws Exception {
        //given
        String errorBaekjoonUsername = "fds342dfsz";

        //then
        Assertions.assertThrows(SolvedAcUserNotFound.class, () -> solvedAcHttp.getBaekjoonUser(errorBaekjoonUsername));
    }

    @DisplayName("옳바른 문제 번호가 주어질 경우 problemId 값은 항상 같아야 한다.")
    @Test
    public void 백준_문제() throws Exception {
        //given
        Long problemId = 17141L;

        //when
        SolvedAcProblemResp problem = solvedAcHttp.getProblemByProblemId(problemId);

        //then
        Assertions.assertEquals(problemId, problem.getProblemId());
    }

    @DisplayName("옳바르지 않은 문제번호가 주어질 경우 SolvedAcProblemNotFound 에러가 출력되어야 한다.")
    @Test
    public void 옳바르지_않은_문제번호() throws Exception {
        //given
        Long problemId = -1L;

        //then
        Assertions.assertThrows(SolvedAcProblemNotFound.class, () -> solvedAcHttp.getProblemByProblemId(problemId));
    }

}
