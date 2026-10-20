package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Returner;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

@Configuration
@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - Check whether make more general purpose with generics for custom EquinoxUser and related Authentication
                - Add related docu page (also in Configurations like MessageSourceConfig)
                """
)
public class EquinoxUserLocaleResolver<U extends EquinoxUser> implements LocaleResolver {

    @Override
    public @NonNull Locale resolveLocale(@NonNull HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return LocaleContextHolder.getLocale();

        String language = resolveAuthenticatedUserLanguage(authentication);
        return Locale.forLanguageTag(language);
    }

    @Returner
    @SuppressWarnings("unchecked")
    protected String resolveAuthenticatedUserLanguage(Authentication authentication) {
        U user = (U) authentication.getPrincipal();
        String language = DEFAULT_LANGUAGE;
        if(user != null)
            language = user.getLanguage();

        return language;
    }

    @Override
    public void setLocale(@NonNull HttpServletRequest request, @Nullable HttpServletResponse response,
                          @Nullable Locale locale) {
    }

}