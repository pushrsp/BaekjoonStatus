package project.BaekjoonStatus.shared.util;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

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
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(uri, SolvedAcProblemResp[].class))).collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    public List<SolvedAcProblemResp> getProblemsByProblemIds(List<Long> problemIds) {
        return this.getProblemsByProblemIds(problemIds.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
