package project.BaekjoonStatus.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.Date;

public class JWTProvider {
    private static final Long EXPIRE_TIME = 1000L * 60 * 60 * 24; //하루

    public static String generateToken(String userId, String secret) {
        return JWT.create()
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(secret));
    }

    public static String validateToken(String authorization, String secret) {
        if(authorization.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        String[] token = authorization.split(" ");
        if(token.length != 2)
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        try {
            DecodedJWT info = JWT.require(Algorithm.HMAC256(secret)).build().verify(token[1]);
            return info.getClaim("id").asString();
        } catch (TokenExpiredException e) {
            throw new MyException(CodeEnum.MY_SERVER_TOKEN_EXPIRED);
        } catch (JWTDecodeException e) {
            throw new MyException(CodeEnum.MY_SERVER_NOT_VALID_TOKEN);
        }
    }
}
