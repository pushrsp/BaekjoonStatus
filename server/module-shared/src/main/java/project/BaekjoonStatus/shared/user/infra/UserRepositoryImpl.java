package project.BaekjoonStatus.shared.user.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.user.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static project.BaekjoonStatus.shared.user.infra.QUserEntity.userEntity;

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
        return userJpaRepository.save(UserEntity.from(user)).to();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::to);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(UserEntity::to);
    }

    @Override
    public List<User> findAllByGreaterThanUserId(Long userId, int limit) {
        return queryFactory
                .select(Projections.constructor(User.class, userEntity.id.as("userId"), userEntity.username, userEntity.baekjoonUsername))
                .from(userEntity)
                .where(userEntity.id.gt(userId))
                .limit(limit)
                .fetch();
    }

    @Override
    public void deleteAllInBatch() {
        userJpaRepository.deleteAllInBatch();
    }
}
