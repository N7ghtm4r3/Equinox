Provides shared access to the backend configuration, resources, server protector, and user locale resolver.

## Usage

Configure [`equinox.config`](../Environment.md#additional-backend-setup), then initialize the environment
in `main` before starting Spring Boot:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;

    EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

    environmentService.initEquinoxEnvironment(args);
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService

    val environmentService = EquinoxEnvironmentService.getInstance()

    environmentService.initEquinoxEnvironment(args)
    ```

Initialization creates the configured resource directories and launches the server protector when enabled.
An enabled server protector can be initialized only once. Initialization failures are wrapped in
`RuntimeException`.

Additional arguments to `initEquinoxEnvironment(args, ...)` format the protector's save message;
see the [configuration example](../Environment.md#format-the-protectors-save-message).

## Available methods

| Method | Description |
|--------|-------------|
| `getInstance()` | Gets the shared environment service |
| `initialize(customService)` | Registers a custom environment service |
| `initEquinoxEnvironment(args, ...)` | Initializes the configured resource directories and server protector |
| `getBackendConfiguration()` | Gets the backend configuration |
| `getResourcesProvider()` | Gets the [resources provider](ResourcesProvider.md), or `null` before initialization or when disabled |
| `getServerProtector()` | Gets the server protector, or `null` before initialization or when disabled |
| `getUserLocaleResolver()` | Gets the retained [user locale resolver](EquinoxUserLocaleResolver.md) |
| `generateIdentifier()` | Generates a random UUID as a 32-character string without hyphens |

Using the initialized `environmentService` from the example above:

=== "Java"

    ```java
    EquinoxBackendConfiguration configuration = environmentService.getBackendConfiguration();
    ResourcesProvider resourcesProvider = environmentService.getResourcesProvider();
    ServerProtector serverProtector = environmentService.getServerProtector();
    EquinoxUserLocaleResolver<?> localeResolver = environmentService.getUserLocaleResolver();
    String identifier = environmentService.generateIdentifier();
    ```

=== "Kotlin"

    ```kotlin
    val configuration = environmentService.getBackendConfiguration()
    val resourcesProvider = environmentService.getResourcesProvider()
    val serverProtector = environmentService.getServerProtector()
    val localeResolver = environmentService.getUserLocaleResolver()
    val identifier = environmentService.generateIdentifier()
    ```

## Custom service

Register your implementation with `EquinoxEnvironmentService.initialize(customService)` before the first
`getInstance()` call. Later calls return that registered instance. Without registration, first access creates
the default service.

Registration rejects `null`, and an existing shared service cannot be replaced. See the
[custom environment service example](EquinoxUserLocaleResolver.md#custom-environment-service) to provide
your own locale resolver.
