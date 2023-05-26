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
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserService;
import project.BaekjoonStatus.shared.dto.UserDto;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.template.ListDividerTemplate;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.DateProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SaveUserProblemJob {
    private static final int CHUNK_SIZE = 5;
    private static final int OFFSET = 100;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SolvedAcHttp solvedAcHttp;

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
                .<UserDto, List<SolvedHistory>>chunk(CHUNK_SIZE)
                .reader(new UserJpaPagingItemReader(userService, CHUNK_SIZE))
                .processor(this.userProblemItemProcessor())
                .writer(this.userProblemItemWriter())
                .build();
    }

    private ItemProcessor<UserDto, List<SolvedHistory>> userProblemItemProcessor() {
        return userDto -> {
            List<Long> newIds = findNewIds(userDto);
            if(newIds.isEmpty()) {
                return null;
            }

            List<Problem> problems = new ArrayList<>();
            ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, newIds);

            listDivider.execute((List<Long> ids) -> {
                List<Long> notExistedIds = problemService.findAllByNotExistedIds(ids);
                saveNotExistedProblems(notExistedIds);
                problems.addAll(problemService.findAllByIdsIn(ids));
                return null;
            });

            return problems.stream()
                    .map(p -> SolvedHistory.ofWithUserAndProblem(User.from(userDto), p, false, DateProvider.getDate().minusDays(1), DateProvider.getDateTime().minusDays(1)))
                    .collect(Collectors.toList());
        };
    }

    private ItemWriter<List<SolvedHistory>> userProblemItemWriter() {
        return items -> {
            for (List<SolvedHistory> item : items) {
                solvedHistoryService.saveAll(item);
            }
        };
    }

    private void saveNotExistedProblems(List<Long> ids) {
        if(ids.isEmpty()) {
            return;
        }

        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(ids);
        List<Problem> problems = problemService.saveAll(Problem.ofWithInfos(infos));
        tagService.saveAll(Tag.ofWithInfosAndProblems(infos, problems));
    }

    private List<Long> findNewIds(UserDto user) {
        List<Long> newHistories = new BaekjoonCrawling(user.getBaekjoonUsername()).get();
        List<SolvedHistory> oldHistories = solvedHistoryService.findAllByUserId(user.getUserId());

        Set<Long> newIds = new HashSet<>(newHistories);
        for (SolvedHistory oldHistory : oldHistories) {
            newIds.remove(oldHistory.getProblem().getId());
        }

        return new ArrayList<>(newIds);
    }
}
