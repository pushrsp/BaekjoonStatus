package project.BaekjoonStatus.api.auth.controller.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.api.auth.service.request.UserLoginServiceRequest;

import static org.assertj.core.api.Assertions.*;

class UserLoginRequestTest {
    @DisplayName("UserLoginServiceRequest로 컨버팅이 가능하다.")
    @Test
    public void can_convert_to_service_request() throws Exception {
        //given
        UserLoginRequest userLoginRequest = createUserLoginRequest("test", "test");

        //when
        UserLoginServiceRequest result = userLoginRequest.toServiceRequest();

        //then
        assertThat(result.getUsername()).isEqualTo(userLoginRequest.getUsername());
        assertThat(result.getPassword()).isEqualTo(userLoginRequest.getPassword());
    }

    private UserLoginRequest createUserLoginRequest(String username, String password) {
        return UserLoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
