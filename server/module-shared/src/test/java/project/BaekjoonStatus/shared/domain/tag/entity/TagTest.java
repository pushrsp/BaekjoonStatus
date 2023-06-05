package project.BaekjoonStatus.shared.domain.tag.entity;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class TagTest {
    @Test
    public void tagName과_Problem은_필수() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> Tag.ofWithProblem(null, "abc"), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("문제를 입력해주세요.");

        Problem problem = Problem.of(1L, 1, "test", DateProvider.getDateTime());
        illegalArgumentException = catchThrowableOfType(() -> Tag.ofWithProblem(problem, ""), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("태그 이름을 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> Tag.ofWithProblem(problem, null), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("태그 이름을 입력해주세요.");
    }

    @Test
    public void solved_ac_응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        SolvedAcProblemResp info = solvedAcHttp.getProblemByProblemId(1000L);

        Problem problem = Problem.ofWithInfo(info);
        List<Tag> tags = info.getTags().stream()
                .map(name -> Tag.ofWithProblem(problem, name.getKey()))
                .collect(Collectors.toList());

        assertThat(tags).doesNotHaveDuplicates();
        assertThat(tags).hasSize(info.getTags().size());

        for (Tag tag : tags) {
            assertThat(tag.getTagName()).isNotNull();
            assertThat(tag.getTagName()).hasSizeGreaterThan(0);
        }
    }

    @Test
    public void solved_ac_list응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(getProblemIds());

        List<Problem> problems = Problem.ofWithInfos(infos);
        List<Tag> tags = Tag.ofWithInfosAndProblems(infos, problems);

        assertThat(tags).hasSizeGreaterThan(0);
        for (Tag tag : tags) {
            assertThat(tag.getProblem()).isNotNull();
        }

        tags = Tag.ofWithInfosAndProblems(null, null);

        assertThat(tags).hasSize(0);
    }

    private List<Long> getProblemIds() {
        List<Long> ids = new ArrayList<>();
        for (long i = 1000; i <= 1010; i++) {
            ids.add(i);
        }

        return ids;
    }
}
