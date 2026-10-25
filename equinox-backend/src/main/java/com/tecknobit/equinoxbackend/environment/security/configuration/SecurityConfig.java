package com.tecknobit.equinoxbackend.environment.security.configuration;

import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxForbiddenException;
import com.tecknobit.equinoxbackend.environment.security.filters.EquinoxTokenAuthenticationFilter;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.Returner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * The {@code SecurityConfig} class is useful to provide default backend security beans
 *
 * <p>The filter chain and token filter are registered independently when no bean of the corresponding
 * type has been declared by the application. Register this auto-configuration through Spring Boot's
 * auto-configuration imports rather than component scanning
 *
 * <p>Declare custom beans to replace the defaults, or override the protected methods in a subclass
 * to customize the chain baseline, permitted patterns, and access denied response
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * {@code DEFAULT_PERMITTED_PATTERNS} the sign-up and sign-in request patterns permitted without authentication
     */
    protected final List<String> DEFAULT_PERMITTED_PATTERNS = List.of(
            BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT,
            BASE_EQUINOX_ENDPOINT + SIGN_IN_ENDPOINT
    );

    /**
     * {@code DEFAULT_PERMITTED_DOCU_PATTERNS} the Swagger UI and OpenAPI request patterns permitted
     * when both documentation flags are enabled
     */
    protected final List<String> DEFAULT_PERMITTED_DOCU_PATTERNS = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    );

    /**
     * {@code DEFAULT_ALL_PERMITTED_PATTERNS} the combined sign-up, sign-in, and documentation request
     * patterns, not used by the default pattern resolution
     */
    protected final List<String> DEFAULT_ALL_PERMITTED_PATTERNS = List.of(
            BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT,
            BASE_EQUINOX_ENDPOINT + SIGN_IN_ENDPOINT,
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    );

    /**
     * {@code apiDocsEnabled} whether OpenAPI documentation paths may be permitted, defaulting to {@code false}
     */
    @Value("${springdoc.api-docs.enabled:false}")
    protected boolean apiDocsEnabled;

    /**
     * {@code swaggerEnabled} whether Swagger UI paths may be permitted, defaulting to {@code false}
     */
    @Value("${springdoc.swagger-ui.enabled:false}")
    protected boolean swaggerEnabled;

    /**
     * {@code exceptionResolver} the MVC bean named {@code handlerExceptionResolver} used to delegate
     * access denied failures to the registered exception handlers
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    protected HandlerExceptionResolver exceptionResolver;

    /**
     * {@code localeResolver} the bean named {@code localeResolver} used to resolve the access denied
     * response locale before delegating the failure to MVC
     */
    @Autowired
    @Qualifier("localeResolver")
    protected LocaleResolver localeResolver;

    /**
     * Method used to provide the default security filter chain when the application has not declared one
     *
     * <p>Uses the baseline returned by {@link #assembleFilterChainBaseline(HttpSecurity, EquinoxTokenAuthenticationFilter)}
     *
     * @param http                      The security builder to configure
     * @param tokenAuthenticationFilter The default or application-provided token filter
     *
     * @return the configured security filter chain as {@link SecurityFilterChain}
     */
    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   EquinoxTokenAuthenticationFilter<?, ?> tokenAuthenticationFilter) {
        HttpSecurity httpSecurity = assembleFilterChainBaseline(http, tokenAuthenticationFilter);
        return httpSecurity.build();
    }

    /**
     * Method used to assemble the default stateless security configuration before building the chain
     *
     * <p>Disables CSRF and form login, permits the resolved request patterns, and requires authentication
     * for every other request. The token filter runs before {@link AnonymousAuthenticationFilter}
     *
     * <p>Override this method to customize the baseline. Calling the superclass implementation retains
     * its configuration and returns the same builder for further customization
     *
     * @param http                      The security builder to configure
     * @param tokenAuthenticationFilter The token filter to include in the chain
     *
     * @return the configured builder as {@link HttpSecurity}
     */
    @Assembler
    protected HttpSecurity assembleFilterChainBaseline(HttpSecurity http,
                                                       EquinoxTokenAuthenticationFilter<?, ?> tokenAuthenticationFilter) {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(getPermittedPatterns()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(useAccessDeniedHandler())
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http;
    }

    /**
     * Method used to get the request patterns permitted without authentication
     *
     * <p>Combines {@link #resolvePermittedPatterns()} with the configured resource subdirectories,
     * matching each resource directory and its descendants through {@code /directory/**}
     *
     * <p>Override {@link #resolvePermittedPatterns()} to customize the base patterns while retaining
     * the resource patterns, or override this method to replace the complete result
     *
     * @return the permitted request patterns as {@code String[]}
     */
    protected String[] getPermittedPatterns() {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
        ArrayList<String> permittedPatterns = resolvePermittedPatterns();

        return environmentService.useResourcesConfig(config -> {
            for (String directory : config.getSubdirectories())
                permittedPatterns.add("/" + directory + "/**");

            return permittedPatterns.toArray(new String[0]);
        });
    }

    /**
     * Method used to resolve the base request patterns permitted without authentication
     *
     * <p>Includes sign-up and sign-in paths. Documentation paths are added only when both
     * {@code springdoc.api-docs.enabled} and {@code springdoc.swagger-ui.enabled} are {@code true}
     *
     * <p>Override this method to customize these patterns before resource paths are added.
     * Documentation paths are not permitted by default and should remain restricted in production
     *
     * @return a mutable list of permitted base patterns as {@link ArrayList} of {@link String}
     */
    @Returner
    protected ArrayList<String> resolvePermittedPatterns() {
        ArrayList<String> permittedPatterns = new ArrayList<>(DEFAULT_PERMITTED_PATTERNS);
        if (apiDocsEnabled && swaggerEnabled)
            permittedPatterns.addAll(DEFAULT_PERMITTED_DOCU_PATTERNS);

        return permittedPatterns;
    }

    /**
     * Method used to create the handler for localized access denied responses
     *
     * <p>Uses {@link #localeResolver} to resolve the request locale explicitly, including when
     * access is denied before MVC establishes its locale context. Delegates an
     * {@link EquinoxForbiddenException} carrying that locale to {@link #exceptionResolver}
     *
     * <p>Register
     * {@link com.tecknobit.equinoxbackend.environment.exceptions.EquinoxGlobalExceptionHandler}
     * in the application context to produce the localized HTTP {@code 403} failure response.
     * Override this method to customize the handler
     *
     * @return the access denied handler as {@link AccessDeniedHandler}
     */
    @Returner
    protected AccessDeniedHandler useAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            Locale requestLocale = localeResolver.resolveLocale(request);

            exceptionResolver.resolveException(request, response, null, new EquinoxForbiddenException(requestLocale));
        };
    }

    /**
     * Method used to provide the default token filter when the application has not declared a compatible bean
     *
     * <p>A bean of a subclass also replaces this default. This condition is independent of the
     * condition on {@link #securityFilterChain(HttpSecurity, EquinoxTokenAuthenticationFilter)}
     *
     * @param repository The repository used to retrieve users by token
     *
     * @return the default token filter as {@link EquinoxTokenAuthenticationFilter}
     */
    @Bean
    @ConditionalOnMissingBean(EquinoxTokenAuthenticationFilter.class)
    public EquinoxTokenAuthenticationFilter<?, ?> tokenAuthenticationFilter(EquinoxUsersRepository<?> repository) {
        return new EquinoxTokenAuthenticationFilter<>(repository);
    }

}