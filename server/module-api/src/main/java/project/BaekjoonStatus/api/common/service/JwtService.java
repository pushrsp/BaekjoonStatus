package project.BaekjoonStatus.api.common.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;
import project.BaekjoonStatus.shared.common.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtService implements TokenService {
    @Override
    public String generate(String data, String secret, Long offset) {
        if(offset <= 0) {
            throw new IllegalArgumentException("offset 0보다 커야 됩니다.");
        }

        return JWT.create()
                .withClaim("data", data)
                .withExpiresAt(new Date(System.currentTimeMillis() + offset))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String extract(String authorization) {
        if(!StringUtils.hasText(authorization)) {
            throw new IllegalArgumentException("접근할 수 없는 페이지 입니다.");
        }

        String[] tokens = authorization.split(" ");
        if(tokens.length != 2) {
            throw new IllegalArgumentException("접근할 수 없는 페이지 입니다.");
        }

        return tokens[1];
    }

    @Override
    public String verify(String token, String secret) {
        try {
            DecodedJWT info = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return info.getClaim("data").asString();
        } catch (TokenExpiredException e) {
            throw new IllegalStateException("로그인을 다시 해주세요.");
        } catch (JWTDecodeException | SignatureVerificationException e) {
            throw new IllegalArgumentException("토큰 형식이 잘못되었습니다.");
        }
    }
}
