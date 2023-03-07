package project.BaekjoonStatus.shared.util;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DailyProblemCrawlingTest {

    private DailyProblemCrawling dailyProblemCrawling;

    @BeforeEach
    public void beforeEach() {
        dailyProblemCrawling = new DailyProblemCrawling(Jsoup.connect(DailyProblemCrawling.URL));
    }

    @Test
    public void 오늘의_문제() throws Exception {
        //given
        dailyProblemCrawling.start();

        //when
        LocalDate today = dailyProblemCrawling.getToday();
        List<Long> dailyProblems = dailyProblemCrawling.getDailyProblems();

        //then
        Assertions.assertEquals(4, dailyProblems.size());

        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        Assertions.assertEquals(today, LocalDate.now(zoneId));
    }

}
