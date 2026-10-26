## Summary

This group of classes is useful for creating a Spring Boot environment based on the Equinox system.
The base environment provides a core set of classes to manage users, controllers, and database operations.

### Main Classes

- [EquinoxEnvironmentService](APIs/EquinoxEnvironmentService.md)  
  Initializes the backend environment and provides access to its configuration, resources, and server protector

- [EquinoxBaseEndpointsSet](../core/APIs/common/EquinoxBaseEndpointsSet.md)  
  A set of pre-defined endpoints for the Equinox's environment

- [EquinoxControllerCore](APIs/EquinoxControllerCore.md)  
  Provides the logic of an Equinox's controller and some utilities to build responses and to handle the `i18n`

- [EquinoxController](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/controllers/EquinoxController.java)   
  Manages other rest controllers in the backend providing utilities for the user handling

- [DefaultEquinoxController](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/DefaultEquinoxController.java)  
  Manages other rest controllers with default usage of
  the [EquinoxUser](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)  
  class

- [EquinoxItem](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/entities/EquinoxItem.java)  
  Base class for items used by an Equinox backend

### User Utilities

- [EquinoxUser](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)  
  Standard Equinox user class

- [EquinoxLocalUser](../compose/APIs/EquinoxLocalUser.md)  
  Helper for managing a local user session in client applications

- [EquinoxUsersController](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/controller/EquinoxUsersController.java)  
  Controller for operations on EquinoxUser objects

- [EquinoxUsersService](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/service/EquinoxUsersService.java)  
  Helper used by EquinoxUsersController to interact with the database

- [EquinoxUsersRepository](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/repository/EquinoxUsersRepository.java)    
  JpaRepository for executing user-related database queries

### Other Utilities

- [EquinoxMessages](APIs/EquinoxMessages.md)  
  Collection of message keys used for backend validation and response messages

- [EquinoxRequester](../../equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/network/EquinoxRequester.kt)  
  Request helper with
  pre-implemented [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)
  requests

- [InputsValidator](../../equinox-core/src/commonMain/kotlin/com/tecknobit/equinoxcore/helpers/InputsValidator.kt)  
  Utility class for input validation,
  including [EquinoxUser](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)
  details

- [EquinoxItemsHelper](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/apis/batch/EquinoxItemsHelper.java)  
  Helper for database operations
  on [EquinoxItem](../../equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/entities/EquinoxItem.java)
## Usage

### Gradle configuration

To correctly use this environment you need to use the following base **gradle configuration**

=== "Gradle"

    ```groovy
    plugins {
        id 'java'
        id 'org.springframework.boot' version '3.2.3'
    }
    
    apply plugin: 'io.spring.dependency-management'
    
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-web:3.2.3'
        implementation 'org.springframework.boot:spring-boot-starter-validation:3.2.3'
        implementation 'org.springframework.boot:spring-boot-starter-security:3.2.3'
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa:3.2.3'
        implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.4'
        implementation 'mysql:mysql-connector-java:8.0.33'
        implementation 'com.github.N7ghtm4r3:APIManager:2.2.4'
        implementation 'org.json:json:20250517'
    
        // implement the backend utilities
        implementation 'io.github.n7ghtm4r3:equinox-backend:2.0.0'
    
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.2.1'

        ...
    }
    ```

=== "Gradle (Kotlin)"

    ```kotlin
    plugins {
        id("java")
        id("org.springframework.boot") version "3.2.3"
    }
    
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
    
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
        implementation("org.springframework.boot:spring-boot-starter-validation:3.2.3")
        implementation("org.springframework.boot:spring-boot-starter-security:3.2.3")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
        implementation("mysql:mysql-connector-java:8.0.33")
        implementation("com.github.N7ghtm4r3:APIManager:2.2.4")
        implementation("org.json:json:20250517")
    
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:2.0.0")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.2.1")
    
        ...
    }
    ```

