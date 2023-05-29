package project.BaekjoonStatus.shared.util;

import org.jsoup.Jsoup;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DailyProblemCrawling extends MyCrawling {
    public static final String GITHUB_URL = "https://raw.githubusercontent.com/tony9402/baekjoon/main/";
    public static final String FILE = "picked.md";

    @Override
    public List<Long> get() {
        try {
            URI uri = getURI();
            RestTemplate restTemplate = new RestTemplate();

            String result = restTemplate.getForObject(uri, String.class);
            validateResult(result);

            return parseResult(result);
        } catch (Exception e) {
            throw new MyException(CodeEnum.UNKNOWN_EXCEPTION);
        }
    }

    private void validateResult(String result) {
        Assert.hasText(result, "응답값이 비어있습니다.");
        Assert.notNull(result, "응답값이 비어있습니다.");
    }

    private List<Long> parseResult(String result) {
        List<Long> ret = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[[0-9]+\\]");
        Matcher matcher = pattern.matcher(result);

        int count = 0;
        while (matcher.find() && count < 4) {
            String group = matcher.group();
            ret.add(Long.parseLong(group.substring(1, group.length() -1)));
            count++;
        }

        return ret;
    }

    private URI getURI() {
        return UriComponentsBuilder
                .fromHttpUrl(GITHUB_URL)
                .path(FILE)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
    }
}
