package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DailyProblemCrawlingTest {
    @Test
    public void dailyProblems_is_not_duplicated() throws Exception {
        //given
        DailyProblemCrawling crawling = new DailyProblemCrawling();

        //when
        List<Long> problemIds = crawling.get();

        //then
        assertThat(problemIds).doesNotHaveDuplicates();
    }

    @Test
    public void dailyProblems_has_size_of_4() throws Exception {
        //given
        DailyProblemCrawling crawling = new DailyProblemCrawling();

        //when
        List<Long> problemIds = crawling.get();

        //then
        assertThat(problemIds).hasSize(4);
    }
}
