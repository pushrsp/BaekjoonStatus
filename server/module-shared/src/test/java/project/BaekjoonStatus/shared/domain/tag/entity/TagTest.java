package project.BaekjoonStatus.shared.domain.tag.entity;

import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.common.service.solvedac.SolvedAcHttp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class TagTest {
    @Test
    public void tagName과_Problem은_필수() throws Exception {
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> TagEntity.ofWithProblem(null, "abc"), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("문제를 입력해주세요.");

        ProblemEntity problem = ProblemEntity.of(1L, 1, "test", DateProvider.getDateTime());
        illegalArgumentException = catchThrowableOfType(() -> TagEntity.ofWithProblem(problem, ""), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("태그 이름을 입력해주세요.");

        illegalArgumentException = catchThrowableOfType(() -> TagEntity.ofWithProblem(problem, null), IllegalArgumentException.class);
        assertThat(illegalArgumentException.getMessage())
                .isEqualTo("태그 이름을 입력해주세요.");
    }

    @Test
    public void solved_ac_응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        SolvedAcProblemResponse info = solvedAcHttp.getProblemByProblemId(1000L);

        ProblemEntity problem = ProblemEntity.ofWithInfo(info);
        List<TagEntity> tags = info.getTags().stream()
                .map(name -> TagEntity.ofWithProblem(problem, name.getKey()))
                .collect(Collectors.toList());

        assertThat(tags).doesNotHaveDuplicates();
        assertThat(tags).hasSize(info.getTags().size());

        for (TagEntity tag : tags) {
            assertThat(tag.getTagName()).isNotNull();
            assertThat(tag.getTagName()).hasSizeGreaterThan(0);
        }
    }

    @Test
    public void solved_ac_list응답값_변환() throws Exception {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        List<SolvedAcProblemResponse> infos = solvedAcHttp.getProblemsByProblemIds(getProblemIds());

        List<ProblemEntity> problems = ProblemEntity.ofWithInfos(infos);
        List<TagEntity> tags = TagEntity.ofWithInfosAndProblems(infos, problems);

        assertThat(tags).hasSizeGreaterThan(0);
        for (TagEntity tag : tags) {
            assertThat(tag.getProblem()).isNotNull();
        }

        tags = TagEntity.ofWithInfosAndProblems(null, null);

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
