package com.tecknobit.equinoxbackend.environment.security.filters;

import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxcore.annotations.Returner;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.TOKEN_KEY;

/**
 * The {@code EquinoxTokenAuthenticationFilter} class is useful to load users from the token request header
 * and expose them in the current security context
 *
 * <p>Requests with a missing, blank, or unknown token continue without this filter changing the
 * security context. Tokens belonging to soft deleted users also leave the context unchanged
 *
 * <p>Requests whose URI starts with a configured resource directory prefix are skipped
 *
 * <p>Override {@link #shouldNotFilter(HttpServletRequest)} to customize exclusions and
 * {@link #getUserAuthentication(EquinoxUser)} to customize the authentication representation.
 * The default representation is authenticated without granted authorities
 *
 * @param <U> The user type retrieved from the repository
 * @param <R> The repository type used to retrieve users by token
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see OncePerRequestFilter
 *
 * @since 2.0.0
 */
public class EquinoxTokenAuthenticationFilter<U extends EquinoxUser, R extends EquinoxUsersRepository<U>> extends OncePerRequestFilter {

    /**
     * {@code usersRepository} the repository used to retrieve users matching the request token
     */
    protected final R usersRepository;

    /**
     * {@code excludedPaths} the resource directory names whose URI prefixes are excluded from token processing
     */
    protected final HashSet<String> excludedPaths;

    /**
     * Constructor to init the {@link EquinoxTokenAuthenticationFilter} class
     *
     * <p>Copies the resource subdirectories from the shared environment configuration at construction.
     * Initialize the Equinox environment before creating the filter
     *
     * @param usersRepository The repository used to retrieve users by token
     */
    public EquinoxTokenAuthenticationFilter(R usersRepository) {
        this.usersRepository = usersRepository;
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

        excludedPaths = environmentService.useResourcesConfig(config -> new HashSet<>(config.getSubdirectories()));
    }

    /**
     * Method used to check whether the request URI starts with an excluded resource directory prefix
     *
     * <p>Each configured directory is prefixed with {@code /}. Matching uses a string prefix without
     * requiring a path segment boundary. Override this method to customize the exclusion policy
     *
     * @param request The request whose URI must be checked
     *
     * @return whether token processing must be skipped as {@code boolean}
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return excludedPaths.stream().anyMatch(directory -> {
            directory = "/" + directory;
            return path.startsWith(directory);
        });
    }

    /**
     * Method used to expose the user matching the token header in the current security context
     *
     * <p>Missing, blank, or unknown tokens and tokens belonging to soft deleted users leave the context
     * unchanged. For a matching active user, the
     * result of {@link #getUserAuthentication(EquinoxUser)} replaces the context authentication.
     * The filter continues the chain without directly rejecting missing or unknown tokens
     *
     * @param request     The request containing the {@code token} header
     * @param response    The response passed to the remaining filters
     * @param filterChain The remaining request processing chain
     *
     * @throws ServletException if the remaining chain fails with a servlet error
     * @throws IOException if the remaining chain fails with an input or output error
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_KEY);
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        U user = usersRepository.findUserByToken(token).orElse(null);
        if (user == null || user.isSoftDeleted()) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUserAuthentication(user);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * Method used to create the authentication representation for a user retrieved by token
     *
     * <p>The default implementation creates an authenticated token without authorities.
     * Override this method to customize the granted authorities or authentication representation
     *
     * @param user The user retrieved for the supplied token
     *
     * @return the user authentication representation as {@link EquinoxUserAuthentication}
     */
    @Returner
    protected EquinoxUserAuthentication<U> getUserAuthentication(U user) {
        return new EquinoxUserAuthentication<>(user);
    }

}