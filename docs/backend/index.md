# Overview

**v1.1.5**

Utilities and apis for the backends based on the **Spring Boot** framework

## Implementation

### Version catalog

```toml
[versions]
equinox-backend = "1.1.5"
equinox = "1.1.6"

[libraries]
equinox-backend = { module = "io.github.n7ghtm4r3:equinox-backend", version.ref = "equinox-backend" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
```

### Gradle

Add the JitPack repository to your build file

- Add it in your root build.gradle at the end of repositories

    ```groovy
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        maven { url 'https://repo.clojars.org' }
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```kotlin
    repositories {
        ...
        maven("https://jitpack.io")
        maven("https://repo.clojars.org")
    }
    ```

- Add the dependency

    ```groovy
    dependencies {
       
        // implement the backend utilities
        implementation 'io.github.n7ghtm4r3:equinox-backend:1.1.5'
        
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.1.6'
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```kotlin
    dependencies {
        
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:1.1.5")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.1.6")
    }
    ```

    <h4> Gradle (version catalog) </h4>

    ```kotlin
    dependencies {
    
        // implement the backend utilities
        implementation(libs.equinox.backend)
    
        // implement the core utilities
        implementation(libs.equinox.core)
    }
    ```

## APIs available

??? batch "Batch"
      - [EquinoxItemsHelper](APIs/EquinoxItemsHelper.md)  
        Executes batch database operations such as insertion, deletion, and synchronization
    
      - [BatchSynchronizationProcedure](APIs/BatchSynchronizationProcedure.md)  
        Defines reusable and compacted synchronization procedures

??? database "Database"
      - [IndexesCreator](APIs/IndexesCreator.md)  
        Automatically creates indexes for custom database tables

      - [FilteredQuery](APIs/FilteredQuery.md)  
        Builds dynamic queries with filter parameters leverages regex expressions

??? api-miscellaneous "Miscellaneous"
     - [ResourcesProvider](APIs/ResourcesProvider.md)  
       Manages static resources to serve to client applications

     - [EquinoxEvents](APIs/EquinoxEvents.md)  
       Facilitates event sharing and collection between backend services

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