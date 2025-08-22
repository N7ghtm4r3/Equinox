# Equinox Backend

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

    ```groovy
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        maven { url 'https://repo.clojars.org' }
    }
    ```

  #### Gradle (Kotlin)

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
        implementation 'io.github.n7ghtm4r3:equinox-backend:1.1.4'
        
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'
    }
    ```

  #### Gradle (Kotlin)

    ```kotlin
    dependencies {
        
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:1.1.4")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
    }
    ```

  #### Gradle (version catalog)

    ```kotlin
    dependencies {
    
        // implement the backend utilities
        implementation(libs.equinox.backend)
    
        // implement the core utilities
        implementation(libs.equinox.core)
    }
    ```

## APIs available

- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/ResourcesProvider.md">**ResourcesProvider**</a>
  allows to manage the static resources to serve to the clients
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/IndexesCreator.md">**IndexesCreator**</a>
  allows to create automatically the indexes for your own tables of the database
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/FilteredQuery.md">**FilteredQuery**</a> allows
  you to create dynamic queries with filters parameters
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/EquinoxItemsHelper.md">**EquinoxItemsHelper**</a> allows to execute batch queries such insertion, deletion and synchronization dynamically
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/BatchSynchronizationProcedure.md">**BatchSynchronizationProcedure**</a> 
allows to create reusable synchronization procedures compacted and cleaned
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/EquinoxEvents.md">**EquinoxEventsEmitter**</a> allows to share and collect events between services

The other apis will be gradually released

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation. You can take
a
look [here](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/EquinoxEnvironment.md) to check how it works
and how
to implement it on your own backend

## Configure your backend

To configure your backend as you need you can follow this [guide](../documd/backend/Configurations.md) to directly copy
the `@Component` classes you need and implement them in your backend instance, personal advice is put them in a folder
named `configuration` for clean implementation and better readability as follows:

 ``` bash
  com.your.package
   └── configuration
       └── config_class
       └── config_class
       └── config_class
       └── ...
  ```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot) [![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit
