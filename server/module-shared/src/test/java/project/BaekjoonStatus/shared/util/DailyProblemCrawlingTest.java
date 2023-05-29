package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DailyProblemCrawlingTest {
    @Test
    public void 크기는_항상_4이어야하며_중복_X() throws Exception {
        DailyProblemCrawling crawling = new DailyProblemCrawling();
        List<Long> problems = crawling.get();

        assertThat(problems)
                .hasSize(4);
        assertThat(problems)
                .doesNotHaveDuplicates();
    }
}
