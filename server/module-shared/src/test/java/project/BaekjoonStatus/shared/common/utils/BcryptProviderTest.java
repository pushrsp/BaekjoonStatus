package project.BaekjoonStatus.shared.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BcryptProviderTest {
    @DisplayName("잘못된 비밀번호와 비교할 시 false를 반환한다.")
    @Test
    public void validate_with_different_password_return_false() throws Exception {
        //given
        String hashPassword = BcryptProvider.hashPassword("test1234");

        //when
        boolean result = BcryptProvider.validatePassword("1234test", hashPassword);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("올바른 비밀번호와 비교할 시 true를 반환한다.")
    @Test
    public void validate_with_same_password_return_true() throws Exception {
        //given
        String hashPassword = BcryptProvider.hashPassword("test1234");

        //when
        boolean result = BcryptProvider.validatePassword("test1234", hashPassword);

        //then
        assertThat(result).isTrue();
    }
}
