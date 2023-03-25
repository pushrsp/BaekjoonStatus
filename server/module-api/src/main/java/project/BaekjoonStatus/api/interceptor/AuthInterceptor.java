package project.BaekjoonStatus.api.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
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
