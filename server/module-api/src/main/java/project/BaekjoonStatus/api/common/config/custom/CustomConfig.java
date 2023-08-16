package project.BaekjoonStatus.api.common.config.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.api.common.service.BcryptService;
import project.BaekjoonStatus.api.common.service.JwtService;
import project.BaekjoonStatus.api.common.service.KrDateService;
import project.BaekjoonStatus.api.concurrent.CustomThreadPoolExecutor;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.common.service.TokenService;

@Configuration
public class CustomConfig {
    @Bean
    public CustomThreadPoolExecutor customThreadPoolExecutor() {
        return new CustomThreadPoolExecutor(1);
    }

    @Bean
    public PasswordService passwordService() {
        return new BcryptService();
    }

    @Bean
    public TokenService tokenService() {
        return new JwtService();
    }

    @Bean
    public DateService dateService() {
        return new KrDateService();
    }
}
