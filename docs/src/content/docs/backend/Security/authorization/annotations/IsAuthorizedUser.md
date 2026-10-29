---
title: "IsAuthorizedUser"
slug: "backend/Security/authorization/annotations/IsAuthorizedUser"
---

The `IsAuthorizedUser` annotation restricts access to methods whose `userId` argument
matches the authenticated principal's `id`. It can be applied to a method or a type

It belongs to the `com.tecknobit.equinoxbackend.environment.security.authorization.annotations` package

## Usage

Enable Spring Security method authorization in your application. The `userId` argument
must be resolvable by name, and the authenticated principal must expose an `id` property


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.security.authorization.annotations.IsAuthorizedUser;

@IsAuthorizedUser
public void updateUser(String userId) {
    // update the requested user
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.security.authorization.annotations.IsAuthorizedUser

@IsAuthorizedUser
fun updateUser(userId: String) {
    // update the requested user
}
```

</div>

</div>

When applied to a type, the same authorization expression applies to its secured methods
