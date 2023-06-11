package project.BaekjoonStatus.shared.common.service.baekjoon;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaekjoonCrawling {

    public static final String BAEKJOON_URL = "https://www.acmicpc.net/user";
    private Connection conn;

    public BaekjoonCrawling(String username) {
        initConnect(username);
    }

    private void initConnect(String username) {
        setConn(Jsoup.connect(BAEKJOON_URL + "/" + username));
    }

    private void setConn(Connection conn) {
        this.conn = conn;
    }

    public List<Long> get() {
        try {
            Elements elements = getElements();
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

    private Elements getElements() throws IOException {
        Document document = conn.get();
        return document.select("div.problem-list");
    }
}