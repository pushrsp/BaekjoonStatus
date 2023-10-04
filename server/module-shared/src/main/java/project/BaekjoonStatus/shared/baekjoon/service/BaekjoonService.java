package project.BaekjoonStatus.shared.baekjoon.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaekjoonService {
    public static final String BAEKJOON_URL = "https://www.acmicpc.net/user";

    public List<String> getProblemIdsByUsername (String username) {
        Connection conn = Jsoup.connect(BAEKJOON_URL + "/" + username);
        try {
            Elements elements = findElements(conn);
            return Arrays.stream(elements.get(0).text().split(" "))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        } catch (Exception e) {
            throw new InternalError("잠시 후 다시 시도해주세요.");
        }
    }

    private Elements findElements(Connection conn) throws IOException {
        Document document = conn.get();
        return document.select("div.problem-list");
    }
}
