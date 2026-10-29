---
title: "EquinoxEnvironmentService"
slug: "backend/APIs/EquinoxEnvironmentService"
---

Provides shared access to the backend configuration, resources, server protector, and user locale resolver.

## Usage

Configure [`equinox.config`](/Equinox/backend/Environment/#additional-backend-setup), then initialize the environment
in `main` before starting Spring Boot:


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;

EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

environmentService.initEquinoxEnvironment(args);
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService

val environmentService = EquinoxEnvironmentService.getInstance()

environmentService.initEquinoxEnvironment(args)
```

</div>

</div>

Initialization creates the configured resource directories and launches the server protector when enabled.
An enabled server protector can be initialized only once. Initialization failures are wrapped in
`RuntimeException`.

Additional arguments to `initEquinoxEnvironment(args, ...)` format the protector's save message;
see the [configuration example](/Equinox/backend/Environment/#format-the-protectors-save-message).

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
| `getResourcesProvider()` | Gets the [resources provider](/Equinox/backend/APIs/ResourcesProvider/), or `null` before initialization or when disabled |
| `getServerProtector()` | Gets the server protector, or `null` before initialization or when disabled |
| `getUserLocaleResolver()` | Gets the retained [user locale resolver](/Equinox/backend/APIs/EquinoxUserLocaleResolver/) |
| `generateIdentifier()` | Generates a random UUID as a 32-character string without hyphens |

Using the initialized `environmentService` from the example above:


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


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

</div>

</div>

## Access configuration values

These methods let you read a configuration section directly from `environmentService`.
The lambda receives the section and returns the value you need.

### Resources configuration

Use `useResourcesConfig()` to access the resource settings. This example checks whether the resources
folder should be created:


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
boolean createResourcesFolder = environmentService.useResourcesConfig(
        config -> config.createResourcesFolder()
);
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
val createResourcesFolder = environmentService.useResourcesConfig { config ->
    config.createResourcesFolder()
}
```

</div>

</div>

### Server protector configuration

Use `useServerProtectorConfig()` to access the server protector settings. This example reads its storage
path:


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
String storagePath = environmentService.useServerProtectorConfig(config -> {
    if (config == null)
        return null;

    return config.getStoragePath();
});
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
val storagePath = environmentService.useServerProtectorConfig { config ->
    config?.storagePath
}
```

</div>

</div>


<aside class="equinox-callout note">

<p class="equinox-callout-title">Optional server protector</p>


The lambda receives `null` when the `server_protector` section is absent from `equinox.config`.
In this example, `storagePath` is `null` when the protector is not configured

</aside>

### Default values

Use `useDefaultsConfig()` to access the default values. This example reads the profile picture assigned
to new users:


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
String profilePic = environmentService.useDefaultsConfig(
        config -> config.getDefaultUserProfilePic()
);
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
val profilePic = environmentService.useDefaultsConfig { config ->
    config.defaultUserProfilePic
}
```

</div>

</div>

The resources and defaults sections use their standard values when omitted from `equinox.config`.
See [backend setup](/Equinox/backend/Environment/#additional-backend-setup) to configure these values.

## Custom service

Register your implementation with `EquinoxEnvironmentService.initialize(customService)` before the first
`getInstance()` call. Later calls return that registered instance. Without registration, first access creates
the default service.

Registration rejects `null`, and an existing shared service cannot be replaced. See the
[custom environment service example](/Equinox/backend/APIs/EquinoxUserLocaleResolver/#custom-environment-service) to provide
your own locale resolver.
