package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;
import project.BaekjoonStatus.shared.common.utils.JWTProvider;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {
    @Test
    public void decoded_result_is_same_with_userId() throws Exception {
        //given
        Long userId = 2L;

        //when
        String encodedToken = JWTProvider.generateToken(String.valueOf(userId), "test", 2000L);
        String decodedToken = JWTProvider.validateToken(encodedToken, "test");

        //then
        assertThat(Long.parseLong(decodedToken)).isEqualTo(userId);
    }

    @Test
    public void decode_with_different_key_is_invalid() throws Exception {
        //given
        String encodedToken = JWTProvider.generateToken(String.valueOf(1L), "key", 2000L);

        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(encodedToken, "different key"), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideWrongTokenFormat")
    public void decode_with_wrong_token_format_is_invalid(String wrongTokenFormat) throws Exception {
        //given

        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(wrongTokenFormat, "key"), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getMessage());
    }

    @Test
    public void token_with_expired_date_is_not_allowed() throws Exception {
        //given
        Long userId = 1L;
        long expiredOffset = 2000L;

        //when
        String token = JWTProvider.generateToken(String.valueOf(userId), "test", expiredOffset);
        sleep(expiredOffset + 1000L);
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(token, "test"), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidExpiredOffset")
    public void can_detect_negative_number_and_zero_are_invalid(Long expiredOffset) throws Exception {
        //given
        Long userId = 1L;

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> JWTProvider.generateToken(String.valueOf(userId), "test", expiredOffset), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("expiredOffset 0보다 커야 됩니다.");
    }

    @ParameterizedTest
    @MethodSource("provideWrongAuthorizationHeader")
    public void can_detect_wrong_authorization_header_is_invalid(String authorizationHeader) throws Exception {
        //given

        //when
        MyException myException = catchThrowableOfType(() -> JWTProvider.extractToken(authorizationHeader), MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_UNAUTHORIZED.getMessage());
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Arguments> provideInvalidExpiredOffset() {
        return Stream.of(
                Arguments.of(-1L),
                Arguments.of(0L)
        );
    }

    private static Stream<Arguments> provideWrongTokenFormat() {
        return Stream.of(
                Arguments.of("fdsafsa"),
                Arguments.of("1"),
                Arguments.of("vcxzcvxz.fdsafdsa")
        );
    }

    private static Stream<Arguments> provideWrongAuthorizationHeader() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("-1"),
                Arguments.of("abc"),
                Arguments.of("abc abc abc")
        );
    }
}
