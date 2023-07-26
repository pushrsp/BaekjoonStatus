package project.BaekjoonStatus.shared.user.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserCreateServiceDtoTest {
    @DisplayName("도메인으로 컨버팅이 가능하다.")
    @Test
    public void can_convert_to_user_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 1, 1);
        UserCreateServiceDto userCreateServiceDto = createUserCreateServiceDto("test", "test", now);

        //when
        User result = userCreateServiceDto.toDomain();

        //then
        assertThat(result.getId()).isNull();
        assertThat(result.getUsername()).isEqualTo(userCreateServiceDto.getUsername());
        assertThat(result.getPassword()).isEqualTo(userCreateServiceDto.getPassword());
        assertThat(result.getCreatedTime()).isEqualTo(now);
        assertThat(result.getModifiedTime()).isEqualTo(now);
    }

    private UserCreateServiceDto createUserCreateServiceDto(String username, String password, LocalDateTime now) {
        return UserCreateServiceDto.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("test")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
