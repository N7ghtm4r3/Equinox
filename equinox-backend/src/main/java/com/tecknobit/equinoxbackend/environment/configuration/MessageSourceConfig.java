package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.helpers.CommonKeysKt;
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

/**
 * The {@code MessageSourceConfig} class configures the message resources bundle
 * used by the backend application.
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see WebMvcConfigurer
 * @since 1.0.9
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    /**
     * {@code MESSAGES_KEY} default path where are placed the resources bundle
     */
    private static final String MESSAGES_KEY = "lang/messages";

    /**
     * {@code CUSTOM_MESSAGES_KEY} path where the user can place the custom resources bundle
     */
    private static final String CUSTOM_MESSAGES_KEY = "lang/custom_messages";

    /**
     * Method used to load and assemble the bundle resources of the international messages resources
     *
     * @return the bundle as {@link ResourceBundleMessageSource}
     */
    @Bean
    @Assembler
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(ENGLISH);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * Method used to resolve the current locale of the current session
     *
     * @return the resolver as {@link LocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(ENGLISH);
        return resolver;
    }

    /**
     * Method used to add a custom interceptor to detect the locale change by the {@link CommonKeysKt#LANGUAGE_KEY} key
     *
     * @return the interceptor as {@link LocaleChangeInterceptor}
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(LANGUAGE_KEY);
        return interceptor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
