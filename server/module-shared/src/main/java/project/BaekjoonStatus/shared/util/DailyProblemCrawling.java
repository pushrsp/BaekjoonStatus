package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DailyProblemCrawling {
    public static final String URL = "https://github.com/tony9402/baekjoon/blob/main/picked.md";
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final List<Long> dailyProblems = new ArrayList<>();
    private final Connection conn;
    private LocalDate today;

    public DailyProblemCrawling(Connection conn) {
        this.conn = conn;
    }

    public void start() {
        //TODO 날짜 안맞을시 예외 던지고 다시 배치
        try {
            Document document = conn.get();
            Elements elements = document.select("article.markdown-body");

            setToday(elements);
            setDailyProblems(elements);
        } catch (Exception e) {

        }
    }

    private void setToday(Elements elements) {
        this.today = LocalDate.parse(elements.get(0).getElementsByTag("h2").get(0).text(), DATE_PATTERN);
    }

    private void setDailyProblems(Elements elements) {
        Elements body = elements.get(0).getElementsByTag("table")
                .get(0)
                .getElementsByTag("tbody")
                .get(0)
                .getElementsByTag("tr");

        for (Element data : body)
            dailyProblems.add(Long.parseLong(data.getElementsByTag("td").get(1).text()));
    }

    public List<Long> getDailyProblems() {
        return this.dailyProblems;
    }

    public LocalDate getToday() {
        return this.today;
    }
}
