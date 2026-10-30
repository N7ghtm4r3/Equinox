Provides shared access to the backend configuration, resources, [server protector](ServerProtector.md), and user locale resolver.

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

When protection has no stored secret, the protector generates one and raises
[SaveData](../Exceptions/SaveData.md), which this service wraps in `RuntimeException`. Save the exposed
value and restart before serving requests. See the [first-launch flow](ServerProtector.md#first-launch).

## Available methods

| Method | Description |
|--------|-------------|
| `getInstance()` | Gets the shared environment service |
| `initialize(customService)` | Registers a custom environment service |
| `initEquinoxEnvironment(args, ...)` | Initializes the configured resource directories and server protector |
| `getBackendConfiguration()` | Gets the backend configuration |
| `useResourcesConfig(usage)` | Applies a callback to the resources configuration and returns its result |
| `useServerProtectorConfig(usage)` | Applies a callback to the server protector configuration and returns its result |
| `useDefaultsConfig(usage)` | Applies a callback to the defaults configuration and returns its result |
| `getResourcesProvider()` | Gets the [resources provider](ResourcesProvider.md), or `null` before initialization or when disabled |
| `getServerProtector()` | Gets the [server protector](ServerProtector.md), or `null` before initialization or when disabled |
| `getUserLocaleResolver()` | Gets the retained [user locale resolver](EquinoxUserLocaleResolver.md) |
| `generateIdentifier()` | Generates a random UUID as a 32-character string without hyphens |

Using the initialized `environmentService` from the example above:

=== "Java"

    ```java
    // Access the backend configuration
    EquinoxBackendConfiguration configuration =
            environmentService.getBackendConfiguration();

    // Get the resources provider, available after resource initialization
    ResourcesProvider resourcesProvider = environmentService.getResourcesProvider();

    // Get the server protector, available after initialization when configured
    ServerProtector serverProtector = environmentService.getServerProtector();

    // Get the resolver used to select the user locale
    EquinoxUserLocaleResolver<?> localeResolver =
            environmentService.getUserLocaleResolver();

    // Generate a random identifier without hyphens
    String identifier = environmentService.generateIdentifier();
    ```

=== "Kotlin"

    ```kotlin
    // Access the backend configuration
    val configuration = environmentService.getBackendConfiguration()

    // Get the resources provider, available after resource initialization
    val resourcesProvider = environmentService.getResourcesProvider()

    // Get the server protector, available after initialization when configured
    val serverProtector = environmentService.getServerProtector()

    // Get the resolver used to select the user locale
    val localeResolver = environmentService.getUserLocaleResolver()

    // Generate a random identifier without hyphens
    val identifier = environmentService.generateIdentifier()
    ```

## Access configuration values

These methods let you read a configuration section directly from `environmentService`.
The lambda receives the section and returns the value you need.

### Resources configuration

Use `useResourcesConfig()` to access the resource settings. This example checks whether the resources
folder should be created:

=== "Java"

    ```java
    boolean createResourcesFolder = environmentService.useResourcesConfig(
            config -> config.createResourcesFolder()
    );
    ```

=== "Kotlin"

    ```kotlin
    val createResourcesFolder = environmentService.useResourcesConfig { config ->
        config.createResourcesFolder()
    }
    ```

### Server protector configuration

Use `useServerProtectorConfig()` to access the server protector settings. This example reads its storage
path:

=== "Java"

    ```java
    String storagePath = environmentService.useServerProtectorConfig(config -> {
        if (config == null)
            return null;

        return config.getStoragePath();
    });
    ```

=== "Kotlin"

    ```kotlin
    val storagePath = environmentService.useServerProtectorConfig { config ->
        config?.storagePath
    }
    ```

!!! note "Optional server protector"

    The lambda receives `null` when the `server_protector` section is absent from `equinox.config`.
    In this example, `storagePath` is `null` when the protector is not configured

### Default values

Use `useDefaultsConfig()` to access the default values. This example reads the profile picture assigned
to new users:

=== "Java"

    ```java
    String profilePic = environmentService.useDefaultsConfig(
            config -> config.getDefaultUserProfilePic()
    );
    ```

=== "Kotlin"

    ```kotlin
    val profilePic = environmentService.useDefaultsConfig { config ->
        config.defaultUserProfilePic
    }
    ```

The resources and defaults sections use their standard values when omitted from `equinox.config`.
See [backend setup](../Environment.md#additional-backend-setup) to configure these values.

## Custom service

Register your implementation with `EquinoxEnvironmentService.initialize(customService)` before the first
`getInstance()` call. Later calls return that registered instance. Without registration, first access creates
the default service.

Registration rejects `null`, and an existing shared service cannot be replaced. See the
[custom environment service example](EquinoxUserLocaleResolver.md#custom-environment-service) to provide
your own locale resolver.
