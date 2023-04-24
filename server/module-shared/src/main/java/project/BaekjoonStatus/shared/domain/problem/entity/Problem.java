package project.BaekjoonStatus.shared.domain.problem.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem implements Persistable<Long> {
    @Id
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "title", length = 1000)
    private String title;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    public Problem(Long id, Integer level, String title, LocalDateTime createdTime) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.createdTime = createdTime;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
