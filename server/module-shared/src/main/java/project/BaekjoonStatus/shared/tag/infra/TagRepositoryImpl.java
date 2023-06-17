package project.BaekjoonStatus.shared.tag.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.port.TagRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return tagJpaRepository.save(TagEntity.from(tag)).to();
    }

    @Override
    @Transactional
    public void saveAll(List<Tag> tags) {
        String sql = """
                INSERT INTO TAG (tag_id, tag_name, problem_id)
                VALUES (:tag_id, :tag_name, :problem_id)
                """;

        SqlParameterSource[] params = tags.stream()
                .map(this::generateParams)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private SqlParameterSource generateParams(Tag tag) {
        return new MapSqlParameterSource()
                .addValue("tag_id", UUID.randomUUID().toString())
                .addValue("tag_name", tag.getTagName())
                .addValue("problem_id", tag.getProblem().getId());
    }

    @Override
    public List<Tag> findByProblemIdsIn(List<Long> problemIds) {
        return tagJpaRepository.findAllByProblemIdIn(problemIds)
                .stream()
                .map(TagEntity::to)
                .collect(Collectors.toList());
    }
}
