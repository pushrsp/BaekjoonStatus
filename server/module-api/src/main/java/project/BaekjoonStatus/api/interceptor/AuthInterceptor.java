package project.BaekjoonStatus.api.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.JWTProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JWTProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if(Strings.isBlank(authorization))
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        String[] token = authorization.split(" ");
        if(token.length != 2)
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        try {
            jwtProvider.validateToken(token[1]);
            return true;
        } catch (TokenExpiredException e) {
            throw new MyException(CodeEnum.MY_SERVER_TOKEN_EXPIRED);
        } catch (JWTDecodeException e) {
            throw new MyException(CodeEnum.MY_SERVER_NOT_VALID_TOKEN);
        }
    }
}
