package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {

    @Test
    public void token_with_expired_date_cant_be_used() throws Exception {
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

    @Test
    public void 인코딩_디코딩() throws Exception {
        //given
        Long userId1 = 1L;
        Long userId2 = 2L;

        User user1 =  User.of("test1", "test1", "test1");
        User user2 =  User.of("test1", "test1", "test1");

        String token1 = JWTProvider.generateToken(String.valueOf(userId1), "test", 2000L);
        String token2 = JWTProvider.generateToken(String.valueOf(userId2), "test", 2000L);

        //when
        assertThat(token1)
                        .isNotEqualTo(token2);

        String extractedUserId1 = JWTProvider.validateToken(token1, "test");
        assertThat(extractedUserId1)
                        .isEqualTo(String.valueOf(userId1));

        String extractedUserId2 = JWTProvider.validateToken(token2, "test");
        assertThat(extractedUserId2)
                .isEqualTo(String.valueOf(userId2));
    }

    @Test
    public void 유효하지_않은_시크릿_키값() throws Exception {
        //given
        Long userId = 1L;
        User user = User.of("test1", "test1", "test1");
        String token = JWTProvider.generateToken(String.valueOf(userId), "test", 2000L);

        //when
        Assertions.assertThrows(MyException.class, () -> JWTProvider.validateToken(token, "invalid secret"));
    }

    @Test
    public void 유효하지_않은_토큰() throws Exception {
        //given
        String invalidToken = "fdjsalkfdsaufdsalfa";

        //when
        assertThatThrownBy(() -> JWTProvider.validateToken(invalidToken, "test"))
                        .isInstanceOf(MyException.class);
        assertThatThrownBy(() -> JWTProvider.validateToken(invalidToken, "invalidSecret"))
                        .isInstanceOf(MyException.class);
    }

    @Test
    public void authorization_없을_때() throws Exception {
        assertThatThrownBy(() -> JWTProvider.extractToken(""))
                        .isInstanceOf(MyException.class);
        assertThatThrownBy(() -> JWTProvider.extractToken(null))
                        .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void 옳바르지_않은_authorization_형식() throws Exception {
        //given
        String validAuthorization = "abc test";
        String[] tokens = validAuthorization.split(" ");

        //when
        String token = JWTProvider.extractToken(validAuthorization);

        //then
        assertThat(tokens[1])
                        .isEqualTo(token);
        assertThatThrownBy(() -> JWTProvider.extractToken("abc"))
                        .isInstanceOf(MyException.class);
        assertThatThrownBy(() -> JWTProvider.extractToken("abc abc test"))
                        .isInstanceOf(MyException.class);
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
}
