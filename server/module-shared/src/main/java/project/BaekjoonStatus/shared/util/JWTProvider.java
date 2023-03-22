package project.BaekjoonStatus.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    @Value("${token.secret}")
    private String secret;

    private static final Long EXPIRE_TIME = 1000L * 60 * 60 * 24; //하루

    public String generateToken(String userId) {
        return JWT.create()
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) {
        DecodedJWT info = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return info.getClaim("id").toString();
    }
}
