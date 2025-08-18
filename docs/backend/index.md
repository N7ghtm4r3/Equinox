# Overview

**v1.1.4**

Utilities and apis for the backends based on the **Spring Boot** framework

## Implementation

### Version catalog

```toml
[versions]
equinox-backend = "1.1.4"
equinox = "1.1.5"

[libraries]
equinox-backend = { module = "io.github.n7ghtm4r3:equinox-backend", version.ref = "equinox-backend" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
```

### Gradle

Add the JitPack repository to your build file

- Add it in your root build.gradle at the end of repositories

    ```gradle
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        maven { url 'https://repo.clojars.org' }
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```gradle
    repositories {
        ...
        maven("https://jitpack.io")
        maven("https://repo.clojars.org")
    }
    ```

- Add the dependency

    ```gradle
    dependencies {
       
        // implement the backend utilities
        implementation 'io.github.n7ghtm4r3:equinox-backend:1.1.4'
        
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```gradle
    dependencies {
        
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:1.1.4")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
    }
    ```

    <h4> Gradle (version catalog) </h4>

    ```gradle
    dependencies {
    
        // implement the backend utilities
        implementation(libs.equinox.backend)
    
        // implement the core utilities
        implementation(libs.equinox.core)
    }
    ```

## APIs available

- [ResourcesProvider](APIs/ResourcesProvider.md) allows you to manage the static resources to serve to the clients
- [IndexesCreator](APIs/IndexesCreator.md) allows you to create automatically the indexes for your own tables of the
  database
- [FilteredQuery](APIs/FilteredQuery.md) allows you to create dynamic queries with filters parameters
- [EquinoxItemsHelper](APIs/EquinoxItemsHelper.md)  allows you to execute batch queries such insertion, deletion and
  synchronization dynamically
- [BatchSynchronizationProcedure](APIs/BatchSynchronizationProcedure.md) allows you to create reusable synchronization
  procedures compacted and cleaned
- [EquinoxEvents](APIs/EquinoxEvents.md) allows you to share and collect events between services

The other apis will be gradually released

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation. You can take
a look [here](Environment.md) to check how it works and how to implement it on your own backend

## Configure your backend

To configure your backend as you need you can follow this [guide](Configurations.md) to directly copy
the `@Component` classes you need and implement them in your backend instance, personal advice is put them in a folder
named `configuration` for clean implementation and better readability as follows:

```bash
com.your.package
└── configuration
   └── config_class
   └── config_class
   └── config_class
   └── ...
```