package project.BaekjoonStatus.shared.user.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserEntityTest {
    @DisplayName("User도메인으로부터 User엔티티를 생성할 수 있다.")
    @Test
    public void can_convert_to_user_entity_from_user_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 7, 30, 13, 30);
        User userDomain = createUserDomain("test", "test", now);

        //when
        UserEntity userEntity = UserEntity.from(userDomain);

        //then
        assertThat(userEntity.getUsername()).isEqualTo(userDomain.getUsername());
        assertThat(userEntity.getCreatedTime()).isEqualTo(userDomain.getCreatedTime());
        assertThat(userEntity.getModifiedTime()).isEqualTo(userDomain.getModifiedTime());
    }

    private User createUserDomain(String username, String password, LocalDateTime now) {
        return User.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
