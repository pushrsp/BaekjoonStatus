package project.BaekjoonStatus.shared.solvedac.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SolvedAcService {
    private static final String URL = "https://solved.ac/api/v3";
    private static final String PROBLEM = "/problem/show";
    private static final String PROBLEMS = "/problem/lookup";

    public SolvedAcProblem findById(Long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(URL)
                .path(PROBLEM)
                .queryParam("problemId", id)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(uri, SolvedAcProblem.class);
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    private List<SolvedAcProblem> findByIds(String ids) {
        URI uri = UriComponentsBuilder.fromHttpUrl(URL)
                .path(PROBLEMS)
                .queryParam("problemIds", ids)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(uri, SolvedAcProblem[].class))).collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }
    }

    public List<SolvedAcProblem> findByIds(List<Long> ids) {
        return findByIds(ids.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
