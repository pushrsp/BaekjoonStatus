package project.BaekjoonStatus.shared.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.infra.DailyProblemRepository;
import project.BaekjoonStatus.shared.dailyproblem.service.request.DailyProblemCreateSharedServiceRequest;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DailyProblemService {
    private static final String GITHUB_URL = "https://raw.githubusercontent.com/tony9402/baekjoon/main/";
    private static final String FILE = "picked.md";

    private final DailyProblemRepository dailyProblemRepository;

    @Transactional(readOnly = true)
    public List<DailyProblem> findAllByCreatedDate(DateService dateService) {
        return dailyProblemRepository.findAllByCreatedDate(dateService.getToday(dateService.getDateTime()));
    }

    @Transactional
    public int saveAll(List<DailyProblemCreateSharedServiceRequest> requests) {
        return dailyProblemRepository.saveAll(DailyProblemCreateSharedServiceRequest.toDomainList(requests));
    }

    public List<Long> findTodayProblems() {
        try {
            URI uri = getURI();
            RestTemplate restTemplate = new RestTemplate();

            String result = restTemplate.getForObject(uri, String.class);

            return parseResult(result);
        } catch (Exception e) {
            throw new MyException(CodeEnum.UNKNOWN_EXCEPTION);
        }
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
