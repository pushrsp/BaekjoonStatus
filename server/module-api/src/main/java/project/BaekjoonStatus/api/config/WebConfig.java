package project.BaekjoonStatus.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.BaekjoonStatus.api.interceptor.AuthInterceptor;
import project.BaekjoonStatus.shared.util.JWTProvider;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private static final String[] SET_MAX_AGE_PATTERN = {"/stats/**"};
    private final JWTProvider jwtProvider;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //TODO: Cache
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.SECONDS);

        registry.addResourceHandler("/stats/**")
                .setCacheControl(cacheControl);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtProvider))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/baekjoon", "/auth/signup", "/auth/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
