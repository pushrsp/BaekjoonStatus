package project.BaekjoonStatus.shared.domain.problem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Rollback(value = false)
public class ProblemWriteServiceTest {
    @Autowired
    private ProblemWriteService problemWriteService;

    @Test
    @Transactional
    public void 문제_벌크_생성() throws Exception {
        //given
        BaekjoonCrawling baekjoonCrawling = new BaekjoonCrawling("pushrsp");
        baekjoonCrawling.start();
        List<Long> solved = baekjoonCrawling.getSolved();

        //when
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();

        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(solved);
        List<Problem> problems = new ArrayList<>();

//        for (SolvedAcProblemResp info : infos)
//            problems.add(Problem.create(info.getProblemId(), info.getLevel().intValue(), info.getTitleKo(), new ArrayList<>()));


        int totalLen = problemWriteService.bulkInsert(problems).size();

        //then
        Assertions.assertEquals(solved.size(), totalLen);
    }
}
