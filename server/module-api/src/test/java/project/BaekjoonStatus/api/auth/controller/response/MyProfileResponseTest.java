package project.BaekjoonStatus.api.auth.controller.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.user.domain.User;

import static org.assertj.core.api.Assertions.*;

class MyProfileResponseTest {
    @DisplayName("MyProfileResponse는 User로 만들어진다.")
    @Test
    public void can_ba_made_by_user() throws Exception {
        //given
        User user = createUser(1L, "test");

        //when
        MyProfileResponse result = MyProfileResponse.from(user);

        //then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
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
