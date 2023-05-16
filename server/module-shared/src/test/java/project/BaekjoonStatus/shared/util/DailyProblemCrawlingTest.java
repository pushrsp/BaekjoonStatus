package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import static org.assertj.core.api.Assertions.*;

class DailyProblemCrawlingTest {
    @Test
    public void URL이_유효하지_않을_때() throws Exception {
        DailyProblemCrawling crawling = new DailyProblemCrawling("https://github.com/tony9402/baekjoon/blob/mainfdsafsa/picked.md");
        MyException myException = catchThrowableOfType(crawling::get, MyException.class);

        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.MY_SERVER_UNKNOWN_HOST.getCode());
        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.MY_SERVER_UNKNOWN_HOST.getMessage());
    }
}
