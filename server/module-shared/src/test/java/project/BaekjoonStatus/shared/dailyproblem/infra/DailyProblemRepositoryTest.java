package project.BaekjoonStatus.shared.dailyproblem.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class DailyProblemRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private DailyProblemRepository dailyProblemRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        dailyProblemRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("여러개의 daily problem을 동시에 저장할 수 있다.")
    @Test
    public void can_save_list_of_daily_problem() throws Exception {
        //given
        List<Problem> problems = saveProblems(4);

        LocalDate today = LocalDate.of(2023, 8, 21);
        List<DailyProblem> dailyProblems = createDailyProblems(problems, today);

        //when
        int size = dailyProblemRepository.saveAll(dailyProblems);

        //then
        assertThat(size).isEqualTo(problems.size());
    }

    @DisplayName("createdDate를 통해 daily problem을 찾을 수 있다.")
    @Test
    public void can_find_daily_problem_by_created_date() throws Exception {
        //given
        List<Problem> problems = saveProblems(4);

        LocalDate today1 = LocalDate.of(2023, 8, 21);
        LocalDate today2 = LocalDate.of(2022, 8, 21);

        List<DailyProblem> dailyProblems1 = createDailyProblems(problems, today1);
        List<DailyProblem> dailyProblems2 = createDailyProblems(problems, today2);

        dailyProblemRepository.saveAll(dailyProblems1);
        dailyProblemRepository.saveAll(dailyProblems2);

        //when
        List<DailyProblem> result1 = dailyProblemRepository.findAllByCreatedDate(today1);
        List<DailyProblem> result2 = dailyProblemRepository.findAllByCreatedDate(today2);

        //then
        assertThat(result1).hasSize(problems.size());
        assertThat(result2).hasSize(problems.size());
    }

    private List<DailyProblem> createDailyProblems(List<Problem> problems, LocalDate createdDate) {
        return problems.stream()
                .map(p -> DailyProblem.of(p, createdDate))
                .collect(Collectors.toList());
    }

    private List<Problem> saveProblems(int size) {
        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Problem problem = Problem.builder()
                    .id(String.valueOf(i))
                    .title("title " + i)
                    .level(1)
                    .createdTime(LocalDateTime.now())
                    .build();

            problems.add(problem);
        }

        problemRepository.saveAll(problems);

        return problems;
    }
}
