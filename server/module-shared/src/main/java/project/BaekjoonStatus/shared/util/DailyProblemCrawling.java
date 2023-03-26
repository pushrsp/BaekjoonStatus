package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class DailyProblemCrawling {
    public static final String URL = "https://github.com/tony9402/baekjoon/blob/main/picked.md";
    private final Connection conn;

    public DailyProblemCrawling() {
        this.conn = Jsoup.connect(URL);
    }

    public List<Long> start() {
        try {
            Document document = conn.get();
            Elements elements = document.select("article.markdown-body");

            Elements body = elements.get(0).getElementsByTag("table")
                    .get(0)
                    .getElementsByTag("tbody")
                    .get(0)
                    .getElementsByTag("tr");

            return body.stream()
                    .map((data) -> Long.parseLong(data.getElementsByTag("td").get(1).text()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
}
