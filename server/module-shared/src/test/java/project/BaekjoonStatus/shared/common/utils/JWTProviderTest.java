package project.BaekjoonStatus.shared.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JWTProviderTest {
    @Test
    public void can_detect_token_is_expired() throws Exception {
        //given
        String token = JWTProvider.generateToken("1", "test", 1000L);

        //when
        Thread.sleep(2000);

        //then
        MyException myException = catchThrowableOfType(() -> JWTProvider.validateToken(token, "test"), MyException.class);
        assertThat(myException.getCode()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getMessage());
    }
}
