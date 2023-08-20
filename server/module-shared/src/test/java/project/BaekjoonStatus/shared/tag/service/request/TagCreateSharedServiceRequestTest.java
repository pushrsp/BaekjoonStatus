package project.BaekjoonStatus.shared.tag.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import static org.assertj.core.api.Assertions.*;

class TagCreateSharedServiceRequestTest {
    @DisplayName("tag 도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_tag_domain() throws Exception {
        //given
        TagCreateSharedServiceRequest request = TagCreateSharedServiceRequest.builder()
                .tagName("dp")
                .problemId("1000")
                .build();

        //when
        Tag tag = request.toDomain();

        //then
        assertThat(tag.getTagName()).isEqualTo(request.getTagName());
        assertThat(tag.getProblem().getId()).isEqualTo(request.getProblemId());
    }
}
