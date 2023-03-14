package project.BaekjoonStatus.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("TEST");
    private static final long EXPIRE_TIME = 3600;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(ALGORITHM);
    }

    public void validateToken(String token) {
    }
}
