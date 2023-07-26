package project.BaekjoonStatus.api.auth.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;
import project.BaekjoonStatus.shared.user.service.request.UserCreateServiceDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserCreateServiceRequestTest {
    @DisplayName("UserCreateServiceRequest는 UserCreateServiceDto로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_service_dto() throws Exception {
        //given
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return true;
            }
        };
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 3, 3);
        String password = "5431";
        boolean isPrivate = true;

        UserCreateServiceRequest userCreateServiceRequest = createUserCreateServiceRequest(password, now);

        //when
        UserCreateServiceDto result = userCreateServiceRequest.toServiceDto(isPrivate, passwordEncryptor);

        //then
        assertThat(result.getCreatedTime()).isEqualTo(now);
        assertThat(result.getModifiedTime()).isEqualTo(now);
        assertThat(result.getIsPrivate()).isEqualTo(isPrivate);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    private UserCreateServiceRequest createUserCreateServiceRequest(String password, LocalDateTime now) {
        return UserCreateServiceRequest.builder()
                .username("test")
                .baekjoonUsername("baekjoon")
                .password(password)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
