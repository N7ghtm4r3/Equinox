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
    - [EquinoxUsersController](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/controllers/EquinoxUsersController.java) ->
      controller to manage the operations on the **EquinoxUser**
    - [EquinoxUsersHelper](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java) ->
      helper used by the **EquinoxUsersController** to interact with the database
    - [EquinoxUsersRepository](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/repositories/EquinoxUsersRepository.java) ->
      the **JpaRepository** to make the related queries to the database
- [InputValidator](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/inputs/InputValidator.java) ->
  utility class to validate the inputs, gives a set of the method to validate the **EquinoxUser** details

### Usage/Examples

To correctly launch the base environment _out-of-the-box_ you can simply follow the following snippet of code
representing
the main of a  **SpringApplication**:

```java
import com.tecknobit.equinox.environment.controllers.EquinoxController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.tecknobit.equinox.resourcesutils.ResourcesProvider.CUSTOM_CONFIGURATION_FILE_PATH;
import static com.tecknobit.equinox.resourcesutils.ResourcesProvider.DEFAULT_CONFIGURATION_FILE_PATH;

@EnableJpaRepositories("com.tecknobit.*") // REQUIRED
@EntityScan("com.tecknobit.*") // REQUIRED
@ComponentScan("com.tecknobit.*") // REQUIRED 
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {

        // used to init the server protector to manage the server accesses
        // if it is not done will be thrown a RuntimeException
        // required if you create your own RestController extending it from EquinoxController
        EquinoxController.initServerProtector(
                "the path where storage the server secret",
                "the message to print when the server secret has been generated",
                args);

        // ... your code ...

        // normally launch your SpringBoot's application
        SpringApplication.run(Launcher.class, args);

    }

}
```

> [!NOTE]  
> The REQUIRED annotations are necessary to correctly launch automatically the **EquinoxUsersController** and its
> related
> workflow, so if you don't want to use that controller you can simply remove them, and it will not automatically start

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
