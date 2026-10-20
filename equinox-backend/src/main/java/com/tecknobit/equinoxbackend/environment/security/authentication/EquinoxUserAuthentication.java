package com.tecknobit.equinoxbackend.environment.security.authentication;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - Review behavior of the class, like getCredentials etc, so looking for best implementation following guidelines
                - To docu about the security folder and subfolders and how to use them
                """
)
public class EquinoxUserAuthentication<U extends EquinoxUser> extends AbstractAuthenticationToken {

    protected final U user;

    public EquinoxUserAuthentication(U user) {
        super(List.of());
        this.user = user;
    }

    public EquinoxUserAuthentication(U user, @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public @Nullable String getCredentials() {
        return null;
    }

    @Override
    public @Nullable U getPrincipal() {
        return user;
    }

}
