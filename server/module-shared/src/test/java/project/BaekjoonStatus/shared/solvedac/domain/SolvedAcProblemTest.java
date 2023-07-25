package project.BaekjoonStatus.shared.solvedac.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class SolvedAcProblemTest {
    @DisplayName("solved.ac 응답값은 도메인 코드로 변환이 가능하다.")
    @Test
    public void can_convert_to_problem_domain() throws Exception {
        //given
        String[] tagNames = {"a", "b", "c"};
        SolvedAcProblem solvedAcProblem = createSolvedAcProblem(1000L, tagNames);
        LocalDateTime createdTime = LocalDateTime.of(2023, 5, 1, 13, 0);

        //when
        Problem problem = solvedAcProblem.toDomain(createdTime);
        List<Tag> tags = solvedAcProblem.toTagList(problem);

        //then
        assertThat(problem.getId()).isEqualTo(solvedAcProblem.getProblemId());
        assertThat(problem.getCreatedTime()).isEqualTo(createdTime);

        assertThat(tags).hasSize(tagNames.length);
        assertThat(tags).extracting( "tagName").contains(tagNames);
        assertThat(tags).extracting( "problem").contains(problem);
    }

    @DisplayName("SolvedAcProblem 리스트는 tag와 problem 리스트로 나눌 수 있다.")
    @Test
    public void list_of_solved_ac_problem_can_be_seperated_problem_list_and_tag_list() throws Exception {
        //given
        int size = 10;
        String[] tagNames = {"a", "b", "c"};
        LocalDateTime createdTime = LocalDateTime.of(2023, 5, 1, 1, 0);
        List<SolvedAcProblem> solvedAcProblems = createSolvedAcProblems(size, tagNames);

        //when
        List<Problem> problems = SolvedAcProblem.toProblemList(solvedAcProblems, createdTime);
        List<Tag> tags = SolvedAcProblem.toTagList(solvedAcProblems, createdTime);

        //then
        assertThat(problems).hasSize(size);
        assertThat(problems).extracting("createdTime").contains(createdTime);

        assertThat(tags).hasSize(size * tagNames.length);
        assertThat(tags).extracting("tagName").contains(tagNames);
    }

    private List<SolvedAcProblem> createSolvedAcProblems(int size, String ...tagNames) {
        List<SolvedAcProblem> solvedAcProblems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            solvedAcProblems.add(createSolvedAcProblem((long) i, tagNames));
        }

        return solvedAcProblems;
    }

    private SolvedAcProblem createSolvedAcProblem(Long problemId, String ...tagNames) {
        List<SolvedAcTag> tags = Arrays.stream(tagNames)
                .map(tagName -> SolvedAcTag.builder()
                        .key(tagName)
                        .build())
                .collect(Collectors.toList());

        return SolvedAcProblem.builder()
                .problemId(problemId)
                .level(14L)
                .tags(tags)
                .titleKo("알고리즘 문제")
                .build();
    }
}
