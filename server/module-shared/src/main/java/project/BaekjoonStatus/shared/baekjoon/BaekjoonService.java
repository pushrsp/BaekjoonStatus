package project.BaekjoonStatus.shared.baekjoon;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaekjoonService {
    public static final String BAEKJOON_URL = "https://www.acmicpc.net/user";

    public List<Long> findByUsername(String username) {
        Connection conn = Jsoup.connect(BAEKJOON_URL + "/" + username);
        try {
            Elements elements = findElements(conn);
            return Arrays.stream(elements.get(0).text().split(" "))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (HttpStatusException e) {
            throw new MyException(CodeEnum.BAEKJOON_NOT_FOUND);
        } catch (UnknownHostException e) {
            throw new MyException(CodeEnum.MY_SERVER_UNKNOWN_HOST);
        } catch (Exception e) {
            throw new MyException(CodeEnum.BAEKJOON_SERVER_ERROR);
        }
    }

    private Elements findElements(Connection conn) throws IOException {
        Document document = conn.get();
        return document.select("div.problem-list");
    }
}
