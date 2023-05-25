package project.BaekjoonStatus.shared.domain.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.domain.user.entity.QUser;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.UserDto;

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
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
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
