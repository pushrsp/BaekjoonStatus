package project.BaekjoonStatus.api.common.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.BaekjoonStatus.api.common.argumentresolver.AuthArgumentResolver;
import project.BaekjoonStatus.api.common.interceptor.AuthInterceptor;
import project.BaekjoonStatus.shared.common.service.TokenService;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Value("${token.secret}")
    private String tokenSecret;

    private final TokenService tokenService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(tokenSecret, tokenService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(tokenSecret, tokenService))
                .order(1)
                .addPathPatterns("/stat/**", "/auth/me")
                .excludePathPatterns("/", "/auth/baekjoon", "/auth/signup", "/auth/login", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
