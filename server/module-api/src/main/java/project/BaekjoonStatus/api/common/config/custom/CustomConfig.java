package project.BaekjoonStatus.api.common.config.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.api.common.utils.PasswordBcryptor;
import project.BaekjoonStatus.api.concurrent.CustomThreadPoolExecutor;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;

@Configuration
public class CustomConfig {
    @Bean
    public CustomThreadPoolExecutor customThreadPoolExecutor() {
        return new CustomThreadPoolExecutor(1);
    }

    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new PasswordBcryptor();
    }
}
