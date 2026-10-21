In this page you can find different configuration components ready to be implemented in your backend

## ResourcesConfig

This configuration class allows the backend to serve static resources

=== "Java"

    ```java
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    import org.springframework.web.servlet.resource.PathResourceResolver;
    
    /**
     * The {@code ResourceConfigs} class is useful to set the configuration of the resources to correctly serve the
     * images by the server
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see WebMvcConfigurer
     */
    @Configuration
    public class ResourcesConfig implements WebMvcConfigurer {
    
        /**
         * Add handlers to serve static resources such as images, js, and, css
         * files from specific locations under web application root, the classpath,
         * and others.
         *
         * @see ResourceHandlerRegistry
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("file:<list>") // replace <list> with the folders the backend will use
                    .setCachePeriod(0)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import org.springframework.context.annotation.Configuration
    import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
    import org.springframework.web.servlet.resource.PathResourceResolver
    
    /**
     * The `ResourceConfigs` class is useful to set the configuration of the resources to correctly serve the
     * images by the server
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see WebMvcConfigurer
     */
    @Configuration
    class ResourcesConfig : WebMvcConfigurer {
    
        /**
         * Add handlers to serve static resources such as images, js, and, css
         * files from specific locations under web application root, the classpath,
         * and others.
         *
         * @see ResourceHandlerRegistry
         */
        override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
            registry.addResourceHandler("/**")
                .addResourceLocations("file:<list>") // replace <list> with the folders the backend will use
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(PathResourceResolver())
        }
    
    }
    ```

## CORS

This configuration class allows to set the `CORS` origin policy for the backend

=== "Java"

    ```java
    import org.springframework.boot.web.servlet.FilterRegistrationBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.filter.CorsFilter;
    
    /**
     * The {@code CORSAdvice} class is useful to set the CORS policy
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    @Configuration
    public class CORSAdvice {
    
        /**
         * Method used to set the CORS filter 
         */
        @Bean
        @SuppressWarnings({"rawtypes"})
        public FilterRegistrationBean corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(false); // set as you need
            config.addAllowedOrigin("*"); // set as you need
            config.addAllowedHeader("*"); // set as you need
            config.addAllowedMethod("*"); // set as you need
            source.registerCorsConfiguration("/**", config); // set as you need
            FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
            bean.setOrder(0);
            return bean;
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import org.springframework.boot.web.servlet.FilterRegistrationBean
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.web.cors.CorsConfiguration
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource
    import org.springframework.web.filter.CorsFilter
    
    /**
     * The `CORSAdvice` class is useful to set the CORS policy
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    @Configuration
    class CORSAdvice {
    
        /**
         * Method used to set the CORS filter
         */
        @Bean
        @SuppressWarnings(["rawtypes"])
        fun corsFilter(): FilterRegistrationBean<*> {
            val source = UrlBasedCorsConfigurationSource()
            val config = CorsConfiguration()
            config.allowCredentials = false // set as you need
            config.addAllowedOrigin("*") // set as you need
            config.addAllowedHeader("*") // set as you need
            config.addAllowedMethod("*") // set as you need
            source.registerCorsConfiguration("/**", config) // set as you need
            val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
            bean.order = 0
            return bean
        }
    
    }
    ```

## MessageSource

This configuration provides the message resource bundles used to internationalize your backend application.
It loads the configured bundles with UTF-8 encoding and uses English as the default message source locale.

Request locale selection is configured separately by [EquinoxLocaleConfig](#equinoxlocaleconfig).
Use both configurations together: `MessageSourceConfig` provides the `messageSource` bean, while
`EquinoxLocaleConfig` provides the `localeResolver` bean.

=== "Java"

    ```java
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
        private static final String MESSAGES_KEY = "lang/messages"; // customize as needed
    
        /**
         * {@code CUSTOM_MESSAGES_KEY} path where the user can place the custom resources bundle
         */
        private static final String CUSTOM_MESSAGES_KEY = "lang/custom_messages"; // customize as needed
    
        /**
         * Method used to load and assemble the bundle resources of the international messages resources
         *
         * @return the bundle as {@link ResourceBundleMessageSource}
         */
        @Bean
        @Assembler
        public ResourceBundleMessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY); // customize as needed
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
            messageSource.setDefaultLocale(ENGLISH); // customize as needed
            messageSource.setUseCodeAsDefaultMessage(true); // Use the message code when no translation is available
            return messageSource;
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxcore.annotations.Assembler
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.context.support.ResourceBundleMessageSource
    import java.nio.charset.StandardCharsets
    import java.util.Locale

    /**
     * The `MessageSourceConfig` class configures the message resources bundle
     * used by the backend application.
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @since 2.0.0
     */
    @Configuration
    open class MessageSourceConfig {
    
        companion object {
    
            /**
             * `MESSAGES_KEY` default path where are placed the resources bundle
             */
            private const val MESSAGES_KEY = "lang/messages"
    
            /**
             * `CUSTOM_MESSAGES_KEY` path where the user can place the custom resources bundle
             */
            private const val CUSTOM_MESSAGES_KEY = "lang/custom_messages"
    
        }
    
        /**
         * Method used to load and assemble the bundle resources of the international messages resources
         *
         * @return the bundle as [ResourceBundleMessageSource]
         */
        @Bean
        @Assembler
        open fun messageSource(): ResourceBundleMessageSource {
            val messageSource = ResourceBundleMessageSource()
            messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY) // customize as needed
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name())
            messageSource.setDefaultLocale(Locale.ENGLISH) // customize as needed
            messageSource.setUseCodeAsDefaultMessage(true) // Use the message code when no translation is available
            return messageSource
        }
    
    }
    ```

## EquinoxLocaleConfig

This configuration exposes the `localeResolver` bean used to resolve request locales from the authenticated
user's language. It retrieves the resolver from the shared
[environment service](Environment.md#environment-service), so a custom service can supply its own
[EquinoxUserLocaleResolver](APIs/EquinoxUserLocaleResolver.md).

The default environment service creates its resolver once and returns the same instance on subsequent
calls. Register a custom environment service before the first call to `getInstance()` and before Spring
creates the `localeResolver` bean.

Use this configuration alongside [MessageSource](#messagesource): the resolver selects the request locale,
and the message source uses that locale to look up translations in the configured bundles.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.LocaleResolver;

    @Configuration(proxyBeanMethods = false)
    public final class EquinoxLocaleConfig {

        @Bean
        public LocaleResolver localeResolver() {
            EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
            return environmentService.getUserLocaleResolver();
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.web.servlet.LocaleResolver

    @Configuration(proxyBeanMethods = false)
    class EquinoxLocaleConfig {

        @Bean
        fun localeResolver(): LocaleResolver {
            val environmentService = EquinoxEnvironmentService.getInstance()
            return environmentService.getUserLocaleResolver()
        }

    }
    ```

For the default resolution rules and a custom resolver example, see
[EquinoxUserLocaleResolver](APIs/EquinoxUserLocaleResolver.md).
