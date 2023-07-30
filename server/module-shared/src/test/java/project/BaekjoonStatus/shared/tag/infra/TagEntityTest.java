package project.BaekjoonStatus.shared.tag.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.UUID;

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
        assertThat(tagEntity.getProblem().getId()).isEqualTo(tagDomain.getProblem().getId());
    }

    @DisplayName("Tag엔티티는 Tag도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_tag_domain_from_tag_entity() throws Exception {
        //given
        ProblemEntity problemEntity = createProblemEntity();
        TagEntity tagEntity = createTagEntity(UUID.randomUUID(), "test", problemEntity);

        //when
        Tag tagDomain = tagEntity.to();

        //then
        assertThat(tagDomain.getId()).isEqualTo(tagEntity.getId().toString());
        assertThat(tagDomain.getTagName()).isEqualTo(tagEntity.getTagName());
        assertThat(tagDomain.getProblem().getId()).isEqualTo(tagEntity.getProblem().getId());
    }

    private ProblemEntity createProblemEntity() {
        return ProblemEntity.builder()
                .id(1L)
                .level(1)
                .title("test")
                .createdTime(LocalDateTime.now())
                .build();
    }

    private TagEntity createTagEntity(UUID id, String tagName, ProblemEntity problem) {
        return TagEntity.builder()
                .id(id)
                .tagName(tagName)
                .problem(problem)
                .build();
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
