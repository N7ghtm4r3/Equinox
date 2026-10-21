Resolves request locales from the language stored on an authenticated `EquinoxUser`.
[EquinoxLocaleConfig](../Configurations.md#equinoxlocaleconfig) exposes it as the `localeResolver` bean.

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

=== "Java"

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

=== "Kotlin"

    ```kotlin
    class CustomLocaleResolver : EquinoxUserLocaleResolver<EquinoxUser>() {

        override fun resolveAuthenticatedUserLanguage(authentication: Authentication): String {
            val user = authentication.principal as? EquinoxUser

            return user?.language ?: "en"
        }

    }
    ```

### Custom environment service

`EquinoxLocaleConfig` asks the shared `EquinoxEnvironmentService` for its resolver.
Extend the service and override `getUserLocaleResolver()` to return the `CustomLocaleResolver` defined
above. Keep that resolver in a field so every call returns the same instance.

=== "Java"

    ```java
    public class CustomEnvironmentService extends EquinoxEnvironmentService {

        private final CustomLocaleResolver resolver = new CustomLocaleResolver();

        @Override
        public CustomLocaleResolver getUserLocaleResolver() {
            return resolver;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    class CustomEnvironmentService : EquinoxEnvironmentService() {

        private val resolver = CustomLocaleResolver()

        override fun getUserLocaleResolver(): CustomLocaleResolver {
            return resolver
        }

    }
    ```

## Initialize the environment

In `main`, register your custom service before the first `getInstance()` call and
[Spring Boot startup](../Environment.md#out-of-the-box-launch).
`initialize(customService)` stores that object as the shared service: subsequent `getInstance()` calls,
including those made by `EquinoxLocaleConfig`, return it.

=== "Java"

    ```java
    CustomEnvironmentService customService = new CustomEnvironmentService();

    EquinoxEnvironmentService.initialize(customService);

    EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
    environmentService.initEquinoxEnvironment(args);
    ```

=== "Kotlin"

    ```kotlin
    val customService = CustomEnvironmentService()

    EquinoxEnvironmentService.initialize(customService)

    val environmentService = EquinoxEnvironmentService.getInstance()
    environmentService.initEquinoxEnvironment(args)
    ```
