package project.BaekjoonStatus.shared.tag.domain;

import org.junit.jupiter.api.Test;

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
}
