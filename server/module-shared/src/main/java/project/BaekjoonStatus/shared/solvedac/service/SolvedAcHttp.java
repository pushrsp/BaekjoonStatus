package project.BaekjoonStatus.shared.solvedac.service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SolvedAcHttp {
    private static final String SOLVED_AC_URL = "https://solved.ac/api/v3";
    private static final String SOLVED_AC_GET_PROBLEM_PATH = "/problem/show";
    private static final String SOLVED_AC_GET_PROBLEMS_PATH = "/problem/lookup";

    public SolvedAcProblemResponse getProblemByProblemId(Long problemId) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(SOLVED_AC_URL)
                .path(SOLVED_AC_GET_PROBLEM_PATH)
                .queryParam("problemId", problemId)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(uri, SolvedAcProblemResponse.class);
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    private List<SolvedAcProblemResponse> getProblemsByProblemIds(String ids) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(SOLVED_AC_URL)
                .path(SOLVED_AC_GET_PROBLEMS_PATH)
                .queryParam("problemIds", ids)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(uri, SolvedAcProblemResponse[].class))).collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    public List<SolvedAcProblemResponse> getProblemsByProblemIds(List<Long> problemIds) {
        return this.getProblemsByProblemIds(problemIds.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
