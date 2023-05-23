package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

public class DailyProblemCrawling extends MyCrawling {
    public static final String GITHUB_URL = "https://github.com/tony9402/baekjoon/blob/main/picked.md";

    public DailyProblemCrawling() {
        initConnect();
    }

    public DailyProblemCrawling(String url) {
        initConnect(url);
    }

    private void initConnect() {
        setConn(Jsoup.connect(GITHUB_URL));
    }

    private void initConnect(String url) {
        setConn(Jsoup.connect(url));
    }

    public List<Long> get() {
        try {
            return getElements().stream()
                    .map((data) -> Long.parseLong(data.getElementsByTag("td").get(1).text()))
                    .collect(Collectors.toList());
        } catch (UnknownHostException | HttpStatusException e) {
            throw new MyException(CodeEnum.MY_SERVER_UNKNOWN_HOST);
        } catch (Exception e) {
            throw new MyException(CodeEnum.UNKNOWN_EXCEPTION);
        }
    }

    @Override
    protected Elements getElements() throws IOException {
        Document document = conn.get();
        Elements elements = document.select("article.markdown-body");

        return elements.get(0).getElementsByTag("table")
                .get(0)
                .getElementsByTag("tbody")
                .get(0)
                .getElementsByTag("tr");
    }
}
