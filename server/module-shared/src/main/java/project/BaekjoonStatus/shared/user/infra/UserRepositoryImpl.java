package project.BaekjoonStatus.shared.user.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.common.domain.dto.UserDto;
import project.BaekjoonStatus.shared.user.service.port.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static project.BaekjoonStatus.shared.domain.user.entity.QUser.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserRepositoryImpl(EntityManager em, UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllByGreaterThanUserId(Long userId, int limit) {
        return queryFactory
                .select(Projections.bean(UserDto.class, user.id.as("userId"), user.username, user.baekjoonUsername))
                .from(user)
                .where(user.id.gt(userId))
                .limit(limit)
                .fetch();
    }
}