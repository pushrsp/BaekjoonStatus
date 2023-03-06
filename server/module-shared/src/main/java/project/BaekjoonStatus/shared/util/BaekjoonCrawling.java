package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.BaekjoonNotFound;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BaekjoonCrawling {

    public static final String BAEKJOON_URL = "https://www.acmicpc.net/user";
    private final Connection conn;
    private List<Long> solved;

    public BaekjoonCrawling(Connection conn) {
        this.conn = conn;
    }

    public BaekjoonCrawling(String username) {
        this.conn = Jsoup.connect(BAEKJOON_URL + "/" + username);
    }

    public void start() {
        try {
            Document document = conn.get();
            setSolved(document);
        } catch (IOException e) {
            throw new BaekjoonNotFound(CodeEnum.BAEKJOON_NOT_FOUND);
        }
    }

    private void setSolved(Document document) {
        Elements elements = document.select("div.problem-list");
        this.solved = Arrays.stream(elements.get(0).text().split(" "))
                .map(Long::parseLong)
                .toList();
    }

    public int getSolvedSize() {
        return this.solved.size();
    }

    public List<Long> getSolved() {
        return this.solved;
    }

}
