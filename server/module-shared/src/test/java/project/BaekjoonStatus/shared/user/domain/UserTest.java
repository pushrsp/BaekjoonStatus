package project.BaekjoonStatus.shared.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    @DisplayName("아이디와 비밀번호가 다를 시 예외를 반환한다.")
    @Test
    public void different_username_and_password_is_not_valid() throws Exception {
        //given
        String wrongUsername = "test1";
        String wrongPassword = "test1";
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return plainPassword.equals(hashedPassword);
            }
        };

        User user = createUser("test2", "test2");

        //when
        MyException myException = catchThrowableOfType(() -> user.login(wrongUsername, wrongPassword, passwordEncryptor), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST.getMessage());
    }

    private User createUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("test")
                .isPrivate(true)
                .createdTime(DateProvider.getDateTime())
                .modifiedTime(DateProvider.getDateTime())
                .build();
    }
}
