package project.BaekjoonStatus.api.auth.service.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserLoginServiceRequestTest {
    @DisplayName("UserLoginServiceRequest는 username과 password로 만들어진다.")
    @Test
    public void can_be_made_by_username_and_password() throws Exception {
        //given
        String username = "test";
        String password = "password";

        //when
        UserLoginServiceRequest result = createUserLoginServiceRequest(username, password);

        //then
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    private UserLoginServiceRequest createUserLoginServiceRequest(String username, String password) {
        return UserLoginServiceRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
