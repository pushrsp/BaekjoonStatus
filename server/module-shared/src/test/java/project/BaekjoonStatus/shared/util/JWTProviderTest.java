package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.UUID;

class JWTProviderTest {

    @Test
    public void 유효기간() throws Exception {
        //given
        User user = new User(UUID.randomUUID(),"test1", "test1", "test1");

        //when
        String token = JWTProvider.generateToken(user.getId().toString(), "test", 20L);
        sleep(2500L);

        //then
        Assertions.assertThrows(MyException.class, () -> JWTProvider.validateToken(token, "test"));
    }

    @Test
    public void 인코딩_디코딩() throws Exception {
        //given
        User user1 = new User(UUID.randomUUID(),"test1", "test1", "test1");
        User user2 = new User(UUID.randomUUID(),"test1", "test1", "test1");

        String token1 = JWTProvider.generateToken(user1.getId().toString(), "test", 2000L);
        String token2 = JWTProvider.generateToken(user2.getId().toString(), "test", 2000L);

        //when
        Assertions.assertNotEquals(token1, token2);

        String userId1 = JWTProvider.validateToken(token1, "test");
        Assertions.assertEquals(user1.getId().toString(), userId1);

        String userId2 = JWTProvider.validateToken(token2, "test");
        Assertions.assertEquals(user2.getId().toString(), userId2);
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
        Assertions.assertThrows(MyException.class, () -> JWTProvider.validateToken(invalidToken, "test"));
        Assertions.assertThrows(MyException.class, () -> JWTProvider.validateToken(invalidToken, "invalidSecret"));
    }

    @Test
    public void authorization_없을_때() throws Exception {
        Assertions.assertThrows(MyException.class, () -> JWTProvider.extractToken(""));
        Assertions.assertThrows(NullPointerException.class, () -> JWTProvider.extractToken(null));
    }

    @Test
    public void 옳바르지_않은_authorization_형식() throws Exception {
        //given
        String validAuthorization = "abc test";
        String[] tokens = validAuthorization.split(" ");

        //when
        String token = JWTProvider.extractToken(validAuthorization);

        //then
        Assertions.assertEquals(tokens[1], token);
        Assertions.assertThrows(MyException.class, () -> JWTProvider.findToken("abc"));
        Assertions.assertThrows(MyException.class, () -> JWTProvider.findToken("abc abc test"));
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
