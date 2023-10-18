package project.BaekjoonStatus.api.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.TokenService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class TokenServiceTest {
    @DisplayName("offset 값은 항상 0보다 커야한다.")
    @Test
    public void can_detect_offset_is_valid() throws Exception {
        //given
        TokenService tokenService = new JwtService();

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> tokenService.generate("a", "secret", 0L), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("offset 0보다 커야 됩니다.");
    }

    @DisplayName("secret 값이 올바른 값인지 확인할 수 있다.")
    @Test
    public void can_detect_secret_is_valid() throws Exception {
        //given
        TokenService tokenService = new JwtService();
        String token = tokenService.generate("aaaa", "secret", 10000L);

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> tokenService.verify(token, "ccc"), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("토큰 형식이 잘못되었습니다.");
    }

    @DisplayName("token의 유통기한을 확인할 수 있다.")
    @Test
    public void can_detect_token_is_expired() throws Exception {
        //given
        TokenService tokenService = new JwtService();
        String token = tokenService.generate("aaaa", "secret", 1000L);

        //when
        sleep(2000);
        IllegalStateException illegalStateException = catchThrowableOfType(() -> tokenService.verify(token, "secret"), IllegalStateException.class);

        //then
        assertThat(illegalStateException).isNotNull();
        assertThat(illegalStateException.getMessage()).isEqualTo("로그인을 다시 해주세요.");
    }

    @DisplayName("Authorization header값이 올바른지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideAuthorization")
    public void can_detect_authorization_header_is_valid(String authorization, String expected) throws Exception {
        //given
        TokenService tokenService = new JwtService();

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> tokenService.extract(authorization), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideAuthorization() {
        return Stream.of(
                Arguments.of("", "접근할 수 없는 페이지 입니다."),
                Arguments.of(null, "접근할 수 없는 페이지 입니다."),
                Arguments.of("aaa bb cc", "접근할 수 없는 페이지 입니다.")
        );
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
