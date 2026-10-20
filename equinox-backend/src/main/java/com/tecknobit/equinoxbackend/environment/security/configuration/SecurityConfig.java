package com.tecknobit.equinoxbackend.environment.security.configuration;

import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.SIGN_IN_ENDPOINT;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.SIGN_UP_ENDPOINT;
import static com.tecknobit.laniakea.core.helpers.LaniakeaEndpoints.USERS_CUSTOMERS_SIGN_UP_ENDPOINT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import com.tecknobit.laniakea.security.filters.EquinoxTokenAuthenticationFilter;
import com.tecknobit.laniakea.services.users.repository.LaniakeaUsersRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - Must be generic to accept customized users and related repos
                - Provide a default securityFilterChain but overridable for custom logic
                - The SecurityConfig file must be inserted inside docu in Configurations section
                - Take a look on its implementation avoiding inheritance and using Default config + override con bean
                """
)
public class SecurityConfig {

    protected final List<String> DEFAULT_PERMITTED_PATTERNS = List.of(
            BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT,
            BASE_EQUINOX_ENDPOINT + SIGN_IN_ENDPOINT
    );

    protected final List<String> DEFAULT_PERMITTED_DOCU_PATTERNS = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    );

    protected final List<String> DEFAULT_ALL_PERMITTED_PATTERNS = List.of(
            BASE_EQUINOX_ENDPOINT + SIGN_UP_ENDPOINT,
            BASE_EQUINOX_ENDPOINT + SIGN_IN_ENDPOINT,
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    );

    @Value("${springdoc.api-docs.enabled:false}")
    protected boolean apiDocsEnabled;

    @Value("${springdoc.swagger-ui.enabled:false}")
    protected boolean swaggerEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   EquinoxTokenAuthenticationFilter tokenAuthenticationFilter) {
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

        return http.build();
    }

    @FutureEquinoxApi(
            additionalNotes = """
                    - To docu how to customize patterns
                    - To remove custom CUSTOMER_SIGN_UP endpoint
                    - To warn about omitting docu in production environment
                    - To docu about default docu is not permitted
                    """
    )
    @Wrapper
    protected String[] getPermittedPatterns() {
        ArrayList<String> permittedPatterns = new ArrayList<>(DEFAULT_PERMITTED_PATTERNS);
        if (apiDocsEnabled && swaggerEnabled)
            permittedPatterns.addAll(DEFAULT_PERMITTED_DOCU_PATTERNS);

        permittedPatterns.add(BASE_EQUINOX_ENDPOINT + USERS_CUSTOMERS_SIGN_UP_ENDPOINT);

        EquinoxBackendConfiguration backendConfiguration = EquinoxBackendConfiguration.getInstance();
        EquinoxBackendConfiguration.ResourcesConfig config = backendConfiguration.getResourcesConfig();
        for (String directory : config.getSubdirectories())
            permittedPatterns.add("/" + directory + "/**");

        return permittedPatterns.toArray(new String[0]);
    }

    // TODO: 23/04/2026 TO DOCU
    @Returner
    protected AccessDeniedHandler useAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            // TODO: 23/04/2026 TO USE EquinoxControllerCore.notAuthorizedOrWrongDetailsResponse() METHOD INSTEAD
            response.getWriter().write("{\"response\":\"Not authorized or wrong details\",\"status\":\"FAILED\"}");
        };
    }

    @Bean
    // TODO: - Take a look about @ConditionalOnMissingBean(EquinoxTokenAuthenticationFilter.class) behavior
    public EquinoxTokenAuthenticationFilter tokenAuthenticationFilter(LaniakeaUsersRepository repository) {
        return new EquinoxTokenAuthenticationFilter(repository);
    }

}