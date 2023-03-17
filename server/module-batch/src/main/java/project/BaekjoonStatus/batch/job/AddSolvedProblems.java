package project.BaekjoonStatus.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.application.CreateProblemsAndTagsUsecase;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemReadService;
import project.BaekjoonStatus.shared.dto.command.CreateProblemsAndTagsCommand;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.util.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AddSolvedProblems {
    private static final int CHUNK_SIZE = 100;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProblemReadService problemReadService;
    private final CreateProblemsAndTagsUsecase createProblemsAndTagsUsecase;

    public Job addSolvedProblemsJob(String date, String baekjoonUsername) {
        return this.jobBuilderFactory.get(date + "_addSolvedProblemsJob")
                .incrementer(new RunIdIncrementer())
                .start(this.addSolvedProblemsStep(date, baekjoonUsername))
                .build();
    }

    public Step addSolvedProblemsStep(String date, String baekjoonUsername) {
        return this.stepBuilderFactory.get(date + "_addSolvedProblemsStep")
                .<Long, Long>chunk(CHUNK_SIZE)
                .reader(new ProblemItemReader(baekjoonUsername))
                .writer(itemWriter())
                .build();
    }

    private ItemWriter<Long> itemWriter() {
        return items -> {
            Set<Long> problemSet = new HashSet<>(items);

            List<Problem> findProblems = problemReadService.findByIds((List<Long>) items);
            for (Problem findProblem : findProblems)
                problemSet.remove(findProblem.getId());

            SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
            List<SolvedAcProblemResp> info = solvedAcHttp.getProblemsByProblemIds(problemSet.stream().toList());

            CreateProblemsAndTagsCommand command = CreateProblemsAndTagsCommand.builder()
                    .problemInfos(info)
                    .build();

            createProblemsAndTagsUsecase.execute(command);
        };
    }
}
