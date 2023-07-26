package project.BaekjoonStatus.api.auth.controller.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.user.domain.User;

import static org.assertj.core.api.Assertions.*;

class UserLoginResponseTest {
    @DisplayName("UserLoginResponse는 user와 token으로 만들어진다.")
    @Test
    public void can_be_made_by_user_and_token() throws Exception {
        //given
        User user = createUser(1L, "test");
        String token = "token";

        //when
        UserLoginResponse result = UserLoginResponse.from(user, token);

        //then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getToken()).isEqualTo(token);
    }

    private User createUser(Long id, String username) {
        return User.builder()
                .id(id)
                .username(username)
                .password("password")
                .baekjoonUsername("test")
                .isPrivate(true)
                .createdTime(DateProvider.getDateTime())
                .modifiedTime(DateProvider.getDateTime())
                .build();
    }
}
