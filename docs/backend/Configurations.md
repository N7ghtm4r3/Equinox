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

This configuration is used to configure the messages resources bundle to internationalize your backend application

=== "Java"

    ```java
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
     *
     * @since 1.0.9
     *
     * @see WebMvcConfigurer
     */
    @Configuration
    public class MessageSourceConfig implements WebMvcConfigurer {
    
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
            messageSource.setUseCodeAsDefaultMessage(true); // Set false to prevent exceptions from being thrown when resources are not found
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
            resolver.setDefaultLocale(ENGLISH); // customize as needed
            return resolver;
        }
    
        /**
         * Method used to add a custom interceptor to detect the locale change by the {@code "language"} key
         *
         * @return the interceptor as {@link LocaleChangeInterceptor}
         */
        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
            interceptor.setParamName("language"); // customize as needed
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
    ```

=== "Kotlin"

    ```kotlin
    /**
     * The `MessageSourceConfig` class configures the message resources bundle
     * used by the backend application.
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @since 1.0.9
     *
     * @see WebMvcConfigurer
     */
    @Configuration
    class MessageSourceConfig : WebMvcConfigurer {
    
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
        fun messageSource(): ResourceBundleMessageSource {
            val messageSource = ResourceBundleMessageSource()
            messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY) // customize as needed
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name())
            messageSource.setDefaultLocale(Locale.ENGLISH) // customize as needed
            messageSource.setUseCodeAsDefaultMessage(true) // Set false to prevent exceptions from being thrown when resources are not found
            return messageSource
        }
    
        /**
         * Method used to resolve the current locale of the current session
         *
         * @return the resolver as [LocaleResolver]
         */
        @Bean
        fun localeResolver(): LocaleResolver {
            val resolver = SessionLocaleResolver()
            resolver.setDefaultLocale(Locale.ENGLISH) // customize as needed
            return resolver
        }
    
        /**
         * Method used to add a custom interceptor to detect the locale change by the `language` key
         *
         * @return the interceptor as [LocaleChangeInterceptor]
         */
        @Bean
        fun localeChangeInterceptor(): LocaleChangeInterceptor {
            val interceptor = LocaleChangeInterceptor()
            interceptor.paramName = "language" // customize as needed
            return interceptor
        }
    
        /**
         * {@inheritDoc}
         */
        override fun addInterceptors(registry: InterceptorRegistry) {
            registry.addInterceptor(localeChangeInterceptor())
        }
    
    }
    ```