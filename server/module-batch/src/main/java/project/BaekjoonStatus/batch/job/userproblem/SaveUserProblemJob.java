package project.BaekjoonStatus.batch.job.userproblem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.baekjoon.BaekjoonService;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.UserService;
import project.BaekjoonStatus.shared.common.template.ListDividerTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SaveUserProblemJob {
    private static final int CHUNK_SIZE = 5;
    private static final int OFFSET = 100;
    private static final LocalDateTime CREATED_TIME = DateProvider.getDateTime();

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SolvedAcService solvedAcService;
    private final BaekjoonService baekjoonService;

    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;
    private final ProblemService problemService;
    private final TagService tagService;

    @Bean
    public Job userProblemJob() {
        return this.jobBuilderFactory.get("userProblemJob")
                .incrementer(new RunIdIncrementer())
                .start(this.userProblemStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step userProblemStep(@Value("#{jobParameters[date]}") String date) {
        return this.stepBuilderFactory.get(date + "_userProblemJob")
                .<User, List<SolvedHistory>>chunk(CHUNK_SIZE)
                .reader(new UserJpaPagingItemReader(userService, CHUNK_SIZE))
                .processor(this.userProblemItemProcessor())
                .writer(this.userProblemItemWriter())
                .build();
    }

    private ItemProcessor<User, List<SolvedHistory>> userProblemItemProcessor() {
        return user -> {
            List<Long> newIds = findNewIds(user);
            if(newIds.isEmpty()) {
                return null;
            }

            List<Problem> problems = new ArrayList<>();
            ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, newIds);

            listDivider.execute((List<Long> ids) -> {
                List<Long> notSavedIds = problemService.findAllByNotExistedIds(ids);
                if(!notSavedIds.isEmpty()) {
                    List<SolvedAcProblem> solvedAcProblems = solvedAcService.findByIds(notSavedIds);
                    List<Problem> newProblems = SolvedAcProblem.toProblemList(solvedAcProblems, CREATED_TIME);

                    problemService.saveAll(newProblems);
                    tagService.saveAll(SolvedAcProblem.toTagList(solvedAcProblems, CREATED_TIME));
                }

                problems.addAll(problemService.findAllByIdsIn(ids));
                return null;
            });

            return SolvedHistory.from(user, problems, false);
        };
    }

    private ItemWriter<List<SolvedHistory>> userProblemItemWriter() {
        return items -> {
            items.forEach(solvedHistoryService::saveAll);
        };
    }

    private List<Long> findNewIds(User user) {
        List<Long> newHistories = baekjoonService.getProblemIdsByUsername (user.getBaekjoonUsername());
        List<Long> oldHistories = solvedHistoryService.findAllByUserId(user.getId())
                .stream()
                .map(sh -> Long.parseLong(sh.getId()))
                .toList();

        return newHistories.stream()
                .filter(id -> !oldHistories.contains(id))
                .collect(Collectors.toList());
    }
}
