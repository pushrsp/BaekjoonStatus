package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.BaekjoonNotFound;
import project.BaekjoonStatus.shared.exception.MyException;

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

    public List<Long> getMySolvedHistories() {
        try {
            Document document = conn.get();
            Elements elements = document.select("div.problem-list");

            return Arrays.stream(elements.get(0).text().split(" "))
                    .map(Long::parseLong)
                    .toList();
        } catch (Exception e) {
            throw new MyException(CodeEnum.BAEKJOON_SERVER_ERROR);
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
