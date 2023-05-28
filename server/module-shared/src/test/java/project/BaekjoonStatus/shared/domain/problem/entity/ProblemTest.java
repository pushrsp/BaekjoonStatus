package project.BaekjoonStatus.shared.domain.problem.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ProblemTest {
    @Test
    public void id_level_title_created_time은_필수입력() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> Problem.of(null, 1, "A + B", DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("id를 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, null, "A + B", DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("레벨을 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, -1, "A + B", DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("레벨은 0이상 30이하의 값만 설정할 수 있습니다.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, 31, "A + B", DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("레벨은 0이상 30이하의 값만 설정할 수 있습니다.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, 1, null, DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("제목을 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, 1, "", DateProvider.getDateTime()), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("제목을 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> Problem.of(1000L, 1, "A + B", null), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("생성날짜를 입력해주세요.");
    }

    @Test
    public void 정상값을_입력받을_때() throws Exception {
        LocalDateTime now = DateProvider.getDateTime();
        Problem problem = Problem.of(1000L, 1, "A + B", now);

        assertThat(problem.getId())
                .isEqualTo(1000L);
        assertThat(problem.getLevel())
                .isEqualTo(1);
        assertThat(problem.getTitle())
                .isEqualTo("A + B");
        assertThat(problem.getCreatedTime())
                .isEqualTo(now);
    }

    @Test
    public void solved_ac_응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        SolvedAcProblemResp info = solvedAcHttp.getProblemByProblemId(1000L);

        Problem problem = Problem.ofWithInfo(info);

        assertThat(problem.getId())
                .isEqualTo(info.getProblemId());
        assertThat(problem.getTitle())
                .isEqualTo(info.getTitleKo());
        assertThat(problem.getLevel())
                .isEqualTo(info.getLevel().intValue());
    }

    @Test
    public void solved_ac_list응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(getProblemIds());

        List<Problem> problems = Problem.ofWithInfos(infos);

        assertThat(problems)
                .hasSize(infos.size());
        assertThat(problems.stream().map(Problem::getId).collect(Collectors.toList()))
                .doesNotHaveDuplicates();
    }

    private List<Long> getProblemIds() {
        List<Long> ids = new ArrayList<>();
        for (long i = 1000; i <= 1010; i++) {
            ids.add(i);
        }

        return ids;
    }
}
