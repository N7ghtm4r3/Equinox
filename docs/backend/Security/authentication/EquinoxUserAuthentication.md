# EquinoxUserAuthentication

Represents an `EquinoxUser` as a Spring Security authentication principal, preserving its concrete
user type through `EquinoxUserAuthentication<U>`. The caller verifies the user before creating
an authenticated token

It belongs to the `com.tecknobit.equinoxbackend.environment.security.authentication` package

## Behavior

| Constructor or method | Result |
| --- | --- |
| `EquinoxUserAuthentication(user)` | Creates an unauthenticated token without authorities |
| `EquinoxUserAuthentication(user, authorities)` | Creates an authenticated token with the supplied authorities |
| `getPrincipal()` | Returns the supplied user |
| `getCredentials()` | Returns `null` |

Passing empty or `null` authorities to the second constructor still creates an authenticated token
without authorities. Authentication state and granted permissions are separate: an authenticated
user does not automatically receive a role

The token does not check passwords, validate incoming tokens, load users, or install itself in
the security context

## Usage

After your authentication flow has verified the user, create the authenticated token and place it
in the security context. These examples assume `verifiedUser` is an `EquinoxUser` or a subclass
that your application has already verified. The user receives no granted authorities in this example

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication;
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import org.springframework.security.core.context.SecurityContext;
    import org.springframework.security.core.context.SecurityContextHolder;

    import java.util.List;

    EquinoxUserAuthentication<EquinoxUser> authentication =
            new EquinoxUserAuthentication<>(verifiedUser, List.of());
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication
    import org.springframework.security.core.context.SecurityContextHolder

    val authentication = EquinoxUserAuthentication(verifiedUser, emptyList())
    val context = SecurityContextHolder.createEmptyContext()
    context.authentication = authentication
    SecurityContextHolder.setContext(context)
    ```

The current security context now exposes the verified user through `authentication.getPrincipal()`
The example does not configure persistence between requests

Use the single-argument constructor when you need an unauthenticated representation instead
For ownership checks against the authenticated user's identifier, see
[IsAuthorizedUser](../authorization/annotations/IsAuthorizedUser.md)
