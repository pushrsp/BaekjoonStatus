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
    @DisplayName("tag 리스트를 통해 map을 만들 수 있다.")
    @Test
    public void can_convert_to_map_by_list_of_tag() throws Exception {
        //given
        List<Tag> t1 = createTagDomains(createProblemDomain("1000"), List.of("math", "dp"));
        List<Tag> t2 = createTagDomains(createProblemDomain("2000"), List.of("implementation", "dp", "test"));

        t1.addAll(t2);

        //when
        Map<String, List<Tag>> map = Tag.toMap(t1);

        //then
        assertThat(map.keySet()).hasSize(2);
        assertThat(map.keySet()).containsExactlyInAnyOrder("1000", "2000");
        assertThat(map.values()).hasSize(2);
    }

    private List<Tag> createTagDomains(Problem problem, List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(Tag.from(UUID.randomUUID().toString(), tagName, problem));
        }

        return tags;
    }

    private Problem createProblemDomain(String id) {
        return Problem.builder()
                .id(id)
                .title("title")
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
