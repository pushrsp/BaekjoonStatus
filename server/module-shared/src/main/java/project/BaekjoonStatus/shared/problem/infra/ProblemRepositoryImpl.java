package project.BaekjoonStatus.shared.problem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.common.repository.BaseRepository;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryImpl extends BaseRepository implements ProblemRepository {
    private final ProblemJpaRepository problemJpaRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public int saveAll(List<Problem> problems) {
        String sql =
                """
                INSERT INTO PROBLEM (problem_id, level, title, created_time)
                VALUES (:problem_id, :level, :title, :created_time)
                """;

        SqlParameterSource[] params = problems.stream()
                .map(this::generateParams)
                .toArray(SqlParameterSource[]::new);

        return namedParameterJdbcTemplate.batchUpdate(sql, params).length;
    }

    private SqlParameterSource generateParams(Problem problem) {
        return new MapSqlParameterSource()
                .addValue("problem_id", problem.getId())
                .addValue("level", problem.getLevel())
                .addValue("title", problem.getTitle())
                .addValue("created_time", problem.getCreatedTime());
    }

    @Override
    @Transactional
    public Problem save(Problem problem) {
        return problemJpaRepository.save(ProblemEntity.from(problem)).to();
    }

    @Override
    @Transactional
    public Problem saveAndFlush(Problem problem) {
        return problemJpaRepository.saveAndFlush(ProblemEntity.from(problem)).to();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Problem> findAllByIdsIn(List<String> ids) {
        List<Long> problemIds = ids.stream()
                .map(this::parseLong)
                .collect(Collectors.toList());

        return problemJpaRepository.findAllByIdIn(problemIds)
                .stream()
                .map(ProblemEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Problem> findById(String id) {
        return problemJpaRepository.findById(parseLong(id)).map(ProblemEntity::to);
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        problemJpaRepository.deleteAllInBatch();
    }
}
