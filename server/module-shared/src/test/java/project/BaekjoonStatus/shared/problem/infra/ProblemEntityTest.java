package project.BaekjoonStatus.shared.problem.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ProblemEntityTest {
    @DisplayName("Problem엔티티는 tags필드를 제외한 Problem도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_problem_domain_from_problem_entity() throws Exception {
        //given
        UUID[] uuids = {UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()};
        String[] tagNames = {"a", "b", "c"};
        List<TagEntity> tagEntities = createTagEntities(tagNames, uuids);
        LocalDateTime now = LocalDateTime.of(2023, 7, 30, 13, 57);

        ProblemEntity problemEntity = createProblemEntity(1L, "test", tagEntities, now);

        //when
        Problem problemDomain = problemEntity.to();

        //then
        assertThat(problemDomain.getId()).isEqualTo(problemEntity.getId());
        assertThat(problemDomain.getTags()).isNullOrEmpty();
    }

    private ProblemEntity createProblemEntity(Long id, String title, List<TagEntity> tags, LocalDateTime now) {
        return ProblemEntity.builder()
                .id(id)
                .level(1)
                .title(title)
                .tags(tags)
                .createdTime(now)
                .build();
    }

    private List<TagEntity> createTagEntities(String[] tagNames, UUID[] uuids) {
        List<TagEntity> ret = new ArrayList<>();
        for (int i = 0; i < tagNames.length; i++) {
            ret.add(createTagEntity(tagNames[i], uuids[i]));
        }

        return ret;
    }

    private TagEntity createTagEntity(String tagName, UUID id) {
        return TagEntity.builder()
                .id(id)
                .tagName(tagName)
                .build();
    }
}
