package project.BaekjoonStatus.shared.common.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {
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

    @ParameterizedTest
    @MethodSource("provideWrongAuthorizationHeaderFormat")
    public void wrong_authorization_header_format_is_not_allowed(String authorizationHeader) throws Exception {
        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.extractToken(authorizationHeader), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getMessage());
    }

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
