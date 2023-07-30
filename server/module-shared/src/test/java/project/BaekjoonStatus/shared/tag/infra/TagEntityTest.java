package project.BaekjoonStatus.shared.tag.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class TagEntityTest {
    @DisplayName("Tag도메인으로부터 Tag엔티티를 생성할 수 있다.")
    @Test
    public void can_create_tag_entity_from_tag_domain() throws Exception {
        //given
        Problem problemDomain = createProblemDomain(1L, "test", 1);
        Tag tagDomain = createTagDomain("test", problemDomain);

        //when
        TagEntity tagEntity = TagEntity.from(tagDomain);

        //then
        assertThat(tagEntity.getTagName()).isEqualTo(tagDomain.getTagName());
        assertThat(tagEntity.getProblem()).isNotNull();
    }

    private Tag createTagDomain(String tagName, Problem problem) {
        return Tag.builder()
                .tagName(tagName)
                .problem(problem)
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
}
