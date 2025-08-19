## Summary

This group of classes is useful for creating a Spring Boot environment based on the Equinox system.
The base environment provides a core set of classes to manage users, controllers, and database operations.

### Main Classes

- [EquinoxBaseEndpointsSet](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-core/src/commonMain/kotlin/com/tecknobit/equinoxcore/network/EquinoxBaseEndpointsSet.kt)  
  A set of pre-defined endpoints for EquinoxUsersController endpoints

- [EquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/controller/EquinoxController.java)  
  Manages other RestControllers in the backend

- [DefaultEquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/DefaultEquinoxController.java)  
  Manages other RestControllers with default usage of
  the [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)
  class

- [EquinoxItem](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/entity/EquinoxItem.java)  
  Base class for items used by an Equinox backend

### User Utilities

- [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)  
  Standard Equinox user class

- [EquinoxLocalUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/session/EquinoxLocalUser.kt)  
  Helper for managing a local user session in client applications

- [EquinoxUsersController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/controller/EquinoxUsersController.java)  
  Controller for operations on EquinoxUser objects

- [EquinoxUsersService](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/service/EquinoxUsersService.java)  
  Helper used by EquinoxUsersController to interact with the database

- [EquinoxUsersRepository](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/repository/EquinoxUsersRepository.java)  
  JpaRepository for executing user-related database queries

### Other Utilities

- [EquinoxRequester](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/kotlin/com/tecknobit/equinoxbackend/environment/helpers/EquinoxRequester.kt)  
  Request helper with
  pre-implemented [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)
  requests

- [InputsValidator](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-core/src/commonMain/kotlin/com/tecknobit/equinoxcore/helpers/InputsValidator.kt)  
  Utility class for input validation,
  including [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/entity/EquinoxUser.java)
  details

- [EquinoxItemsHelper](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/service/EquinoxItemsHelper.java)  
  Helper for database operations
  on [EquinoxItem](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/entity/EquinoxItem.java),
  including batch queries

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
        maven { url 'https://repo.clojars.org' }
    }
    
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-web:3.2.3'
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa:3.2.3'
        implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.4'
        implementation 'mysql:mysql-connector-java:8.0.33'
        implementation 'com.github.N7ghtm4r3:APIManager:2.2.4'
        implementation 'org.json:json:20250517'
    
        // implement the backend utilities
        implementation 'io.github.n7ghtm4r3:equinox-backend:1.1.4'
    
        // implement the core utilities
        implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'

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
        maven("https://repo.clojars.org")
    }
    
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
        implementation("mysql:mysql-connector-java:8.0.33")
        implementation("com.github.N7ghtm4r3:APIManager:2.2.4")
        implementation("org.json:json:20250517")
    
        // implement the backend utilities
        implementation("io.github.n7ghtm4r3:equinox-backend:1.1.4")
        
        // implement the core utilities
        implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
    
        ...
    }
    ```

### Out-of-the-Box Launch

To launch the out-of-the-box environment, simply follow the snippet below showing the main method of a *
*SpringApplication**

=== "Java"

    ```java
    package other.packages;

    import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.domain.EntityScan;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.ComponentScan;
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
        }
    )
    @SpringBootApplication
    public class Launcher {
    
        public static void main(String[] args) {
            EquinoxController.initEquinoxEnvironment(Launcher.class, args);
            // ... your code ...
            // then normally launch your SpringBoot's application
            SpringApplication.run(Launcher.class, args);
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    package other.packages

    import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController
    import org.springframework.boot.SpringApplication
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.autoconfigure.domain.EntityScan
    import org.springframework.context.ApplicationContext
    import org.springframework.context.event.ContextRefreshedEvent
    import org.springframework.context.event.EventListener
    import org.springframework.context.annotation.ComponentScan
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
        ]
    )
    @SpringBootApplication
    class Launcher {
    
        companion object {
            @JvmStatic
            fun main(args: Array<String>) {
                EquinoxController.initEquinoxEnvironment(
                    Launcher::class.java,
                    args
                )
                // ... your code ...
                // then normally launch your SpringBoot's application
                SpringApplication.run(Launcher::class.java, *args)
            }
        }

    }
    ```

!!! note

     The REQUIRED annotations are necessary to correctly launch automatically the **EquinoxUsersController** and its
     related workflow, so if you don't want to use that controller you can simply remove them, and it will not automatically start

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
  }
}
```

### Customize the **EquinoxUser**

In this example, you will add a `currency` field to your user and include the corresponding update request
To add the custom field you need to extend your custom users from the base **EquinoxUser**, the class will be as
the following:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.json.JSONObject;

@Entity
public class CustomUser extends EquinoxUser {

  @Column
  private final String currency;

  public CustomUser(String currency) {
    this.currency = currency;
  }

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

