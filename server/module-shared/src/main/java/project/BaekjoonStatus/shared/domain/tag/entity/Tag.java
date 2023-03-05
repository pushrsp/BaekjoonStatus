package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "TAG")
@Data
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "eng_name", nullable = false, length = 100)
    private String engName;

    @Column(name = "kor_name", nullable = false, length = 100)
    private String korName;

}
