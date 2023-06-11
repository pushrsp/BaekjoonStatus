package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;
import project.BaekjoonStatus.shared.common.service.baekjoon.BaekjoonCrawling;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class BaekjoonCrawlingTest {
    @ParameterizedTest
    @MethodSource("provideUnregisteredUsername")
    public void can_detect_unregistered_username_is_not_allowed(String username) throws Exception {
        //given
        BaekjoonCrawling crawling = new BaekjoonCrawling(username);

        //when
        MyException myException = catchThrowableOfType(crawling::get, MyException.class);

        //then
        assertThat(myException.getCode()).isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getCode());
        assertThat(myException.getMessage()).isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getMessage());
    }

    private static Stream<Arguments> provideUnregisteredUsername() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("fdsarewq")
        );
    }
}
