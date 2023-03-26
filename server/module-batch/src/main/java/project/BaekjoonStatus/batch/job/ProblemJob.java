package project.BaekjoonStatus.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.service.DailyProblemService;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.DailyProblemCrawling;
import project.BaekjoonStatus.shared.util.DateProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class ProblemJob {
    private static final SolvedAcHttp SOLVED_AC_HTTP = new SolvedAcHttp();
    private static final int PROBLEM_ID_OFFSET = 100;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ProblemService problemService;
    private final TagService tagService;
    private final DailyProblemService dailyProblemService;
    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;

    @Bean
    public Job saveProblemJob() {
        return this.jobBuilderFactory.get("saveProblemJob")
                .incrementer(new RunIdIncrementer())
                .start(this.saveDailyProblemStep(null))
                .next(this.saveUserSolvedProblemStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step saveDailyProblemStep(@Value("#{jobParameters[date]}") String date) {
        return this.stepBuilderFactory.get(date + "_saveDailyProblemStep")
                .<Long, Problem>chunk(1)
                .reader(this.saveDailyProblemItemReader())
                .processor(this.saveDailyProblemItemProcessor())
                .writer(this.saveDailyProblemItemWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step saveUserSolvedProblemStep(@Value("#{jobParameters[date]}") String date) {
        return this.stepBuilderFactory.get(date + "saveUserSolvedProblemStep")
                .<User, Map<User, List<Problem>>>chunk(5)
                .reader(this.saveUserSolvedProblemItemReader())
                .processor(this.saveUserSolvedProblemItemProcessor())
                .writer(this.saveUserSolvedProblemItemWriter())
                .build();
    }

    private ItemReader<User> saveUserSolvedProblemItemReader() {
        return new ListItemReader<>(userService.findAll());
    }

    private ItemProcessor<User, Map<User, List<Problem>>> saveUserSolvedProblemItemProcessor() {
        return user -> {
            BaekjoonCrawling crawling = new BaekjoonCrawling(user.getBaekjoonUsername());
            List<Long> newSolvedHistories = crawling.getMySolvedHistories();
            List<SolvedHistory> oldSolvedHistories = solvedHistoryService.findAllByUserId(user.getId());

            if(newSolvedHistories.size() == oldSolvedHistories.size())
                return null;

            Map<User, List<Problem>> ret = new HashMap<>();
            Set<Long> newIds = new HashSet<>(newSolvedHistories);
            for (SolvedHistory solved : oldSolvedHistories)
                newIds.remove(solved.getProblem().getId());

            List<Long> ids = newIds.stream().toList();
            List<Problem> problems = new ArrayList<>();

            int startIndex = 0;
            while (startIndex < ids.size()) {
                List<Long> problemIds = ids.subList(startIndex, Math.min(startIndex + PROBLEM_ID_OFFSET, ids.size()));
                startIndex += PROBLEM_ID_OFFSET;

                List<Long> saveIds = problemService.findProblemIdsByNotIn(problemIds);
                if(!saveIds.isEmpty()) {
                    List<SolvedAcProblemResp> infos = SOLVED_AC_HTTP.getProblemsByProblemIds(saveIds);
                    List<Problem> newProblems = problemService.saveAll(infos);
                    tagService.saveAll(infos, newProblems);
                }

                problems.addAll(problemService.findAllByIds(problemIds));
            }

            ret.put(user, problems);

            return ret;
        };
    }

    private ItemWriter<Map<User, List<Problem>>> saveUserSolvedProblemItemWriter() {
        return items -> {
            LocalDate now = DateProvider.getDate().minusDays(1);
            for (Map<User, List<Problem>> item : items) {
                for (User user : item.keySet())
                    solvedHistoryService.bulkInsert(SolvedHistory.create(user, item.get(user), false, now));
            }
        };
    }

    private ItemReader<Long> saveDailyProblemItemReader() {
        return new ListItemReader<>(new DailyProblemCrawling().start());
    }

    private ItemProcessor<Long, Problem> saveDailyProblemItemProcessor() {
        return problemId -> {
            Optional<Problem> findProblem = problemService.findById(problemId);
            if(findProblem.isPresent())
                return findProblem.get();

            SolvedAcProblemResp problemInfo = SOLVED_AC_HTTP.getProblemByProblemId(problemId);
            Problem problem = problemService.save(Problem.create(problemInfo));
            List<Tag> tags =  problemInfo.getTags().stream()
                    .map((tagInfo) -> Tag.create(problem, tagInfo.getKey()))
                    .toList();

            tagService.bulkInsert(tags);

            return problem;
        };
    }

    private ItemWriter<Problem> saveDailyProblemItemWriter() {
        return items -> {
            List<DailyProblem> dailyProblems = new ArrayList<>();
            for (Problem problem : items)
                dailyProblems.add(DailyProblem.create(problem));

            dailyProblemService.bulkInsert(dailyProblems);
        };
    }
}
