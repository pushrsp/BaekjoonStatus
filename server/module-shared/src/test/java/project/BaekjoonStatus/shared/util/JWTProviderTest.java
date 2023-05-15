package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {

    @Test
    public void 유효기간() throws Exception {
        //given
        User user = new User(UUID.randomUUID(),"test1", "test1", "test1");

        //when
        String token = JWTProvider.generateToken(user.getId().toString(), "test", 20L);
        sleep(2500L);

        //then
        assertThatThrownBy(() -> JWTProvider.validateToken(token, "test"))
                        .isInstanceOf(MyException.class);
    }

    @Test
    public void 인코딩_디코딩() throws Exception {
        //given
        User user1 = new User(UUID.randomUUID(),"test1", "test1", "test1");
        User user2 = new User(UUID.randomUUID(),"test1", "test1", "test1");

        String token1 = JWTProvider.generateToken(user1.getId().toString(), "test", 2000L);
        String token2 = JWTProvider.generateToken(user2.getId().toString(), "test", 2000L);

        //when
        assertThat(token1)
                        .isNotEqualTo(token2);

        String userId1 = JWTProvider.validateToken(token1, "test");
        assertThat(user1.getId().toString())
                        .isEqualTo(userId1);

        String userId2 = JWTProvider.validateToken(token2, "test");
        assertThat(user2.getId().toString())
                .isEqualTo(userId2);
    }

    @Test
    public void 유효하지_않은_시크릿_키값() throws Exception {
        //given
        User user = new User(UUID.randomUUID(),"test1", "test1", "test1");
        String token = JWTProvider.generateToken(user.getId().toString(), "test", 2000L);

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
}