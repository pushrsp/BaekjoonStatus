package project.BaekjoonStatus.shared.dailyproblem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.infra.DailyProblemRepository;
import project.BaekjoonStatus.shared.dailyproblem.service.request.DailyProblemCreateSharedServiceRequest;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class DailyProblemServiceTest extends IntegrationTestSupport {
    @Autowired
    private DailyProblemService dailyProblemService;

    @Autowired
    private DailyProblemRepository dailyProblemRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        dailyProblemRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("여러개의 daily problem을 여러개의 DailyProblemCreateSharedServiceRequest를 통해 동시에 저장할 수 있다.")
    @Test
    public void can_save_daily_problems_by_list_of_DailyProblemCreateSharedServiceRequest() throws Exception {
        //given
        List<Problem> problems = saveProblems(1, 10);

        LocalDate now = LocalDate.of(2023, 8, 21);
        List<DailyProblemCreateSharedServiceRequest> requests = createDailyProblemCreateSharedServiceRequests(problems, now);

        //when
        int size = dailyProblemService.saveAll(requests);

        //then
        assertThat(size).isEqualTo(problems.size());
    }

    @DisplayName("date service를 통해 daily problems을 찾을 수 있다.")
    @Test
    public void can_find_daily_problems_by_date_service() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 21, 12, 8);
        DateService dateService = createDateService(now);

        int startId = 1;
        int lastId = 4;

        saveDailyProblems(startId, lastId, dateService);
        saveDailyProblems(5, 10, createDateService(LocalDateTime.of(2022, 8, 21, 12, 8)));

        //when
        List<DailyProblem> dailyProblems = dailyProblemService.findAllByCreatedDate(dateService);

        //then
        assertThat(dailyProblems).hasSize(lastId - startId + 1);
    }

    @DisplayName("오늘의 문제를 구해올 수 있다.")
    @Test
    public void can_get_today_problem() throws Exception {
        //when
        List<Long> todayProblems = dailyProblemService.findTodayProblems();

        //then
        assertThat(todayProblems).hasSize(4);
    }

    private void saveDailyProblems(int startId, int lastId, DateService dateService) {
        List<Problem> problems = saveProblems(startId, lastId);
        List<DailyProblemCreateSharedServiceRequest> requests = createDailyProblemCreateSharedServiceRequests(problems, dateService.getDate());

        dailyProblemService.saveAll(requests);
    }

    private List<DailyProblemCreateSharedServiceRequest> createDailyProblemCreateSharedServiceRequests(List<Problem> problems, LocalDate createdDate) {
        return problems.stream()
                .map(p -> createDailyProblemCreateSharedServiceRequest(p.getId(), createdDate))
                .collect(Collectors.toList());

    }

    private DailyProblemCreateSharedServiceRequest createDailyProblemCreateSharedServiceRequest(String problemId, LocalDate createdDate) {
        return DailyProblemCreateSharedServiceRequest.builder()
                .problemId(problemId)
                .createdDate(createdDate)
                .build();
    }

    private List<Problem> saveProblems(int startId, int lastId) {
        List<Problem> problems = new ArrayList<>();
        for (int i = startId; i <= lastId; i++) {
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

    private DateService createDateService(LocalDateTime now) {
        return new DateService() {
            @Override
            public LocalDate getDate() {
                return now.toLocalDate();
            }

            @Override
            public LocalDate getToday(LocalDateTime now) {
                return now.toLocalDate();
            }

            @Override
            public LocalDateTime getDateTime() {
                return now;
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
