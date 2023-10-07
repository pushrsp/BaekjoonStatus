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
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.dailyproblem.service.request.DailyProblemCreateSharedServiceRequest;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.problem.service.request.ProblemCreateSharedServiceRequest;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import org.springframework.batch.item.ItemReader;
import project.BaekjoonStatus.shared.tag.service.request.TagCreateSharedServiceRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SaveDailyProblemJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SolvedAcService solvedAcService;

    private final ProblemService problemService;
    private final TagService tagService;
    private final DailyProblemService dailyProblemService;

    private final DateService dateService;

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
        return new ListItemReader<>(dailyProblemService.findTodayProblems());
    }

    private ItemProcessor<Long, Problem> dailyProblemItemProcessor() {
        return problemId -> {
            Optional<Problem> optionalProblem = problemService.findById(String.valueOf(problemId));
            return optionalProblem.orElseGet(() -> saveProblem(problemId));
        };
    }

    private ItemWriter<Problem> dailyProblemItemWriter() {
        return problems -> {
            LocalDate now = dateService.getDate();
            List<DailyProblemCreateSharedServiceRequest> dailyProblems = problems.stream()
                    .map(p -> DailyProblemCreateSharedServiceRequest.of(p, now))
                    .collect(Collectors.toList());

            dailyProblemService.saveAll(dailyProblems);
        };
    }

    private Problem saveProblem(Long problemId) {
        LocalDateTime createdTime = dateService.getDateTime();

        SolvedAcProblem solvedAcProblem = solvedAcService.findById(problemId);
        Problem problem = problemService.saveAndFlush(ProblemCreateSharedServiceRequest.from(solvedAcProblem, createdTime));
        tagService.saveAll(TagCreateSharedServiceRequest.from(solvedAcProblem));

        return problem;
    }
}
