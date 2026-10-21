package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxcore.annotations.Assembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

import static java.util.Locale.ENGLISH;

/**
 * The {@code MessageSourceConfig} class configures the message resources bundle
 * used by the backend application.
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Configuration
public class MessageSourceConfig {

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

}
