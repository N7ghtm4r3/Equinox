---
title: "EquinoxUserLocaleResolver"
slug: "backend/APIs/EquinoxUserLocaleResolver"
---

Resolves request locales from the language stored on an authenticated `EquinoxUser`.
[EquinoxLocaleConfig](/Equinox/backend/Configurations/#equinoxlocaleconfig) exposes it as the `localeResolver` bean.

## Behavior

- Authenticated `EquinoxUser`: uses `user.getLanguage()`
- No authentication or `isAuthenticated() == false`: uses `request.getLocale()`
- Authenticated principal is `null`: uses `en`

A non-null authenticated principal must be an `EquinoxUser` with a non-null language.
`setLocale()` does not change the user's language.

## Customization

### Custom resolver

Extend `EquinoxUserLocaleResolver<U>` with your user type and override `resolveAuthenticatedUserLanguage()`.
This example falls back to `en` for unsupported principals and missing languages:


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
public class CustomLocaleResolver extends EquinoxUserLocaleResolver<EquinoxUser> {

    @Override
    protected String resolveAuthenticatedUserLanguage(Authentication authentication) {
        if (authentication.getPrincipal() instanceof EquinoxUser user) {
            String language = user.getLanguage();
            if (language != null)
                return language;
        }
        return "en";
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
class CustomLocaleResolver : EquinoxUserLocaleResolver<EquinoxUser>() {

    override fun resolveAuthenticatedUserLanguage(authentication: Authentication): String {
        val user = authentication.principal as? EquinoxUser

        return user?.language ?: "en"
    }

}
```

</div>

</div>

### Custom environment service

`EquinoxLocaleConfig` asks the shared `EquinoxEnvironmentService` for its resolver.
Extend the service and override `getUserLocaleResolver()` to return the `CustomLocaleResolver` defined
above. Keep that resolver in a field so every call returns the same instance.


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
public class CustomEnvironmentService extends EquinoxEnvironmentService {

    private final CustomLocaleResolver resolver = new CustomLocaleResolver();

    @Override
    public CustomLocaleResolver getUserLocaleResolver() {
        return resolver;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
class CustomEnvironmentService : EquinoxEnvironmentService() {

    private val resolver = CustomLocaleResolver()

    override fun getUserLocaleResolver(): CustomLocaleResolver {
        return resolver
    }

}
```

</div>

</div>

## Initialize the environment

In `main`, register your custom service before the first `getInstance()` call and
[Spring Boot startup](/Equinox/backend/Environment/#out-of-the-box-launch).
`initialize(customService)` stores that object as the shared service: subsequent `getInstance()` calls,
including those made by `EquinoxLocaleConfig`, return it.


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
CustomEnvironmentService customService = new CustomEnvironmentService();

EquinoxEnvironmentService.initialize(customService);

EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
environmentService.initEquinoxEnvironment(args);
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
val customService = CustomEnvironmentService()

EquinoxEnvironmentService.initialize(customService)

val environmentService = EquinoxEnvironmentService.getInstance()
environmentService.initEquinoxEnvironment(args)
```

</div>

</div>
