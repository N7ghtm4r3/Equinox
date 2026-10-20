package com.tecknobit.equinoxbackend.environment.security.filters;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.TOKEN_KEY;

import com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.laniakea.security.authentication.LaniakeaUserAuthentication;
import com.tecknobit.laniakea.services.users.entity.LaniakeaUser;
import com.tecknobit.laniakea.services.users.repository.LaniakeaUsersRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - To docu about shouldNotFilter customization
                - Must be generic to accept customized users and related repos
                - The SecurityConfig file must be inserted inside docu in Configurations section
                - Take a look about @ConditionalOnMissingBean(EquinoxTokenAuthenticationFilter.class) behavior
                """
)
public class EquinoxTokenAuthenticationFilter extends OncePerRequestFilter {

    protected final LaniakeaUsersRepository laniakeaUsersRepository;

    protected final HashSet<String> excludedPaths;

    public EquinoxTokenAuthenticationFilter(LaniakeaUsersRepository laniakeaUsersRepository) {
        this.laniakeaUsersRepository = laniakeaUsersRepository;
        EquinoxBackendConfiguration backendConfiguration = EquinoxBackendConfiguration.getInstance();
        EquinoxBackendConfiguration.ResourcesConfig config = backendConfiguration.getResourcesConfig();

        excludedPaths = new HashSet<>(config.getSubdirectories());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return excludedPaths.stream().anyMatch(directory -> {
            directory = "/" + directory;
            return path.startsWith(directory);
        });
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_KEY);
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        LaniakeaUser user = laniakeaUsersRepository.findByToken(token).orElse(null);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = new LaniakeaUserAuthentication(user);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}