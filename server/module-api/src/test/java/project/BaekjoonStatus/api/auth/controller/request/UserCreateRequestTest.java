package project.BaekjoonStatus.api.auth.controller.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.api.auth.service.request.UserCreateServiceRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserCreateRequestTest {
    @DisplayName("UserCreateServiceRequest로 컨버팅이 가능하다.")
    @Test
    public void can_convert_to_service_request() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 2, 2, 2);
        UserCreateRequest userCreateRequest = createUserCreateRequest("test", "test");

        //when
        UserCreateServiceRequest result = userCreateRequest.toServiceRequest(now);

        //then
        assertThat(result.getCreatedTime()).isEqualTo(now);
        assertThat(result.getModifiedTime()).isEqualTo(now);
        assertThat(result.getUsername()).isEqualTo(userCreateRequest.getUsername());
        assertThat(result.getPassword()).isEqualTo(userCreateRequest.getPassword());
    }

    private UserCreateRequest createUserCreateRequest(String username, String password) {
        return UserCreateRequest.builder()
                .registerToken("registerToken")
                .username(username)
                .baekjoonUsername("baekjoon")
                .password(password)
                .build();
    }
}
