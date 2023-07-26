package project.BaekjoonStatus.api.auth.controller.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BaekjoonVerifyResponseTest {
    @DisplayName("BaekjoonVerifyResponse는 problems와 registerToken으로 만들어진다.")
    @Test
    public void can_be_made_by_problems_and_registerToken() throws Exception {
        //given
        List<Long> problems = List.of(1L, 2L, 3L);
        String registerToken = "abc";

        //when
        BaekjoonVerifyResponse result = BaekjoonVerifyResponse.from(problems, registerToken);

        //then
        assertThat(result.getSolvedCount()).isEqualTo(problems.size());
        assertThat(result.getRegisterToken()).isEqualTo(registerToken);
    }
}
