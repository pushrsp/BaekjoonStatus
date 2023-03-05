package project.BaekjoonStatus.shared.util;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.dto.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.dto.SolvedAcUserResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.SolvedAcProblemNotFound;
import project.BaekjoonStatus.shared.exception.SolvedAcUserNotFound;

import java.net.URI;
import java.nio.charset.Charset;

public class SolvedAcHttp {
    private static final String SOLVED_AC_URL = "https://solved.ac/api/v3";
    private static final String SOLVED_AC_GET_USER_PATH = "/user/show";
    private static final String SOLVED_AC_GET_PROBLEM_PATH = "/problem/show";

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
            throw new SolvedAcUserNotFound(CodeEnum.SOLVED_AC_USER_NOT_FOUND);
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
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.getForObject(uri, SolvedAcProblemResp.class);
        } catch (HttpClientErrorException e) {
            throw new SolvedAcProblemNotFound(CodeEnum.SOLVED_AC_PROBLEM_NOT_FOUND);
        }
    }
}