  public CustomUser(JSONObject jUser, String currency) {
    super(jUser);
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }

}
```

Will be created a single `users` table in the database with the `dtype` field to use as discriminator value to
distinguish
each classes, but it does not affect to the previous queries implemented for the **EquinoxUser** class

### Create the dedicated JpaRepository

You need to create the dedicated repository to work with your custom user, so, you can extend the *
*EquinoxUsersRepository**
interface and add the custom additional queries. The interface will be as the following:

```java
package other.packages

...

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
  @Transactional
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

### Create the dedicated helper

You need to create the dedicated helper to execute the dedicated operations for your custom user, so, you can extend the
**EquinoxUsersHelper** class and add the custom additional methods. The helper will be as the following:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersHelper extends EquinoxUsersHelper<CustomUser, CustomUsersRepository> {

    public void changeCurrency(String newCurrency, String userId) {
        usersRepository.changeCurrency(newCurrency, userId);
    }

}
```

### Create the dedicated controller

You need to create the dedicated controller to execute the dedicated requests for your custom user, so, you can extend
the
**EquinoxUsersController** class and add the custom additional methods. The controller will be as the following:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

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
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

}
```

You can also create your own default controller
as [DefaultEquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/controllers/DefaultEquinoxController.java)
with your own CustomUser instead:

```java
public abstract class DefaultMyOwnController extends EquinoxController<CustomUser, CustomUsersRepository, CustomUsersHelper> {
}
```

### Custom sign-up

To execute a custom sign-up operation you must follow these steps to perform it correctly

#### Override getSignUpKeys() method

To create the correct insertion query with your custom parameters of the [CustomUser](#customize-the-equinoxuser) you
have to override the below method, and you have to add the keys of the custom properties you want to insert with the
sign-up
operation

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersHelper extends EquinoxUsersHelper<CustomUser, CustomUsersRepository> {

    @Override
    protected List<String> getSignUpKeys() {
      ArrayList<String> custom = new ArrayList<>(super.getSignUpKeys());
        custom.add("currency");
        return custom;
    }

}
```

#### Get from the payload the custom parameters

Now you need to get your custom parameters from the sign-up request, to do it you must override the below method from
the
[CustomUsersController](#create-the-dedicated-controller):

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

    @Override
    @CustomParametersOrder(order = {"currency"}) // optional
    protected Object[] getSignUpCustomParams() {
        return new Object[]{jsonHelper.getString("currency", null), /* other parameters */};
    }

}
```

#### Custom parameters validation

You can also validate your custom parameters if needed:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

    @Override
    @CustomParametersOrder(order = {"currency"}) // optional
    protected String validateSignUp(String name, String surname, String email, String password, String language, Object... custom) {
        String validation = super.validateSignUp(name, surname, email, password, language, custom);
        if (validation != null)
            return validation;
        if (custom[0] == null)
            return "wrong_currency_key";
        return null;
    }

}
```

### Custom sign-in

To execute a custom sign-in operation you must follow these steps to perform it correctly

#### Override validateSignIn() method

To execute the sign-in validation with your custom parameters of the [CustomUser](#customize-the-equinoxuser) you
have to override the below method, and perform the custom checks to validate the sign-in operation

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersHelper extends EquinoxUsersHelper<CustomUser, CustomUsersRepository> {

    @Override
    @CustomParametersOrder(order = {"currency"}) // optional
    protected boolean validateSignIn(CustomUser user, String password, Object... custom) throws NoSuchAlgorithmException {
        return super.validateSignIn(user, password, custom) && user.getCurrency().equals(custom[0]);
    }

}
```

#### Get from the payload the custom parameters

Now you need to get your custom parameters from the sign-in request, to do it you must override the below method from
the
[CustomUsersController](#create-the-dedicated-controller):

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

    @Override
    @CustomParametersOrder(order = {"currency"}) // optional
    protected Object[] getSignInCustomParams() {
        return new Object[]{jsonHelper.getString("currency", null)};
    }

}
```

#### Custom parameters validation

You can also validate your custom parameters if needed:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

    @Override
    @CustomParametersOrder(order = {"currency"}) // optional
    protected String validateSignIn(String email, String password, String language, Object... custom) {
        String validation = super.validateSignIn(email, password, language, custom);
        if (validation != null)
            return validation;
        if (custom[0] == null)
            return "wrong_currency_key";
        return null;
    }

}
```

#### Customize the sign-in response

If you need to add your custom parameters to the sign-in response you can do that overriding the below method:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.users.controller.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomUsersController extends EquinoxUsersController<CustomUser, CustomUsersRepository, CustomUsersHelper> {

    @Override
    protected JSONObject assembleSignInSuccessResponse(CustomUser user) {
        JSONObject response = super.assembleSignInSuccessResponse(user);
        response.put("currency", user.getCurrency());
        return response;
    }

}
```

### Custom messages resource bundle

You can create your own resources set to internationalize your backend creating the set as follows:

``` bash 
resources
  └── lang
       └── custom_messages.properties
       └── custom_messages_any_locale.properties
       └── ...
```