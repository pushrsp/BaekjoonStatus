package project.BaekjoonStatus.shared.domain.user.entity;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.domain.dto.UserDto;
import project.BaekjoonStatus.shared.user.infra.UserEntity;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    @Test
    public void 아이디_입력_필수() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> UserEntity.of(null, "abc", "abc"), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("아이디를 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> UserEntity.of("", "abc", "abc"), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("아이디를 입력해주세요.");

        validateFullUser();
    }

    @Test
    public void 백준아이디_입력_필수() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> UserEntity.of("abc", null, "abc"), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("백준 아이디를 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> UserEntity.of("abc", "", "abc"), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("백준 아이디를 입력해주세요.");

        validateFullUser();
    }

    @Test
    public void 비밀번호_입력_필수() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> UserEntity.of("abc", "abc", null), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("비밀번호를 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> UserEntity.of("abc", "abc", ""), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("비밀번호를 입력해주세요.");

        validateFullUser();
    }

    @Test
    public void DTO부터_생성은_ID값_필수() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setBaekjoonUsername("baekjoonUsername");

        assertThat(userDto.getUserId())
                .isNull();

        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> UserEntity.from(userDto), IllegalArgumentException.class);
        validateIllegalArgumentExceptionNotNull(illegalArgumentException);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("ID값을 입력해주세요.");
    }

    private void validateIllegalArgumentExceptionNotNull(IllegalArgumentException illegalArgumentException) {
        assertThat(illegalArgumentException).isNotNull();
    }

    private void validateFullUser() {
        UserEntity user = UserEntity.of("username", "baekjoonUsername", "password");

        assertThat(user)
                .isNotNull();
        assertThat(user.getUsername())
                .isEqualTo("username");
        assertThat(user.getBaekjoonUsername())
                .isEqualTo("baekjoonUsername");
        assertThat(user.getPassword())
                .isEqualTo("password");
    }
}
