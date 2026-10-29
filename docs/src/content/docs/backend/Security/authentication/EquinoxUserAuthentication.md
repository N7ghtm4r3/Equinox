---
title: "EquinoxUserAuthentication"
slug: "backend/Security/authentication/EquinoxUserAuthentication"
---

<span id="equinoxuserauthentication"></span>

Represents an `EquinoxUser` as a Spring Security authentication principal, preserving its concrete
user type through `EquinoxUserAuthentication<U>`. The caller verifies the user before creating
an authenticated token

It belongs to the `com.tecknobit.equinoxbackend.environment.security.authentication` package

## Behavior

| Constructor or method | Result |
| --- | --- |
| `EquinoxUserAuthentication(user)` | Creates an authenticated token without authorities |
| `EquinoxUserAuthentication(user, authorities)` | Creates an authenticated token with the supplied authorities |
| `getPrincipal()` | Returns the supplied user |
| `getCredentials()` | Returns `null` |

Both constructors mark the token as authenticated. Passing empty or `null` authorities to the second
constructor creates a token without authorities, like the single-argument constructor
Authentication state and granted permissions are separate: an authenticated user does not automatically
receive a role

The token does not check passwords, validate incoming tokens, load users, or install itself in
the security context

## Usage

After your authentication flow has verified the user, create the authenticated token and place it
in the security context. These examples assume `verifiedUser` is an `EquinoxUser` or a subclass
that your application has already verified. The user receives no granted authorities in this example


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication
import org.springframework.security.core.context.SecurityContextHolder

val authentication = EquinoxUserAuthentication(verifiedUser, emptyList())
val context = SecurityContextHolder.createEmptyContext()
context.authentication = authentication
SecurityContextHolder.setContext(context)
```

</div>

</div>

The current security context now exposes the verified user through `authentication.getPrincipal()`
The example does not configure persistence between requests

Use the single-argument constructor after verifying the user when no granted authorities are needed
For ownership checks against the authenticated user's identifier, see
[IsAuthorizedUser](/Equinox/backend/Security/authorization/annotations/IsAuthorizedUser/)
