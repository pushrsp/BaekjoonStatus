package project.BaekjoonStatus.shared.util;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.dto.response.SolvedAcUserResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.exception.SolvedAcProblemNotFound;
import project.BaekjoonStatus.shared.exception.SolvedAcUserNotFound;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SolvedAcHttp {
    private static final String SOLVED_AC_URL = "https://solved.ac/api/v3";
    private static final String SOLVED_AC_GET_USER_PATH = "/user/show";
    private static final String SOLVED_AC_GET_PROBLEM_PATH = "/problem/show";
    private static final String SOLVED_AC_GET_PROBLEMS_PATH = "/problem/lookup";
    private static final int SOLVED_AC_MAX_LEN = 100;

    public SolvedAcUserResp getBaekjoonUser(String baekjoonUsername) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(SOLVED_AC_URL)
                .path(SOLVED_AC_GET_USER_PATH)
                .queryParam("handle", baekjoonUsername)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.getForObject(uri, SolvedAcUserResp.class);
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    public SolvedAcProblemResp getProblemByProblemId(Long problemId) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(SOLVED_AC_URL)
                .path(SOLVED_AC_GET_PROBLEM_PATH)
                .queryParam("problemId", problemId)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(uri, SolvedAcProblemResp.class);
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    private List<SolvedAcProblemResp> getProblemsByProblemIds(String ids) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(SOLVED_AC_URL)
                .path(SOLVED_AC_GET_PROBLEMS_PATH)
                .queryParam("problemIds", ids)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(uri, SolvedAcProblemResp[].class))).toList();
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    public List<SolvedAcProblemResp> getProblemsByProblemIds(List<Long> problemIds) {
        List<SolvedAcProblemResp> problems = new ArrayList<>();
        int offset = 0;
        for (int i = 0; i < (problemIds.size() / SOLVED_AC_MAX_LEN) + 1; i++) {
            List<String> ids = problemIds.subList(offset, Math.min(problemIds.size(), offset + SOLVED_AC_MAX_LEN))
                    .stream()
                    .map(Object::toString)
                    .toList();

            problems.addAll(this.getProblemsByProblemIds(String.join(",", ids)));
            offset += SOLVED_AC_MAX_LEN;
        }

        return problems;
    }
}
