This is a group of classes to create a SpringBoot's environment based on the Equinox system.

The base environment gives a base set of classes:

- [EquinoxBaseEndpointsSet](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/helpers/EquinoxBaseEndpointsSet.java) ->
  set of endpoints already created for the **EquinoxUsersController** methods
- [EquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/controller/EquinoxController.java) ->
  to manage the other **RestController** of the backend
- [DefaultEquinoxController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/DefaultEquinoxController.java) ->
  to manage the other **RestController** of the backend with the default usage of
  the [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/models/EquinoxUser.java)
- [EquinoxItem](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/models/EquinoxItem.java) ->
  base class for the items used by an Equinox backend based
- User utilities set
    - [EquinoxUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/models/EquinoxUser.java) ->
      standard Equinox's user
    - [EquinoxLocalUser](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/models/EquinoxLocalUser.java) ->
      local user helper, useful to manage the local session of a user in the clients applications
    - [EquinoxUsersController](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/controller/EquinoxUsersController.java) ->
      controller to manage the operations on the **EquinoxUser**
    - [EquinoxUsersHelper](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/service/EquinoxUsersHelper.java) ->
      helper used by the **EquinoxUsersController** to interact with the database
    - [EquinoxUsersRepository](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/users/repository/EquinoxUsersRepository.java) ->
      the **JpaRepository** to make the related queries to the database
- [EquinoxRequester](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/kotlin/com/tecknobit/equinoxbackend/environment/helpers/EquinoxRequester.kt) ->
  the requester helper with the **EquinoxUser** requests pre-implemented to execute the operations on the user
- [InputsValidator](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-core/src/commonMain/kotlin/com/tecknobit/equinoxcore/helpers/InputsValidator.kt) ->
  utility class to validate the inputs, gives a set of the method to validate the **EquinoxUser** details
- [EquinoxItemsHelper](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/services/builtin/service/EquinoxItemsHelper.java) ->
  helper for manage the database operations of
  the [EquinoxItem](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-backend/src/main/java/com/tecknobit/equinoxbackend/environment/models/EquinoxItem.java)
  such batch queries execution

### Usage

To correctly launch the base environment _out-of-the-box_ you can simply follow the following snippet of code
representing the main of a  **SpringApplication**:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
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
        value = {"com.tecknobit.*" /* REQUIRED */, "other.packages...",
                "com.tecknobit.equinoxbackend.environment.configuration" /* REQUIRED TO USE THE PROVIDED MESSAGES BUNDLE*/}
)
@SpringBootApplication
public class Launcher {

  public static void main(String[] args) {
    EquinoxController.initEquinoxEnvironment(
            Launcher.class,
            args
    );
    // ... your code ...
    // then normally launch your SpringBoot's application
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

...
    
```

## Customization and inheritance

If you need to customize any classes of the base environment you need to follow this guide to make it works correctly
after your own customization, in this example you need to add the **currency** field to your user and add also the
related
change request.

### Configure your backend

#### Create the config file

To configure your backend instance as needed you have to create the `equinox.config` file and place it as below:

``` bash 
resources
  └── equinox.config
```

#### Fill the configuration

You can copy and fill the below `JSON` as you need:

```json
{
  // omit this to disable the server protector usage
  server_protector: {
    storage_path: "storage_path_of_the_protector",
    save_message: "save_message_value"
  },
  resources: {
    resources_folder: true,
    // false to avoid to create the resources folder 
    subdirectories: [
      // list of subdirectories to create inside the resources folder, omit to avoid the creation
    ]
  }
}
```

### Backend with no database needed

If your architecture does not include a database usage you exclude it as following:

```java
package other.packages

...

import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
@ComponentScan(
        value = "com.tecknobit.equinoxbackend.environment.configuration" /* REQUIRED TO USE THE PROVIDED MESSAGES BUNDLE*/
)
// others basic  SpringBoot's annotations
public class Launcher {

  // Rest of the Launcher class ...

}
```

### Customize the **EquinoxUser**

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