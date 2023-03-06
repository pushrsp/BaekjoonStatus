package project.BaekjoonStatus.shared.domain.tag.service;


import org.hibernate.type.UUIDBinaryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Rollback(value = false)
public class TagWriteServiceTest {
    @Autowired private TagWriteService tagWriteService;

    @Test
    @Transactional
    public void bulkInsertTest() throws Exception {
        //given
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            tags.add(new Tag(i + " test"));

        //when
        int totalLen = tagWriteService.bulkInsert(tags);

        //then
        Assertions.assertEquals(100, totalLen);
    }
}
