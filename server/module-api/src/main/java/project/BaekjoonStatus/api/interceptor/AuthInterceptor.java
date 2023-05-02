package project.BaekjoonStatus.api.interceptor;

import lombok.RequiredArgsConstructor;
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
        if(authorization.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        String[] tokens = authorization.split(" ");
        if(tokens.length != 2)
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        String userId = JWTProvider.validateToken(tokens[1], tokenSecret);

        return !userId.isEmpty();
    }
}
