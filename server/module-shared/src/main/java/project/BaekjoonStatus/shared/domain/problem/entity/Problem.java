package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
@Data
public class Problem {

    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private int level;

    @OneToMany(mappedBy = "problem")
    private List<ProblemTag> problemTags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(name = "modified_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedTime;

}
