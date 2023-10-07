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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.baekjoon.service.BaekjoonService;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.problem.service.request.ProblemCreateSharedServiceRequest;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.service.MemberService;
import project.BaekjoonStatus.shared.common.template.ListDividerTemplate;
import project.BaekjoonStatus.shared.tag.service.request.TagCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SaveUserProblemJob {
    private static final int CHUNK_SIZE = 5;
    private static final int OFFSET = 100;

    private final DateService dateService;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SolvedAcService solvedAcService;
    private final BaekjoonService baekjoonService;

    private final MemberService userService;
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
                .<Member, List<SolvedHistory>>chunk(CHUNK_SIZE)
                .reader(new UserJpaPagingItemReader(userService, CHUNK_SIZE))
                .processor(this.userProblemItemProcessor())
                .writer(this.userProblemItemWriter())
                .build();
    }

    private ItemProcessor<Member, List<SolvedHistory>> userProblemItemProcessor() {
        return member -> {
            List<String> newIds = findNewIds(member);
            if(newIds.isEmpty()) {
                return null;
            }

            List<Problem> problems = new ArrayList<>();
            ListDividerTemplate<String> listDivider = new ListDividerTemplate<>(OFFSET, newIds);

            listDivider.execute((List<String> ids) -> {
                List<String> notSavedIds = problemService.findAllByNotExistedIds(ids);
                if(!notSavedIds.isEmpty()) {
                    LocalDateTime createdTime = dateService.getDateTime();

                    List<SolvedAcProblem> solvedAcProblems = solvedAcService.findByIds(notSavedIds);
                    List<Problem> newProblems = SolvedAcProblem.toProblemList(solvedAcProblems, createdTime);

                    problemService.saveAll(ProblemCreateSharedServiceRequest.from(newProblems));
                    tagService.saveAll(TagCreateSharedServiceRequest.from(solvedAcProblems));
                }

                problems.addAll(problemService.findAllByIdsIn(ids));
                return null;
            });

            return SolvedHistory.from(member, problems, false, dateService);
        };
    }

    private ItemWriter<List<SolvedHistory>> userProblemItemWriter() {
        return items -> {
            items.forEach(solvedHistoryService::saveAll);
        };
    }

    private List<String> findNewIds(Member user) {
        List<String> newHistories = baekjoonService.getProblemIdsByUsername (user.getBaekjoonUsername());
        List<String> oldHistories = solvedHistoryService.findAllByMemberId(user.getId())
                .stream()
                .map(SolvedHistory::getId)
                .collect(Collectors.toList());

        return newHistories.stream()
                .filter(id -> !oldHistories.contains(id))
                .collect(Collectors.toList());
    }
}
