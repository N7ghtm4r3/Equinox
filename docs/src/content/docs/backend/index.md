---
title: "Overview"
slug: "backend"
---

<span id="overview"></span>

**v2.0.0**

Utilities and apis for the backends based on the **Spring Boot** framework

## Implementation

### Version catalog

```toml
[versions]
equinox-backend = "2.0.0"
equinox = "1.2.1"

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
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```kotlin
    repositories {
        ...
        maven("https://jitpack.io")
    }
    ```

- Add the dependency

    ```groovy
    dependencies {
       
        // implement the backend utilities
        implementation 'io.github.n7ghtm4r3:equinox-backend:2.0.0'
        
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.2.1'
    }
    ```

    <h4> Gradle (Kotlin) </h4>

    ```kotlin
    dependencies {
        
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:2.0.0")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.2.1")
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


<details class="equinox-callout batch">

<summary>Batch</summary>

  - [EquinoxItemsHelper](/Equinox/backend/APIs/EquinoxItemsHelper/)  
    Executes batch database operations such as insertion, deletion, and synchronization

  - [BatchSynchronizationProcedure](/Equinox/backend/APIs/BatchSynchronizationProcedure/)  
    Defines reusable and compacted synchronization procedures

</details>


<details class="equinox-callout database">

<summary>Database</summary>

  - [IndexesCreator](/Equinox/backend/APIs/IndexesCreator/)  
    Automatically creates indexes for custom database tables

  - [FilteredQuery](/Equinox/backend/APIs/FilteredQuery/)  
    Builds dynamic queries with filter parameters leverages regex expressions

  - [SQLConstants](/Equinox/backend/APIs/SQLConstants/)  
    Collection of constants used in the SQL context

</details>


<details class="equinox-callout api-miscellaneous">

<summary>Miscellaneous</summary>

 - [ResourcesProvider](/Equinox/backend/APIs/ResourcesProvider/)  
   Manages static resources to serve to client applications

 - [EquinoxEvents](/Equinox/backend/APIs/EquinoxEvents/)  
   Facilitates event sharing and collection between backend services

</details>


<details class="equinox-callout utils">

<summary>Utils</summary>

- [DtoUtil](/Equinox/backend/Utils/DtoUtil/)
  Converts entity lists to DTOs with optional custom converters and callbacks

- [FiltersUtil](/Equinox/backend/Utils/FiltersUtil/)
  Provides utility methods to handle filters

</details>

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation. You can take
a look [here](/Equinox/backend/Environment/) to check how it works and how to implement it on your own backend

## Soft deletion

Use the [soft deletion guide](/Equinox/backend/SoftDeletion/) to retain recoverable entities, permanently delete accounts,
and guard custom service operations. It also explains account restoration during sign-in

## Validation

Use the [validation guide](/Equinox/backend/Validation/) to apply the supplied
Jakarta Bean Validation constraints to your request DTOs

- [Name](/Equinox/backend/Validation/Name/) validates names
- [Surname](/Equinox/backend/Validation/Surname/) validates surnames
- [Email](/Equinox/backend/Validation/Email/) validates email addresses
- [UserLanguage](/Equinox/backend/Validation/UserLanguage/) validates supported user languages
- [ServerSecret](/Equinox/backend/Validation/ServerSecret/) validates the server protection secret

## Exceptions

Use the [exception guide](/Equinox/backend/Exceptions/) to return localized failure responses
with the supplied exceptions

- [EquinoxGlobalExceptionHandler](/Equinox/backend/Exceptions/EquinoxGlobalExceptionHandler/)
  converts Equinox exceptions and request validation failures into HTTP responses

## Security

The backend provides Spring Security components for token authentication and user authorization

- [SecurityConfig](/Equinox/backend/Configurations/#securityconfig) provides replaceable security defaults and a customizable chain baseline
- [EquinoxTokenAuthenticationFilter](/Equinox/backend/Security/authentication/EquinoxTokenAuthenticationFilter/) retrieves users from the token request header
- [EquinoxUserAuthentication](/Equinox/backend/Security/authentication/EquinoxUserAuthentication/) exposes the user as the authentication principal
- [IsAuthorizedUser](/Equinox/backend/Security/authorization/annotations/IsAuthorizedUser/) checks that the requested user identifier matches the authenticated user's identifier

See the [security guide](/Equinox/backend/Security/) for configuration, token authentication, and user authorization usage

## Configure your backend

To configure your backend as you need you can follow this [guide](/Equinox/backend/Configurations/) to directly copy
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

## Annotations available

This module provides a set of annotations that can be used to improve code readability and maintainability.
You can explore the available annotations below:

- [BatchQueryImpl](/Equinox/backend/Annotations/batch/BatchQueryImpl/)
- [BatchQueryItem](/Equinox/backend/Annotations/batch/BatchQueryItem/)
- [BatchSyncProcedureImpl](/Equinox/backend/Annotations/batch/BatchSyncProcedureImpl/)
- [ConstraintValidatorDefinition](/Equinox/backend/Annotations/ConstraintValidatorDefinition/)
- [FiltersAdder](/Equinox/backend/Annotations/filters/FiltersAdder/)
- [FiltersExtractor](/Equinox/backend/Annotations/filters/FiltersExtractor/)
- [EmptyConstructor](/Equinox/backend/Annotations/EmptyConstructor/)
- [EntityRetriever](/Equinox/backend/Annotations/EntityRetriever/)
- [Manager](/Equinox/backend/Annotations/Manager/)
- [MappingPurpose](/Equinox/backend/Annotations/MappingPurpose/)
- [TableColumns](/Equinox/backend/Annotations/TableColumns/)

For event consumers, handlers, and emitters, use the following annotations:

- [EventConsumer](/Equinox/backend/Annotations/events/EventConsumer/) identifies types and methods that consume application events
- [EventsHandler](/Equinox/backend/Annotations/events/EventsHandler/) identifies types that handle events
- [EventsNotifier](/Equinox/backend/Annotations/events/EventsNotifier/) identifies types that emit events
- [EventsProcessor](/Equinox/backend/Annotations/events/EventsProcessor/) identifies services that both handle and emit events

For request and response DTOs, use the following annotations:

- [DtoRequest](/Equinox/backend/Annotations/dto/DtoRequest/) identifies DTOs carrying incoming request data
- [DtoResponse](/Equinox/backend/Annotations/dto/DtoResponse/) identifies DTOs carrying outgoing response data
