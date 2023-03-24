package project.BaekjoonStatus.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.BaekjoonStatus.api.argumentresolver.AuthArgumentResolver;
import project.BaekjoonStatus.api.interceptor.AuthInterceptor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Value("${token.secret}")
    private String tokenSecret;

    private static final String[] SET_MAX_AGE_PATTERN = {"/stats/**"};

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(tokenSecret));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //TODO: Cache
//        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.SECONDS);
//
//        registry.addResourceHandler("/stats/**")
//                .setCacheControl(cacheControl);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(tokenSecret))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/baekjoon", "/auth/signup", "/auth/login", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
