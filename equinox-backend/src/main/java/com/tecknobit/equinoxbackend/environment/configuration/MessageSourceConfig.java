package com.tecknobit.equinoxbackend.environment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.LANGUAGE_KEY;
import static java.util.Locale.ENGLISH;

@Configuration
// TODO: 11/03/2025 TO ADD TO Configurations.md
// TODO: 12/03/2025
//@ComponentScan(value = {"com.tecknobit.equinoxbackend.environment.configuration"})
public class MessageSourceConfig implements WebMvcConfigurer {

    private static final String MESSAGES_KEY = "lang/messages";

    private static final String CUSTOM_MESSAGES_KEY = "lang/custom_messages";

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(ENGLISH);
        messageSource.setCacheSeconds(5);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(ENGLISH);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(LANGUAGE_KEY);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
