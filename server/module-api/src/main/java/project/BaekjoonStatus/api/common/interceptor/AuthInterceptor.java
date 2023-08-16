package project.BaekjoonStatus.api.common.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import project.BaekjoonStatus.shared.common.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final String tokenSecret;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS"))
            return true;

        String token = tokenService.extract(request);
        String userId = tokenService.verify(token, tokenSecret);

        return !userId.isEmpty();
    }
}
