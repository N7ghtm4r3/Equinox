package com.tecknobit.equinoxbackend.environment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * The {@code EquinoxLocaleConfig} class is useful to configure the request locale resolver
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
public final class EquinoxLocaleConfig {

    /**
     * Method used to provide the locale resolver from the shared environment service
     *
     * <p>Register a custom environment service before this bean is created to use its resolver
     *
     * @return the resolver provided by the environment service as {@link LocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

        return environmentService.getUserLocaleResolver();
    }

}