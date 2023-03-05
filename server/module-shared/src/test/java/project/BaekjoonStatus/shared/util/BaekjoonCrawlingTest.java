package project.BaekjoonStatus.shared.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import project.BaekjoonStatus.shared.exception.BaekjoonNotFound;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;

class BaekjoonCrawlingTest {

    @DisplayName("백준 아이디가 존재할 경우 solved의 크기가 0 이상이여야 한다.")
    @Test
    public void 백준_유저_테스트() {
        //given
        BaekjoonCrawling crawling = new BaekjoonCrawling(Jsoup.connect(BaekjoonCrawling.BAEKJOON_URL + "/pushrsp"));

        //when
        crawling.start();

        //then
        Assertions.assertTrue(crawling.getSolvedSize() >= 0);
    }

    @DisplayName("백준 아이디가 존재하지 않을 경우 BaekjoonNotFound 에러가 나와야한다.")
    @Test
    public void 백준_유저_없을_경우() throws Exception {
        //given
        BaekjoonCrawling crawling = new BaekjoonCrawling(Jsoup.connect(BaekjoonCrawling.BAEKJOON_URL + "/fds342dfsz"));

        //then
        Assertions.assertThrows(BaekjoonNotFound.class, crawling::start);
    }

}
