package project.BaekjoonStatus.shared.dailyproblem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DailyProblemRepositoryImpl implements DailyProblemRepository {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public int saveAll(List<DailyProblem> dailyProblems) {
        String sql =
                """
                INSERT INTO DAILY_PROBLEM (daily_problem_id, created_date, problem_id)
                VALUES (:daily_problem_id, :created_date, :problem_id)
                """;

        SqlParameterSource[] params = dailyProblems.stream()
                .map(this::generateParams)
                .toArray(SqlParameterSource[]::new);

        return namedParameterJdbcTemplate.batchUpdate(sql, params).length;
    }

    private SqlParameterSource generateParams(DailyProblem dailyProblem) {
        return new MapSqlParameterSource()
                .addValue("daily_problem_id", UUID.randomUUID().toString())
                .addValue("problem_id", dailyProblem.getProblem().getId())
                .addValue("created_date", dailyProblem.getCreatedDate());
    }

    @Override
    public List<DailyProblem> findAllByCreatedDate(LocalDate date) {
        return dailyProblemJpaRepository.findAllByCreatedDate(date)
                .stream()
                .map(DailyProblemEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInBatch() {
        dailyProblemJpaRepository.deleteAllInBatch();
    }
}
