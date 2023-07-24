package project.BaekjoonStatus.shared.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {

    @DisplayName("유통기한이 지난 토큰은 사용할 수 없다.")
    @Test
    public void can_detect_token_is_expired() throws Exception {
        //given
        String token = JWTProvider.generateToken("1", "test", 1000L);

        //when
        Thread.sleep(2000);
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(token, "test"), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getMessage());
    }

    @DisplayName("authorization 헤더의 포맷이 올바른 형태가 아니면 사용할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideWrongAuthorizationHeaderFormat")
    public void wrong_authorization_header_format_is_not_allowed(String authorizationHeader) throws Exception {
        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.extractToken(authorizationHeader), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getMessage());
    }

    @DisplayName("다른 키 값으로 복호화 시도는 허용하지 않는다.")
    @Test
    public void decode_with_different_key_is_not_valid() throws Exception {
        //given
        String token = JWTProvider.generateToken("2", "test", 5000L);

        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(token, "different key"), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getMessage());
    }

    private static Stream<Arguments> provideWrongAuthorizationHeaderFormat() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("aaaaa"),
                Arguments.of("aaaaa bbbbb ccccc")
        );
    }
}
