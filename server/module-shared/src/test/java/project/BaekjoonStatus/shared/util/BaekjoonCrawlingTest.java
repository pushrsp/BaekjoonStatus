package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BaekjoonCrawlingTest {
    @Test
    public void URL이_유효하지_않을_때() throws Exception {
        BaekjoonCrawling crawling = new BaekjoonCrawling( "https://www.acmicpcds.net/userfdsa", "pushrsp");
        MyException myException = catchThrowableOfType(crawling::getMySolvedHistories, MyException.class);

        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.MY_SERVER_UNKNOWN_HOST.getCode());

        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.MY_SERVER_UNKNOWN_HOST.getMessage());
    }

    @Test
    public void 아이디가_존재하지_않을_때() throws Exception {
        BaekjoonCrawling crawling = new BaekjoonCrawling( "-asbsd");
        MyException myException = catchThrowableOfType(crawling::getMySolvedHistories, MyException.class);

        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getCode());

        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.BAEKJOON_NOT_FOUND.getMessage());
    }

    @Test
    public void 아이디가_유효할_경우_푼_문제_반환() throws Exception {
        BaekjoonCrawling crawling = new BaekjoonCrawling("pushrsp");
        List<Long> mySolvedHistories = crawling.getMySolvedHistories();

        assertThat(mySolvedHistories.size())
                .isGreaterThanOrEqualTo(0);
    }
}
