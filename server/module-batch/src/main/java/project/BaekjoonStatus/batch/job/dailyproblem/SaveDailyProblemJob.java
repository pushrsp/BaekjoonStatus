package project.BaekjoonStatus.batch.job.dailyproblem;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.MyCrawling;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;
import org.springframework.batch.item.ItemReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SaveDailyProblemJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SolvedAcHttp solvedAcHttp;
    private final MyCrawling dailyProblemCrawling;

    private final ProblemService problemService;
    private final TagService tagService;
    private final DailyProblemService dailyProblemService;

    @Bean
    public Job dailyProblemJob() {
        return this.jobBuilderFactory.get("dailyProblemJob")
                .incrementer(new RunIdIncrementer())
                .start(this.dailyProblemStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step dailyProblemStep(@Value("#{jobParameters[date]}") String date) {
        return this.stepBuilderFactory.get(date + "_dailyProblemStep")
                .<Long, Problem>chunk(4)
                .reader(this.dailyProblemItemReader())
                .processor(this.dailyProblemItemProcessor())
                .writer(this.dailyProblemItemWriter())
                .build();
    }

    private ItemReader<Long> dailyProblemItemReader() {
        return new ListItemReader<>(dailyProblemCrawling.get());
    }

    private ItemProcessor<Long, Problem> dailyProblemItemProcessor() {
        return problemId -> {
            Optional<Problem> optionalProblem = problemService.findById(problemId);
            return optionalProblem.orElseGet(() -> saveProblem(problemId));
        };
    }

    private ItemWriter<Problem> dailyProblemItemWriter() {
        return problems -> {
            List<DailyProblem> dailyProblems = problems.stream()
                    .map(DailyProblem::ofWithProblem)
                    .collect(Collectors.toList());

            dailyProblemService.saveAll(dailyProblems);
        };
    }

    private Problem saveProblem(Long problemId) {
        SolvedAcProblemResp info = solvedAcHttp.getProblemByProblemId(problemId);
        Problem problem = problemService.save(Problem.ofWithInfo(info));

        saveTags(problem, info);

        return problem;
    }

    private void saveTags(Problem problem, SolvedAcProblemResp info) {
        List<Tag> tags = info.getTags().stream()
                .map((tagInfo) -> Tag.ofWithProblem(problem, tagInfo.getKey()))
                .collect(Collectors.toList());

        tagService.saveAll(tags);
    }
}