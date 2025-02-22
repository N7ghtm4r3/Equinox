# Equinox Backend

**v1.0.8**

Utilities and apis for the backends based on the **Spring Boot**'s architecture

## Implementation

### Version catalog

```gradle
[versions]
equinox = "1.0.8"

[libraries]
equinox-backend = { module = "io.github.n7ghtm4r3:equinox-backend", version.ref = "equinox" }
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

  #### Gradle (Kotlin)

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
        implementation 'io.github.n7ghtm4r3:equinox-backend:1.0.8'
        
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.0.8'
    }
    ```

  #### Gradle (Kotlin)

    ```gradle
    dependencies {
        
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:1.0.8")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.0.8")
    }
    ```

  #### Gradle (version catalog)

    ```gradle
    dependencies {
    
        // implement the backend utilities
        implementation(libs.equinox.backend)
    
        // implement the core utilities
        implementation(libs.equinox.core)
    }
    ```

## APIs available

- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/ResourcesProvider.md">**ResourcesProvider**</a>
  allows you to manage the static resources to serve to the clients
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/ConfigsGenerator.md">**ConfigsGenerator**</a>
  allows
  you to create automatically the configuration file for your backend
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/IndexesCreator.md">**IndexesCreator**</a>
  allows you to create automatically the the indexes for your own tables of the database
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/FilteredQuery.md">**FilteredQuery**</a> allows
  you to create dynamic queries with filters parameters
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/EquinoxItemsHelper.md">**EquinoxItemsHelper**</a> allows you to execute batch queries such insertion, deletion and synchronization dynamically

The other apis will be gradually released

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation. You can take
a
look [here](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/EquinoxEnvironment.md) to check how it works
and how
to implement it on your own backend

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