### Out-of-the-Box Launch

The **EquinoxEnvironmentService** initializes the resource directories and server protector according to the
backend configuration. Call it before starting the **SpringApplication**, as shown below

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.domain.EntityScan;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.FilterType;
    import org.springframework.context.event.ContextRefreshedEvent;
    import org.springframework.context.event.EventListener;
    import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
    import org.springframework.web.method.HandlerMethod;
    import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
    import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
    
    import java.util.Map;
    
    @EnableAutoConfiguration
    @EnableJpaRepositories(
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages..."}
    )
    @EntityScan(
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages..."}
    )
    @ComponentScan(
        value = {
            "com.tecknobit.*" /* REQUIRED */,
            "other.packages...",
            "com.tecknobit.equinoxbackend.environment.configuration" /* REQUIRED TO USE THE PROVIDED MESSAGES BUNDLE */
        },
        excludeFilters = @ComponentScan.Filter(
            type = FilterType.CUSTOM,
            classes = AutoConfigurationExcludeFilter.class
        )
    )
    @SpringBootApplication
    public class Launcher {
    
        public static void main(String[] args) {
            EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args);
            // ... your code ...
            // then normally launch your SpringBoot's application
            SpringApplication.run(Launcher.class, args);
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService
    import org.springframework.boot.SpringApplication
    import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.autoconfigure.domain.EntityScan
    import org.springframework.context.ApplicationContext
    import org.springframework.context.event.ContextRefreshedEvent
    import org.springframework.context.event.EventListener
    import org.springframework.context.annotation.ComponentScan
    import org.springframework.context.annotation.FilterType
    import org.springframework.data.jpa.repository.config.EnableJpaRepositories
    import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
    import org.springframework.web.servlet.mvc.method.RequestMappingInfo
    import org.springframework.web.method.HandlerMethod
    
    @EnableJpaRepositories(
        value = ["com.tecknobit.*" /* REQUIRED */, "other.packages..."]
    )
    @EntityScan(
        value = ["com.tecknobit.*" /* REQUIRED */, "other.packages..."]
    )
    @ComponentScan(
        value = [
            "com.tecknobit.*" /* REQUIRED */,
            "other.packages...",
            "com.tecknobit.equinoxbackend.environment.configuration" /* REQUIRED TO USE THE PROVIDED MESSAGES BUNDLE */
        ],
        excludeFilters = [ComponentScan.Filter(
            type = FilterType.CUSTOM,
            classes = [AutoConfigurationExcludeFilter::class]
        )]
    )
    @SpringBootApplication
    class Launcher {
    
        companion object {
            @JvmStatic
            fun main(args: Array<String>) {
                EquinoxEnvironmentService.getInstance().initEquinoxEnvironment(args)
                // ... your code ...
                // then normally launch your SpringBoot's application
                SpringApplication.run(Launcher::class.java, *args)
            }
        }

    }
    ```

!!! note "Component scanning and security auto-configuration"

    The examples scan Equinox packages to discover the backend components
    `AutoConfigurationExcludeFilter` keeps registered auto-configurations, including `SecurityConfig`,
    out of that component scan. Spring Boot still loads them automatically after your application's
    bean definitions, allowing your custom beans to take precedence over the security defaults

    This filter does not disable auto-configuration. See
    [SecurityConfig](Configurations.md#securityconfig) for replacing its default beans or using a subclass

!!! note

     The REQUIRED annotations are necessary to correctly launch automatically the **EquinoxUsersController** and its
     related workflow, so if you don't want to use that controller you can simply remove them, and it will not automatically start

#### Environment service

The **EquinoxEnvironmentService** manages the backend environment through a shared instance

##### Access the service

=== "Java"

    ```java
    EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
    ```

=== "Kotlin"

    ```kotlin
    val environmentService = EquinoxEnvironmentService.getInstance()
    ```

##### Generate identifiers

Generates random UUID identifiers without hyphens

=== "Java"

    ```java
    String identifier = environmentService.generateIdentifier();
    ```

=== "Kotlin"

    ```kotlin
    val identifier = environmentService.generateIdentifier()
    ```

##### Access configuration and components

| Method | Provides access to |
|--------|--------------------|
| `getBackendConfiguration()` | The configuration defined in [equinox.config](#additional-backend-setup) |
| `getResourcesProvider()` | The [resources provider](APIs/ResourcesProvider.md) |
| `getServerProtector()` | The server protector |

The resources provider and server protector getters return `null` until their components are instantiated.
Disabled components remain `null`

##### Format the protector's save message

Pass the message values after the command-line arguments when initializing the environment:

=== "Java"

    ```java
    environmentService.initEquinoxEnvironment(args, "My backend");
    ```

=== "Kotlin"

    ```kotlin
    environmentService.initEquinoxEnvironment(args, "My backend")
    ```

#### Inspect the current mapped endpoints

To easily inspect the currently mapped endpoints, you can use the following method, which also verifies whether any
inherited controllers from **EquinoxController** are mapped correctly

=== "Java"

    ```java
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        System.out.println("----------- CURRENT ENDPOINTS MAPPED -----------");
        map.forEach((key, value) -> System.out.println("| " + key + " " + value));
        System.out.println("--------------------------------------------");
    }
    ```

=== "Kotlin"

    ```kotlin
    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        val applicationContext: ApplicationContext = event.applicationContext
        val requestMappingHandlerMapping = applicationContext
            .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping::class.java)
        val map: Map<RequestMappingInfo, HandlerMethod> = requestMappingHandlerMapping.handlerMethods
        println("----------- CURRENT ENDPOINTS MAPPED -----------")
        map.forEach { key, value -> println("| $key $value") }
        println("--------------------------------------------")
    }
    ```

#### Launching the backend without a database

If your architecture does not require a database you can exclude it as follows:

=== "Java"

    ```java
    @SpringBootApplication
    @EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
    )
    ```

=== "Kotlin"

    ```kotlin
    @SpringBootApplication
    @EnableAutoConfiguration(
        exclude = [DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class]
    )
    ```

## Extending the environment

Follow this guide to customize any classes of the base environment and ensure your changes function as expected

### Additional Backend Setup

#### Create the config file

To add extra configuration to your backend instance, create an `equinox.config` file and place it in the following
location:

``` bash 
resources
  └── equinox.config
