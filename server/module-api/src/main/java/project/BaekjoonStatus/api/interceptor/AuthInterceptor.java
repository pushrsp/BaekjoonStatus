package project.BaekjoonStatus.api.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.HandlerInterceptor;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.JWTProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final String tokenSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS"))
            return true;

        String authorization = request.getHeader("Authorization");
        String userId = JWTProvider.validateToken(authorization, tokenSecret);

        return !userId.isEmpty();
    }
}
