package project.BaekjoonStatus.api.common.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import project.BaekjoonStatus.shared.common.utils.JWTProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final String tokenSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS"))
            return true;

        String token = JWTProvider.extractToken(request.getHeader("Authorization"));
        String userId = JWTProvider.validateToken(token, tokenSecret);

        return !userId.isEmpty();
    }
}
