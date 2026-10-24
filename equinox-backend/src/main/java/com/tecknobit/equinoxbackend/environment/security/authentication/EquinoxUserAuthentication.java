package com.tecknobit.equinoxbackend.environment.security.authentication;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * The {@code EquinoxUserAuthentication} class is useful to represent an Equinox user as an authentication principal
 *
 * <p>Both constructors create authenticated tokens and must be used only after the caller has
 * verified the user. The user-only constructor assigns no authorities. This class does not validate
 * credentials or register itself in a security context
 *
 * @param <U> The user type exposed as the authentication principal
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see AbstractAuthenticationToken
 *
 * @since 2.0.0
 */
public class EquinoxUserAuthentication<U extends EquinoxUser> extends AbstractAuthenticationToken {

    /**
     * {@code user} the user exposed as the authentication principal
     */
    protected final U user;

    /**
     * Constructor to init the {@link EquinoxUserAuthentication} class with an authenticated user without authorities
     *
     * <p>The caller must verify the user before invoking this constructor. The token is marked
     * authenticated without granting authorities
     *
     * @param user The previously verified user to expose as the authentication principal
     */
    public EquinoxUserAuthentication(U user) {
        super(List.of());
        this.user = user;
        setAuthenticated(true);
    }

    /**
     * Constructor to init the {@link EquinoxUserAuthentication} class with an authenticated user
     *
     * <p>The caller must verify the user before invoking this constructor. The token is marked
     * authenticated even when the supplied authorities are empty or {@code null}
     *
     * @param user        The previously verified user to expose as the authentication principal
     * @param authorities The granted authorities, or {@code null} for no authorities
     */
    public EquinoxUserAuthentication(U user, @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }

    /**
     * Method used to return no credentials for this authentication token
     *
     * @return {@code null}, because this token does not expose credentials
     */
    @Override
    public @Nullable String getCredentials() {
        return null;
    }

    /**
     * Method used to get the user associated with this authentication token
     *
     * @return the supplied user as {@code U}, or {@code null} if constructed with a null user
     */
    @Override
    public @Nullable U getPrincipal() {
        return user;
    }

}
