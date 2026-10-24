# Overview

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

      - [SQLConstants](APIs/SQLConstants.md)  
        Collection of constants used in the SQL context

??? api-miscellaneous "Miscellaneous"
     - [ResourcesProvider](APIs/ResourcesProvider.md)  
       Manages static resources to serve to client applications

     - [EquinoxEvents](APIs/EquinoxEvents.md)  
       Facilitates event sharing and collection between backend services

??? utils "Utils"
    - [DtoUtil](Utils/DtoUtil.md)
      Converts entity lists to DTOs with optional custom converters and callbacks
    
    - [FiltersUtil](Utils/FiltersUtil.md)
      Provides utility methods to handle filters

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation. You can take
a look [here](Environment.md) to check how it works and how to implement it on your own backend

## Validation

Use the [validation guide](Validation/index.md) to apply the supplied
Jakarta Bean Validation constraints to your request DTOs

- [Name](Validation/Name.md) validates names
- [Surname](Validation/Surname.md) validates surnames
- [Email](Validation/Email.md) validates email addresses

## Exceptions

Use the [exception guide](Exceptions/index.md) to return localized failure responses
with the supplied exceptions

- [EquinoxGlobalExceptionHandler](Exceptions/EquinoxGlobalExceptionHandler.md)
  converts Equinox exceptions and request validation failures into HTTP responses

## Security

The backend provides Spring Security components for token authentication and user authorization

- [SecurityConfig](Configurations.md#securityconfig) provides replaceable security defaults and a customizable chain baseline
- [EquinoxTokenAuthenticationFilter](Security/authentication/EquinoxTokenAuthenticationFilter.md) retrieves users from the token request header
- [EquinoxUserAuthentication](Security/authentication/EquinoxUserAuthentication.md) exposes the user as the authentication principal
- [IsAuthorizedUser](Security/authorization/annotations/IsAuthorizedUser.md) checks that the requested user identifier matches the authenticated user's identifier

See the [security guide](Security/index.md) for configuration, token authentication, and user authorization usage

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

## Annotations available

This module provides a set of annotations that can be used to improve code readability and maintainability.
You can explore the available annotations below:

- [BatchQueryImpl](Annotations/batch/BatchQueryImpl.md)
- [BatchQueryItem](Annotations/batch/BatchQueryItem.md)
- [BatchSyncProcedureImpl](Annotations/batch/BatchSyncProcedureImpl.md)
- [ConstraintValidatorDefinition](Annotations/ConstraintValidatorDefinition.md)
- [FiltersAdder](Annotations/filters/FiltersAdder.md)
- [FiltersExtractor](Annotations/filters/FiltersExtractor.md)
- [EmptyConstructor](Annotations/EmptyConstructor.md)
- [EntityRetriever](Annotations/EntityRetriever.md)
- [Manager](Annotations/Manager.md)
- [MappingPurpose](Annotations/MappingPurpose.md)
- [TableColumns](Annotations/TableColumns.md)

For event consumers, handlers, and emitters, use the following annotations:

- [EventConsumer](Annotations/events/EventConsumer.md) identifies types and methods that consume application events
- [EventsHandler](Annotations/events/EventsHandler.md) identifies types that handle events
- [EventsNotifier](Annotations/events/EventsNotifier.md) identifies types that emit events
- [EventsProcessor](Annotations/events/EventsProcessor.md) identifies services that both handle and emit events

For request and response DTOs, use the following annotations:

- [DtoRequest](Annotations/dto/DtoRequest.md) identifies DTOs carrying incoming request data
- [DtoResponse](Annotations/dto/DtoResponse.md) identifies DTOs carrying outgoing response data
