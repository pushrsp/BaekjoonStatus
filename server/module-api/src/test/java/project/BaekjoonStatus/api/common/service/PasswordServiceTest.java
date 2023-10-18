package project.BaekjoonStatus.api.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.PasswordService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class PasswordServiceTest {
    @DisplayName("평문 비밀번호를 통해 암호화 된 비밀번호를 만들 수 있다.")
    @Test
    public void can_generate_hashed_password_based_on_plain_password() throws Exception {
        //given
        PasswordService passwordService = new PasswordService() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return false;
            }
        };
        String plainPassword = "plainPassword";

        //when
        String hashedPassword = passwordService.hashPassword(plainPassword);

        //then
        assertThat(hashedPassword).isEqualTo(plainPassword);
    }

    @DisplayName("주어진 비밀번호가 올바른지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePassword")
    public void can_detect_given_password_is_valid(String plainPassword, String hashedPassword, boolean expected) throws Exception {
        //given
        PasswordService passwordService = new PasswordService() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return hashedPassword.equals(plainPassword);
            }
        };

        //when
        boolean result = passwordService.validatePassword(plainPassword, hashedPassword);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> providePassword() {
        return Stream.of(
                Arguments.of("aa", "aa", true),
                Arguments.of("aaa", "bb", false)
        );
    }
}
