package project.BaekjoonStatus.shared.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.PasswordService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;


class MemberTest {
    @DisplayName("아이디 또는 비밀번호가 잘못되었는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providerUsernameAndPassword")
    public void can_detect_username_or_password_is_invalid(String username, String password, String givenUsername, boolean passwordCheck, String expected) throws Exception {
        //given
        Member member = Member.builder()
                .username(username)
                .password(password)
                .build();

        PasswordService passwordService = new PasswordService() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return passwordCheck;
            }
        };

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> member.login(givenUsername, password, passwordService), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo(expected);
    }

    private static Stream<Arguments> providerUsernameAndPassword() {
        return Stream.of(
                Arguments.of("test", "test", "invalid", true, "아이디 또는 비밀번호가 일치하지 않습니다."),
                Arguments.of("test", "test", "test", false, "아이디 또는 비밀번호가 일치하지 않습니다."),
                Arguments.of("test", "test", "bac", false, "아이디 또는 비밀번호가 일치하지 않습니다.")
        );
    }
}
