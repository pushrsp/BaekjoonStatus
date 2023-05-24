package project.BaekjoonStatus.batch.job.userproblem;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
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
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.template.ListDividerTemplate;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.DateProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SaveUserProblemJob {
    private static final int CHUNK_SIZE = 5;
    private static final int OFFSET = 100;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final SolvedAcHttp solvedAcHttp;

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
                .reader(this.userProblemItemReader())
                .processor(this.userProblemItemProcessor())
                .writer(this.userProblemItemWriter())
                .build();
    }

    private JpaPagingItemReader<User> userProblemItemReader() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("userProblemItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT u FROM User u")
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
                List<Long> notExistedIds = problemService.findAllByNotExistedIds(ids);
                saveNotExistedProblems(notExistedIds);
                problems.addAll(problemService.findAllByIdsIn(ids));
                return null;
            });

            return problems.stream()
                    .map(p -> SolvedHistory.ofWithUserAndProblem(user, p, false, DateProvider.getDate().minusDays(1), DateProvider.getDateTime().minusDays(1)))
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

    private List<Long> findNewIds(User user) {
        List<Long> newHistories = new BaekjoonCrawling(user.getBaekjoonUsername()).get();
        List<SolvedHistory> oldHistories = solvedHistoryService.findByUserId(user.getId().toString());

        Set<Long> newIds = new HashSet<>(newHistories);
        for (SolvedHistory oldHistory : oldHistories) {
            newIds.remove(oldHistory.getProblem().getId());
        }

        return new ArrayList<>(newIds);
    }
}
