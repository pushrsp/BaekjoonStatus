package project.BaekjoonStatus.shared.util;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaekjoonCrawling extends MyCrawling {

    public static final String BAEKJOON_URL = "https://www.acmicpc.net/user";

    public BaekjoonCrawling(String username) {
        initConnect(username);
    }

    public BaekjoonCrawling(String url, String username) {
        initConnect(url, username);
    }

    private void initConnect(String username) {
        setConn(Jsoup.connect(BAEKJOON_URL + "/" + username));
    }

    private void initConnect(String url, String username) {
        setConn(Jsoup.connect(url + "/" + username));
    }

    @Override
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

    protected Elements getElements() throws IOException {
        Document document = conn.get();
        return document.select("div.problem-list");
    }
}
