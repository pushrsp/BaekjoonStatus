package project.BaekjoonStatus.shared.baekjoon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BaekjoonServiceTest {
    private BaekjoonService baekjoonService;

    @BeforeEach
    void setUp() {
        baekjoonService = new BaekjoonService();
    }

    @DisplayName("백준 아이디를 통해 유저가 푼 문제를 구할 수 있다.")
    @Test
    public void can_get_solved_problem_info_by_baekjoon_username() throws Exception {
        //when
        List<Long> problemIds = baekjoonService.getProblemIdsByUsername("pushrsp");

        //then
        assertThat(problemIds).isNotEmpty();
    }

    @DisplayName("백준 아이디가 존재하는지 감지할 수 있다.")
    @Test
    public void can_detect_whether_baekjoon_username_is_existed() throws Exception {
        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> baekjoonService.getProblemIdsByUsername("-1"), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("해당 유저가 존재하지 않습니다.");
    }
}