```

#### Fill the configuration

You can copy and fill the below `JSON` as you need:

```json
{
  // omit this to disable the server protector usage
  "server_protector": {
    "storage_path": "storage_path_of_the_protector",
    "save_message": "save_message_value"
  },
  "resources": {
    "resources_folder": true,
    // false to avoid to create the resources folder 
    "subdirectories": [
      // list of subdirectories to create inside the resources folder, omit to avoid the creation
    ]
  },
  "defaults": {
    // profile pic saved and returned at sign-up; defaults to profiles/defProfilePic.png if omitted
    "default_user_profile_pic": "profiles/customDefault.png"
  }
}
```

### EquinoxUser customization

In this example, will be added the `currency` field to the custom user and will be included the related update request.

#### Creating a CustomUser

To add the custom field you need to extend the **EquinoxUser** class as follows:

=== "Java"

    ```java
    package other.packages
    
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import org.json.JSONObject;
    
    @Entity
    public class CustomUser extends EquinoxUser {
    
        @Column
        private final String currency;
        
        public CustomUser(String id, String token, String name, String surname, String email, String password, String language,
                          String currency) {
            super(id, token, name, surname, email, password, language);
            this.currency = currency;
        }
        
        public CustomUser(String id, String token, String name, String surname, String email, String password, String profilePic,
                          String language, ApplicationTheme theme, String currency) {
            super(id, token, name, surname, email, password, profilePic, language, theme);
            this.currency = currency;
        }

        public String getCurrency() {
            return currency;
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import jakarta.persistence.Column
    import jakarta.persistence.Entity
    
    @Entity
    class CustomUser : EquinoxUser {
    
        @Column
        val currency: String
    
        constructor(
            id: String,
            token: String,
            name: String,
            surname: String,
            email: String,
            password: String,
            language: String,
            currency: String
        ) : super(id, token, name, surname, email, password, language) {
            this.currency = currency
        }
    
        constructor(
            id: String,
            token: String,
            name: String,
            surname: String,
            email: String,
            password: String,
            profilePic: String,
            language: String,
            theme: ApplicationTheme,
            currency: String
        ) : super(id, token, name, surname, email, password, profilePic, language, theme) {
            this.currency = currency
        }

    }
    ```

A single `users` table will be created in the database, with the `dtype` field used as a discriminator to distinguish
between different classes.
This does not affect the existing queries implemented for the **EquinoxUser** class

#### Creating the dedicated repository

To handle the database operations of your custom user, you have to create the dedicated repository extending the
[EquinoxUsersRepository](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/repository/EquinoxUsersRepository.java)
as follows:

=== "Java"

    ```java
    package other.packages
            
    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.context.annotation.Primary;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import org.springframework.stereotype.Service;
    
    import static com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem.IDENTIFIER_KEY;
    import static com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.USERS_KEY;
    
    @Repository
    public interface CustomUsersRepository extends EquinoxUsersRepository<CustomUser> {
    
          @Modifying(clearAutomatically = true)
          @Query(
                  value = "UPDATE " + USERS_KEY + " SET " + "currency" + "=:" + "currency" + " WHERE "
                          + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
                  nativeQuery = true
          )
          void changeCurrency(
                  @Param("currency") String newCurrency,
                  @Param(IDENTIFIER_KEY) String id
          );
    
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository
    import jakarta.transaction.Transactional
    import org.springframework.data.jpa.repository.Modifying
    import org.springframework.data.jpa.repository.Query
    import org.springframework.data.repository.query.Param
    import org.springframework.stereotype.Repository
    import com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem.IDENTIFIER_KEY
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.USERS_KEY
    
    @Repository
    interface CustomUsersRepository : EquinoxUsersRepository<CustomUser> {
    
        @Modifying(clearAutomatically = true)
        @Query(
            value = "UPDATE $USERS_KEY SET currency =: currency WHERE $IDENTIFIER_KEY =: $IDENTIFIER_KEY",
            nativeQuery = true
        )
        fun changeCurrency(
            @Param("currency") newCurrency: String,
            @Param(IDENTIFIER_KEY) id: String
        )

    }
    ```

Custom user repositories inherit the soft deletion queries from `EquinoxUsersRepository`. See the
[soft deletion guide](SoftDeletion.md#user-accounts) for account state and lookup behavior

#### Creating the dedicated service

To handle the requests of your custom user, you have to create the dedicated service extending the
[EquinoxUsersService](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/service/EquinoxUsersService.java)
as follows:

=== "Java"

    ```java
    package other.packages
    
    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Primary;
    import org.springframework.stereotype.Service;
    
    @Service
    public class CustomUsersService extends EquinoxUsersService<CustomUser, CustomUsersRepository> {
    
        @Transactional
        public void changeCurrency(String newCurrency, String userId) {
            usersRepository.changeCurrency(newCurrency, userId);
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService
    import jakarta.transaction.Transactional
    import org.springframework.stereotype.Service
    
    @Service
    class CustomUsersService : EquinoxUsersService<CustomUser, CustomUsersRepository>() {
    
        @Transactional
        fun changeCurrency(
            newCurrency: String,
            userId: String
        ) {
            usersRepository.changeCurrency(
                newCurrency,
                userId
            )
        }
    
    }
    ```

For custom operations that require an active user, use the inherited
[`requireEntityNotSoftDeleted()` guard](SoftDeletion.md#guard-custom-operations). It rejects missing and
soft deleted users before running your callback

#### Creating the dedicated controller

To perform and handle the requests of your custom user, you have to create the dedicated service extending the
[EquinoxUsersService](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/service/EquinoxUsersService.java)
as follows:

=== "Java"

    ```java
    package other.packages
    
    import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
    import org.json.JSONObject;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.Map;
    
    @RestController
    public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersService> {
    
        // you can override an implemented method to customize the response as you necessities
        @Override
        public String changeEmail(
                String id,
                String token,
                Map<String, String> payload
        ) {
            String response = super.changeEmail(id, token, payload);
            JSONObject customResponse = new JSONObject(response);
            customResponse.put("currency", "currency_value");
            return customResponse.toString();
        }
    
        @PatchMapping(
                path = {"users/{user_id}/changeCurrency"},
                headers = {TOKEN_KEY}
        )
        public String changeCurrency(
                @PathVariable("id") String id,
                @RequestHeader("token") String token,
                @RequestBody Map<String, String> payload
        ) {
            if (isMe(id, token)) {
                usersHelper.changeCurrency(payload.get("currency"), id);
                return successResponse();
            } else
                return notAuthorizedOrWrongDetailsResponse();
        }
    
    }
    ```

=== "Kotlin"

    ```java
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController
    import org.json.JSONObject
    import org.springframework.web.bind.annotation.*
    
    @RestController
    class CustomUsersController : EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersService>() {
    
        // you can override an implemented method to customize the response as you necessities
        override fun changeEmail(
            id: String,
            token: String,
            payload: Map<String, String>
        ): String {
            val response = super.changeEmail(id, token, payload)
            val customResponse = JSONObject(response)
            customResponse.put("currency", "currency_value")
            return customResponse.toString()
        }
    
        @PatchMapping(
            path = ["users/{user_id}/changeCurrency"],
            headers = [TOKEN_KEY]
        )
        fun changeCurrency(
            @PathVariable("id") id: String,
            @RequestHeader("token") token: String,
            @RequestBody payload: Map<String, String>
        ): String {
            return if (isMe(id, token)) {
                usersHelper.changeCurrency(
                    payload["currency"] ?: "",
                    id
                )
                successResponse()
            } else
                notAuthorizedOrWrongDetailsResponse()
        }
    
    }
    ```

You can also create your own default controller
as [DefaultEquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/DefaultEquinoxController.java)
with your own [CustomUser](#creating-a-customuser) instead:

=== "Java"

    ```java
    public abstract class MyOwnDefaultController extends EquinoxController<CustomUser, CustomUsersRepository, CustomUsersService> {
    }
    ```

=== "Kotlin"

    ```kotlin
    abstract class MyOwnDefaultController : EquinoxController<CustomUser, CustomUsersRepository, CustomUsersService>() {
    }
    ```

#### Custom sign-up

In this example, `currency` is added to the request, saved with the custom user, and included in the response

###### Define the custom request DTO

The request extends `SignUpRequestDto` with the required `currency` field. The Jackson annotations
select this subtype for the inherited endpoint; the class belongs to a package scanned by Spring Boot

!!! note "Constructor binding"

    These examples use Spring Boot 4 with Jackson 3.1 or later. Java requires `-parameters`;
    Kotlin requires Spring Boot's managed `tools.jackson.module:jackson-module-kotlin`

=== "Java"

    ```java
    package other.packages;

    import com.fasterxml.jackson.annotation.JsonDeserializeAs;
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto;
    import jakarta.validation.constraints.NotBlank;
    import org.springframework.boot.jackson.JacksonMixin;

    @DtoRequest
    @JacksonMixin(SignUpRequestDto.class)
    @JsonDeserializeAs(CustomSignUpRequestDto.class)
    public class CustomSignUpRequestDto extends SignUpRequestDto {

        @NotBlank
        private final String currency;

        public CustomSignUpRequestDto(
                String name,
                String surname,
                String email,
                String password,
                String language,
                String serverSecretKey,
                String currency
        ) {
            super(name, surname, email, password, language, serverSecretKey);
            this.currency = currency;
        }

        public String getCurrency() {
            return currency;
        }
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.fasterxml.jackson.annotation.JsonDeserializeAs
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto
    import jakarta.validation.constraints.NotBlank
    import org.springframework.boot.jackson.JacksonMixin

    @DtoRequest
    @JacksonMixin(SignUpRequestDto::class)
    @JsonDeserializeAs(CustomSignUpRequestDto::class)
    class CustomSignUpRequestDto(
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        serverSecretKey: String,
        currency: String
    ) : SignUpRequestDto(name, surname, email, password, language, serverSecretKey) {

        @field:NotBlank
        val currency: String = currency

    }
    ```

The sign-up payload includes the custom `currency` value:

```json
{
  "name": "Jane",
  "surname": "Doe",
  "email": "jane@example.com",
  "password": "your-valid-password",
  "language": "en",
  "serverSecretKey": "your-server-secret",
  "currency": "EUR"
}
```

The inherited endpoint validates both standard and custom fields.
`serverSecretKey` is the server secret configured for the application

For details about constructor discovery, see the
[Jackson Java parameter-name support](https://github.com/FasterXML/jackson-databind/blob/3.x/src/main/java/tools/jackson/databind/MapperFeature.java)
and the [Jackson Kotlin module](https://github.com/FasterXML/jackson-module-kotlin)

###### Construct the custom user

The `resolveSignUpUser` override creates `CustomUser` from the request and the already hashed `safePassword`

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto;
    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
    import org.springframework.stereotype.Service;

    @Service
    public class CustomUsersService extends EquinoxUsersService<CustomUser, CustomUsersRepository> {

        public CustomUsersService(CustomUsersRepository usersRepository) {
            super(usersRepository);
        }

        @Override
        protected CustomUser resolveSignUpUser(
                String userId,
                String userToken,
                String safePassword,
                SignUpRequestDto signUpRequest
        ) {
            CustomSignUpRequestDto request = (CustomSignUpRequestDto) signUpRequest;

            return new CustomUser(
                    userId,
                    userToken,
                    request.getName(),
                    request.getSurname(),
                    request.getEmail(),
                    safePassword,
                    request.getLanguage(),
                    request.getCurrency()
            );
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto
    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService
    import org.springframework.stereotype.Service

    @Service
    class CustomUsersService(
        usersRepository: CustomUsersRepository
    ) : EquinoxUsersService<CustomUser, CustomUsersRepository>(usersRepository) {

        override fun resolveSignUpUser(
            userId: String,
            userToken: String,
            safePassword: String,
            signUpRequest: SignUpRequestDto
        ): CustomUser {
            val request = signUpRequest as CustomSignUpRequestDto

            return CustomUser(
                userId,
                userToken,
                request.name,
                request.surname,
                request.email,
                safePassword,
                request.language,
                request.currency
            )
        }

    }
    ```

The hook accepts `SignUpRequestDto`, while the concrete request exposes `currency`.
The repository persists the returned `CustomUser`, including its mapped fields

###### Customize the sign-up response

`SignUpResponseDto` carries the registration result. Its subclasses expose additional response fields
through getters; this example includes `currency`

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignUpResponseDto;

    @DtoResponse
    public class CustomSignUpResponseDto extends SignUpResponseDto {

        private final String currency;

        public CustomSignUpResponseDto(
                String userId, String userToken, String profilePic, String currency
        ) {
            super(userId, userToken, profilePic);
            this.currency = currency;
        }

        public String getCurrency() {
            return currency;
        }
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignUpResponseDto

    @DtoResponse
    class CustomSignUpResponseDto(
        userId: String,
        userToken: String,
        profilePic: String,
        val currency: String
    ) : SignUpResponseDto(userId, userToken, profilePic)
    ```

`resolveSignUpResponse` selects the response DTO. The original request remains available to provide
custom response values such as `currency`

=== "Java"

    ```java
    @Override
    protected CustomSignUpResponseDto resolveSignUpResponse(
            String userId,
            String userToken,
            SignUpRequestDto signUpRequest
    ) {
        CustomSignUpRequestDto request = (CustomSignUpRequestDto) signUpRequest;
        SignUpResponseDto base = super.resolveSignUpResponse(userId, userToken, signUpRequest);

        return new CustomSignUpResponseDto(
                base.getUserId(),
                base.getUserToken(),
                base.getProfilePic(),
                request.getCurrency()
        );
    }
    ```

=== "Kotlin"

    ```kotlin
    override fun resolveSignUpResponse(
        userId: String,
        userToken: String,
        signUpRequest: SignUpRequestDto
    ): CustomSignUpResponseDto {
        val request = signUpRequest as CustomSignUpRequestDto
        val base = super.resolveSignUpResponse(userId, userToken, signUpRequest)

        return CustomSignUpResponseDto(
            base.userId,
            base.userToken,
            base.profilePic,
            request.currency
        )
    }
    ```

The inherited controller includes the DTO in the success response. No controller override is required

The deprecated `getSignUpKeys`, `getSignUpCustomParams`, and `validateSignUp` hooks are outside this flow

#### Custom sign-in

In this example, the custom `currency` field is checked during sign-in and included in the response

###### Define the custom request DTO

The request extends `SignInRequestDto` with `currency`. The Jackson mapping follows the
[sign-up example](#define-the-custom-request-dto), including its Spring Boot and Jackson prerequisites

=== "Java"

    ```java
    package other.packages;

    import com.fasterxml.jackson.annotation.JsonDeserializeAs;
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto;
    import jakarta.validation.constraints.NotBlank;
    import org.springframework.boot.jackson.JacksonMixin;

    @DtoRequest
    @JacksonMixin(SignInRequestDto.class)
    @JsonDeserializeAs(CustomSignInRequestDto.class)
    public class CustomSignInRequestDto extends SignInRequestDto {

        @NotBlank
        protected String currency;

        public CustomSignInRequestDto() {
        }

        public String getCurrency() {
            return currency;
        }
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.fasterxml.jackson.annotation.JsonDeserializeAs
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto
    import jakarta.validation.constraints.NotBlank
    import org.springframework.boot.jackson.JacksonMixin

    @DtoRequest
    @JacksonMixin(SignInRequestDto::class)
    @JsonDeserializeAs(CustomSignInRequestDto::class)
    class CustomSignInRequestDto : SignInRequestDto() {

        @field:NotBlank
        var currency: String = ""
    }
    ```

The request payload includes the custom field:

```json
{
  "email": "jane@example.com",
  "password": "ExamplePassword123!",
  "language": "en",
  "currency": "EUR"
}
```

###### Validate the custom sign-in request

In [CustomUsersService](#creating-the-dedicated-service), the standard credential check is combined
with a comparison of the requested and saved currencies. `safePassword` is already hashed

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto;
    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
    import org.springframework.stereotype.Service;

    @Service
    public class CustomUsersService extends EquinoxUsersService<CustomUser, CustomUsersRepository> {

        public CustomUsersService(CustomUsersRepository usersRepository) {
            super(usersRepository);
        }

        @Override
        protected boolean validateSignIn(
                CustomUser user,
                String safePassword,
                SignInRequestDto signInRequest
        ) {
            CustomSignInRequestDto request = (CustomSignInRequestDto) signInRequest;

            return super.validateSignIn(user, safePassword, signInRequest)
                    && java.util.Objects.equals(user.getCurrency(), request.getCurrency());
        }
    }

    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto
    import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService
    import org.springframework.stereotype.Service

    @Service
    class CustomUsersService(
        usersRepository: CustomUsersRepository
    ) : EquinoxUsersService<CustomUser, CustomUsersRepository>(usersRepository) {

        override fun validateSignIn(
            user: CustomUser?,
            safePassword: String,
            signInRequest: SignInRequestDto
        ): Boolean {
            val request = signInRequest as CustomSignInRequestDto

            return super.validateSignIn(user, safePassword, signInRequest) &&
                user?.currency == request.currency
        }
    }

    ```

###### Customize the sign-in response

The response extends `SignInResponseDto` to include the currency saved with the authenticated user

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignInResponseDto;

    public class CustomSignInResponseDto extends SignInResponseDto {

        private final String currency;

        public CustomSignInResponseDto(CustomUser user) {
            super(
                    user.getId(),
                    user.getToken(),
                    user.getProfilePic(),
                    user.getName(),
                    user.getSurname(),
                    user.getLanguage()
            );
            currency = user.getCurrency();
        }

        public String getCurrency() {
            return currency;
        }
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignInResponseDto

    class CustomSignInResponseDto(user: CustomUser) : SignInResponseDto(
        user.id,
        user.token,
        user.profilePic,
        user.name,
        user.surname,
        user.language
    ) {
        val currency: String = user.currency
    }
    ```

The `resolveSignInResponse` override in `CustomUsersService` returns the custom DTO:

=== "Java"

    ```java
    @Override
    protected CustomSignInResponseDto resolveSignInResponse(CustomUser user) {
        return new CustomSignInResponseDto(user);
    }
    ```

=== "Kotlin"

    ```kotlin
    override fun resolveSignInResponse(user: CustomUser): CustomSignInResponseDto {
        return CustomSignInResponseDto(user)
    }
    ```

The inherited controller includes the DTO in the success response. No controller override is required

#### Custom dynamic account data

In this example, the saved `currency` is included in the account data used to synchronize the user's devices

The inherited endpoint returns the authenticated user's email, profile pic, and language.
[IsAuthorizedUser](Security/authorization/annotations/IsAuthorizedUser.md) checks that the requested user identifier
matches the authenticated user

###### Customize the dynamic account data response

The response extends `DynamicAccountDataResponseDto` to include the currency saved with
[CustomUser](#creating-a-customuser)

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.DynamicAccountDataResponseDto;

    @DtoResponse
    public class CustomDynamicAccountDataResponseDto extends DynamicAccountDataResponseDto {

        private final String currency;

        public CustomDynamicAccountDataResponseDto(CustomUser user) {
            super(
                    user.getEmail(),
                    user.getProfilePic(),
                    user.getLanguage()
            );
            currency = user.getCurrency();
        }

        public String getCurrency() {
            return currency;
        }
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse
    import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.DynamicAccountDataResponseDto

    @DtoResponse
    class CustomDynamicAccountDataResponseDto(user: CustomUser) : DynamicAccountDataResponseDto(
        user.email,
        user.profilePic,
        user.language
    ) {
        val currency: String = user.currency
    }
    ```

The `getDynamicAccountData` override in [CustomUsersService](#creating-the-dedicated-service) returns the custom DTO:

=== "Java"

    ```java
    @Override
    public CustomDynamicAccountDataResponseDto getDynamicAccountData(CustomUser user) {
        return new CustomDynamicAccountDataResponseDto(user);
    }
    ```

=== "Kotlin"

    ```kotlin
    override fun getDynamicAccountData(user: CustomUser): CustomDynamicAccountDataResponseDto {
        return CustomDynamicAccountDataResponseDto(user)
    }
    ```

The inherited controller includes the DTO in the success response. No controller override is required

### Customizing the messages resource bundle

You can create your own resource set to internationalize your backend by defining it as follows:

``` bash 
resources
  └── lang
       └── custom_messages.properties
       └── custom_messages_any_locale.properties
       └── ...
```
