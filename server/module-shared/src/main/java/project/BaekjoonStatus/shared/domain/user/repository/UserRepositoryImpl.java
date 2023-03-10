package project.BaekjoonStatus.shared.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.BaekjoonStatus.shared.domain.user.entity.User;

import javax.persistence.EntityManager;

import static project.BaekjoonStatus.shared.domain.user.entity.QUser.user;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public User findByUsername(String username) {
        return queryFactory.select(user)
                .from(user)
                .fetchOne();
    }
}
