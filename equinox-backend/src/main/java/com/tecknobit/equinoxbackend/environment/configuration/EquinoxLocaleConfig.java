package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                -  Add related docu page (also in Configurations like MessageSourceConfig)
                """
)
public class EquinoxLocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

        return environmentService.getUserLocaleResolver();
    }

}