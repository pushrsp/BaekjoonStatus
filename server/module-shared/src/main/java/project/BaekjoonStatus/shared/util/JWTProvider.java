package project.BaekjoonStatus.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.Date;

public class JWTProvider {
    public static String generateToken(String userId, String secret, Long expiredOffset) {
        if(expiredOffset <= 0) {
            throw new IllegalArgumentException("expiredOffset 0보다 커야 됩니다.");
        }

        return JWT.create()
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredOffset))
                .sign(Algorithm.HMAC256(secret));
    }

    public static String extractToken(String authorization) {
        if(authorization.isEmpty() || !StringUtils.hasText(authorization)) {
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);
        }

        String[] tokens = authorization.split(" ");
        if(tokens.length != 2) {
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);
        }

        return tokens[1];
    }

    public static String validateToken(String token, String secret) {
        try {
            DecodedJWT info = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return info.getClaim("id").asString();
        } catch (TokenExpiredException e) {
            throw new MyException(CodeEnum.MY_SERVER_TOKEN_EXPIRED);
        } catch (JWTDecodeException | SignatureVerificationException e) {
            throw new MyException(CodeEnum.MY_SERVER_NOT_VALID_TOKEN);
        }
    }
}
