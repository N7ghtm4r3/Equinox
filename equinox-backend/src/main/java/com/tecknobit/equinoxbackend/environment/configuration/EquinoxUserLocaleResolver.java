package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.Returner;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

/**
 * The {@code EquinoxUserLocaleResolver} class is useful to resolve request locales from authenticated users
 *
 * <p>Absent, unauthenticated, or anonymous authentication uses the locale reported by
 * {@link HttpServletRequest#getLocale()}. Other authenticated principals must be compatible
 * with {@link EquinoxUser}. Explicit locale changes are ignored
 *
 * @param <U> The expected authenticated user type
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see LocaleResolver
 *
 * @since 2.0.0
 */
public class EquinoxUserLocaleResolver<U extends EquinoxUser> implements LocaleResolver {

    /**
     * Method used to resolve the locale associated with the current authentication
     *
     * <p>If authentication is absent, unauthenticated, or represented by an
     * {@link AnonymousAuthenticationToken}, the request locale is returned. Otherwise, the locale
     * is created from the language provided by {@link #resolveAuthenticatedUserLanguage(Authentication)}
     *
     * @param request The current HTTP request
     *
     * @return the resolved request locale as {@link Locale}
     * @throws ClassCastException if the default language resolver receives a non-null principal
     *     that is not an {@link EquinoxUser}
     * @throws NullPointerException if the resolved language is {@code null}
     */
    @Override
    public @NonNull Locale resolveLocale(@NonNull HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || isAnonymousRequest(authentication))
            return request.getLocale();

        String language = resolveAuthenticatedUserLanguage(authentication);
        return Locale.forLanguageTag(language);
    }

    /**
     * Method used to check whether the authentication represents an anonymous request
     *
     * @param authentication The authentication to inspect, which can be {@code null}
     *
     * @return whether the authentication is an {@link AnonymousAuthenticationToken} as {@code boolean}
     */
    private boolean isAnonymousRequest(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    /**
     * Method used to get the language of the authenticated user
     *
     * <p>A {@code null} principal uses the default language. Other principals are cast to the user
     * type before their stored language is read
     *
     * @param authentication The non-null authentication containing the user principal
     *
     * @return the user language, or the default language for a null principal, as {@link String}
     * @throws ClassCastException if the principal is not compatible with {@link EquinoxUser}
     * @throws NullPointerException if {@code authentication} is {@code null}
     */
    @Returner
    protected String resolveAuthenticatedUserLanguage(Authentication authentication) {
        U user = (U) authentication.getPrincipal();
        String language = DEFAULT_LANGUAGE;
        if(user != null)
            language = user.getLanguage();

        return language;
    }

    /**
     * Method used to ignore explicit locale changes
     *
     * <p>This implementation does not modify the request, response, or user language
     *
     * @param request  The current HTTP request
     * @param response The HTTP response, which can be {@code null}
     * @param locale   The requested locale, which can be {@code null}
     */
    @Override
    public void setLocale(@NonNull HttpServletRequest request, @Nullable HttpServletResponse response,
                          @Nullable Locale locale) {
    }

}