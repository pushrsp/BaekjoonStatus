package project.BaekjoonStatus.shared.dailyproblem.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class DailyProblemEntityTest {
    @DisplayName("DailyProblem도메인으로부터 DailyProblem엔티티를 생성할 수 있다.")
    @Test
    public void can_create_daily_problem_entity_from_daily_problem_domain() throws Exception {
        //given
        Problem problemDomain = createProblemDomain(1L, "test", 1);
        DailyProblem dailyProblemDomain = createDailyProblemDomain(problemDomain);

        //when
        DailyProblemEntity dailyProblemEntity = DailyProblemEntity.from(dailyProblemDomain);

        //then
        assertThat(dailyProblemEntity.getCreatedDate()).isEqualTo(dailyProblemDomain.getCreatedDate());
        assertThat(dailyProblemEntity.getProblem().getId()).isEqualTo(dailyProblemDomain.getProblem().getId());
    }

    @DisplayName("DailyProblem엔티티는 DailyProblem도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_daily_problem_domain_from_daily_problem_entity() throws Exception {
        //given
        ProblemEntity problemEntity = createProblemEntity();
        DailyProblemEntity dailyProblemEntity = createDailyProblemEntity(problemEntity);

        //when
        DailyProblem dailyProblemDomain = dailyProblemEntity.to();

        //then
        assertThat(dailyProblemDomain.getId()).isEqualTo(dailyProblemEntity.getId().toString());
        assertThat(dailyProblemDomain.getCreatedDate()).isEqualTo(dailyProblemEntity.getCreatedDate());
        assertThat(dailyProblemDomain.getProblem().getId()).isEqualTo(dailyProblemEntity.getProblem().getId());
    }

    private DailyProblemEntity createDailyProblemEntity(ProblemEntity problem) {
        return DailyProblemEntity.builder()
                .id(UUID.randomUUID())
                .problem(problem)
                .createdDate(LocalDate.now())
                .build();
    }

    private ProblemEntity createProblemEntity() {
        return ProblemEntity.builder()
                .id(1L)
                .level(1)
                .title("test")
                .createdTime(LocalDateTime.now())
                .build();
    }

    private Problem createProblemDomain(Long id, String title, Integer level) {
        return Problem.builder()
                .id(id)
                .title(title)
                .level(level)
                .createdTime(LocalDateTime.now())
                .build();
    }

    private DailyProblem createDailyProblemDomain(Problem problem) {
        return DailyProblem.builder()
                .id("id")
                .problem(problem)
                .createdDate(LocalDate.now())
                .build();
    }
}
