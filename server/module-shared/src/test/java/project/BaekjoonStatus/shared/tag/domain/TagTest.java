package project.BaekjoonStatus.shared.tag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TagTest {
    @Test
    public void can_create_tag_domain() throws Exception {
        //given
        String id = UUID.randomUUID().toString();
        String tagName = "myTag";

        //when
        Tag tag = Tag.from(id, tagName);

        //then
        assertThat(tag.getId()).isEqualTo(id);
        assertThat(tag.getTagName()).isEqualTo(tagName);
    }

    @DisplayName("Tag도메인을 통해 problemId 기준으로 그룹핑을 할 수 있다.")
    @Test
    public void can_group_tag_domain_by_problem_id() throws Exception {
        //given
        Long problemId = 1000L;
        String[] tagNames = {"dp", "math", "implementation"};
        List<Tag> tags = createTagsWithProblemId(problemId, tagNames);

        //when
        Map<Long, List<Tag>> map = Tag.toMap(tags);

        //then
        assertThat(map.containsKey(problemId)).isTrue();
        assertThat(map.get(problemId)).hasSize(tagNames.length);
    }

    private List<Tag> createTagsWithProblemId(Long problemId, String ...tagNames) {
        Problem problem = createProblem(problemId);

        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(createTagWithProblem(problem, tagName));
        }

        return tags;
    }

    private Tag createTagWithProblem(Problem problem, String tagName) {
        return Tag.builder()
                .tagName(tagName)
                .problem(problem)
                .build();
    }

    private Problem createProblem(Long id) {
        return Problem.builder()
                .id(id)
                .title("test")
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
