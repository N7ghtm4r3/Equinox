## EquinoxEnvironment

This is a group of classes to create a SpringBoot's environment based on the Equinox system.

The base environment gives a base set of classes:

- [EquinoxBaseEndpointsSet](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/EquinoxBaseEndpointsSet.java) ->
  set of endpoints already created for the **EquinoxUsersController** methods
- [EquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/controllers/EquinoxController.java) ->
  to manage the other **RestController** of the backend
- [EquinoxItem](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/records/EquinoxItem.java) ->
  base class for the items used by an Equinox backend based
- User utilities set
  - [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/records/EquinoxUser.java) ->
  standard Equinox's user
  - [EquinoxLocalUser](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/records/EquinoxLocalUser.java) ->
  local user helper, useful to manage the local session of a user in the clients applications
  - [EquinoxUsersController](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/controllers/EquinoxUsersController.java) ->
  controller to manage the operations on the **EquinoxUser**
  - [EquinoxUsersHelper](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java) ->
  helper used by the **EquinoxUsersController** to interact with the database
  - [EquinoxUsersRepository](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/repositories/EquinoxUsersRepository.java) ->
  the **JpaRepository** to make the related queries to the database
- [EquinoxRequester](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/kotlin/com/tecknobit/equinox/environment/helpers/EquinoxRequester.kt) ->
  the requester helper with the **EquinoxUser** requests pre-implemented to execute the operations on the user
- [InputValidator](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/inputs/InputValidator.java) ->
  utility class to validate the inputs, gives a set of the method to validate the **EquinoxUser** details

### Usage/Examples

To correctly launch the base environment _out-of-the-box_ you can simply follow the following snippet of code
representing the main of a  **SpringApplication**:

```java
package other.packages...

import com.tecknobit.equinox.environment.controllers.EquinoxController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages..."}
)
@EntityScan(
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages..."}
)
@ComponentScan(
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages..."}
)
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {

        // used to init the server protector to manage the server accesses
        // if it is not done will be thrown a RuntimeException
        // required if you create your own RestController extending it from EquinoxController
        EquinoxController.initEquinoxEnvironment(
                "the path where storage the server secret",
                "the message to print when the server secret has been generated",
                Launcher.class,
                args);

        // ... your code ...

        // normally launch your SpringBoot's application
        SpringApplication.run(Launcher.class, args);

    }

    // to check the current mapped endpoints you can use this method
    // this also to check if any inherited controllers from EquinoxController you have created are mapped correctly
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        System.out.println("----------- CURRENT ENDPOINTS MAPPED -----------");
        map.forEach((key, value) -> System.out.println("| " + key + value));
        System.out.println("--------------------------------------------");
    }
    
}
```

> [!NOTE]  
> The REQUIRED annotations are necessary to correctly launch automatically the **EquinoxUsersController** and its
> related
> workflow, so if you don't want to use that controller you can simply remove them, and it will not automatically start

## Gradle configuration

You need to use this gradle configuration to correctly use this environment

```gradle
plugins {
    id("java")
    id("org.springframework.boot") version "3.2.3"
}

apply(plugin = "io.spring.dependency-management")

...

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.clojars.org")
}

dependencies {

    ...

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springframework.boot:spring-boot-maven-plugin:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.github.N7ghtm4r3:APIManager:2.2.3")
    implementation("com.github.N7ghtm4r3:Equinox:1.0.1")
    implementation("org.json:json:20231013")

    ...

}

...
    
```

## Customization and inheritance

If you need to customize any classes of the base environment you need to follow this guide to make it works correctly
after your own customization, in this example you need to add the **currency** field to your user and add also the
related
change request.

### Customized the **EquinoxUser**

To add the custom field you need to extend your custom users from the base **EquinoxUser**, the class will be as
the following:

```java
package other.packages...

import com.tecknobit.equinox.environment.records.EquinoxUser;
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
package other.packages...

import com.tecknobit.equinox.environment.helpers.services.repositories.EquinoxUsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tecknobit.equinox.environment.records.EquinoxItem.IDENTIFIER_KEY;
import static com.tecknobit.equinox.environment.records.EquinoxUser.USERS_KEY;

@Service
@Repository
@Primary // this is REQUIRED to use correctly this repository instead the EquinoxUsersRepository
public interface CustomUsersRepository extends EquinoxUsersRepository {

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
package other.packages...

import com.tecknobit.equinox.environment.helpers.services.EquinoxUsersHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // this is REQUIRED to use correctly this helper instead the EquinoxUsersHelper
public class CustomUsersHelper extends EquinoxUsersHelper {

    @Autowired
    private CustomUsersRepository customUsersRepository;

    public void changeCurrency(String newCurrency, String userId) {
        customUsersRepository.changeCurrency(newCurrency, userId);
    }

}
```

### Create the dedicated controller

You need to create the dedicated controller to execute the dedicated requests for your custom user, so, you can extend
the
**EquinoxUsersController** class and add the custom additional methods. The controller will be as the following:

```java
package other.packages...

import com.tecknobit.equinox.environment.controllers.EquinoxUsersController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomUsersController extends EquinoxUsersController {

    private final CustomUsersHelper customUsersHelper;

    // you DON'T have to use this constructor
    /*public CustomUsersController(EquinoxUsersHelper usersHelper) {
        super(usersHelper);
        this.customUsersHelper = usersHelper;
    }*/

    // but override the helper type with your custom helper,
    // so you can use the additional methods you have implemented and also 
    // the base methods already implemented
    public CustomUsersController(CustomUsersHelper usersHelper) {
        super(usersHelper);
        this.customUsersHelper = usersHelper;
    }

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
            path = { "users/{id}/changeCurrency" },
            headers = { TOKEN_KEY }
    )
    public String changeCurrency(
            @PathVariable("id") String id,
            @RequestHeader("token") String token,
            @RequestBody Map<String, String> payload
    ) {
        if(isMe(id, token)) {
            customUsersHelper.changeCurrency(payload.get("currency"), id);
            return successResponse();
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

}
```

### Edit your Launcher class

Instead of the
first [Launcher](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/EquinoxEnvironment.md#usageexamples)
class, you need to customize it to correctly launch the backend

```java
package other.packages...

import com.tecknobit.equinox.environment.controllers.EquinoxController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(
        value = {"other.packages..."}
)
@EntityScan(
        value = {"other.packages..."}
)
@ComponentScan(
        value = {"other.packages..."}
)
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {

        // used to init the server protector to manage the server accesses
        // if it is not done will be thrown a RuntimeException
        // required if you create your own RestController extending it from EquinoxController
        EquinoxController.initEquinoxEnvironment(
                "the path where storage the server secret",
                "the message to print when the server secret has been generated",
                Launcher.class,
                args);

        // ... your code ...

        // normally launch your SpringBoot's application
        SpringApplication.run(Launcher.class, args);

    }
    
}
```

> [!NOTE]  
> The REQUIRED annotations are not more necessary because using the `@Primary` annotations the `com.tecknobit.` package
> is not more useful to search the repositories, entities and components pre-implemented

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

[![](https://jitpack.io/v/N7ghtm4r3/Equinox.svg)](https://jitpack.io/#N7ghtm4r3/Equinox)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                        | Network  |
|-----------------------------------------------------------------------------------------------------|------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**         | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4** | Ethereum |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2024 Tecknobit
