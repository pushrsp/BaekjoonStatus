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
import project.BaekjoonStatus.shared.dailyproblem.infra.DailyProblemEntity;
import project.BaekjoonStatus.shared.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.common.service.github.DailyProblemCrawling;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcHttp;
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
    private final DailyProblemCrawling dailyProblemCrawling;

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
                .<Long, ProblemEntity>chunk(4)
                .reader(this.dailyProblemItemReader())
                .processor(this.dailyProblemItemProcessor())
                .writer(this.dailyProblemItemWriter())
                .build();
    }

    private ItemReader<Long> dailyProblemItemReader() {
        return new ListItemReader<>(dailyProblemCrawling.get());
    }

    private ItemProcessor<Long, ProblemEntity> dailyProblemItemProcessor() {
        return problemId -> {
            Optional<ProblemEntity> optionalProblem = problemService.findById(problemId);
            return optionalProblem.orElseGet(() -> saveProblem(problemId));
        };
    }

    private ItemWriter<ProblemEntity> dailyProblemItemWriter() {
        return problems -> {
            List<DailyProblemEntity> dailyProblems = problems.stream()
                    .map(DailyProblemEntity::ofWithProblem)
                    .collect(Collectors.toList());

            dailyProblemService.saveAll(dailyProblems);
        };
    }

    private ProblemEntity saveProblem(Long problemId) {
        SolvedAcProblemResponse info = solvedAcHttp.getProblemByProblemId(problemId);
        ProblemEntity problem = problemService.save(ProblemEntity.ofWithInfo(info));

        saveTags(problem, info);

        return problem;
    }

    private void saveTags(ProblemEntity problem, SolvedAcProblemResponse info) {
        List<TagEntity> tags = info.getTags().stream()
                .map((tagInfo) -> TagEntity.ofWithProblem(problem, tagInfo.getKey()))
                .collect(Collectors.toList());

        tagService.saveAll(tags);
    }
}
