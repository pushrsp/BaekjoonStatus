package project.BaekjoonStatus.shared.common.service.baekjoon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.baekjoon.BaekjoonService;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BaekjoonServiceTest {
    @DisplayName("존재하는 아이디일 시 푼 문제의 아이디를 반환한다.")
    @Test
    public void existed_baekjoon_username_return_problem_ids() throws Exception {
        //given
        String baekjoonUsername = "pushrsp";
        BaekjoonService baekjoonService = new BaekjoonService();

        //when
        List<Long> problemIds = baekjoonService.getProblemIdsByUsername(baekjoonUsername);

        //then
        assertThat(problemIds).doesNotHaveDuplicates();
    }

    @DisplayName("존재하지 않은 아이디일 시 예외를 던진다.")
    @Test
    public void non_existed_baekjoon_username_is_not_allowed() throws Exception {
        //given
        String baekjoonUsername = "-1@@@";
        BaekjoonService baekjoonService = new BaekjoonService();

        //when
        MyException myException = catchThrowableOfType(() -> baekjoonService.getProblemIdsByUsername(baekjoonUsername), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getMessage());
    }
}
