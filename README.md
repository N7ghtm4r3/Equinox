# Equinox

**v1.0.4**

Utilities for backend services based on Springboot framework. Is a support library to implement some utilities both for backend and for client also who comunicate with that Springboot backend

## Implementation

Add the JitPack repository to your build file

### Gradle

- Add it in your root build.gradle at the end of repositories

  #### Gradle (Short)

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

  #### Gradle (Short)

    ```gradle
    dependencies {
        implementation 'com.github.N7ghtm4r3:Equinox:1.0.4'
    }
    ```

  #### Gradle (Kotlin)

    ```gradle
    dependencies {
        implementation("com.github.N7ghtm4r3:Equinox:1.0.4")
    }
    ```

### Maven

- Add it in your root build.gradle at the end of repositories

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency

```xml
<dependency>
    <groupId>com.github.N7ghtm4r3</groupId>
  <artifactId>Equinox</artifactId>
  <version>1.0.4</version>
</dependency>
```

## 🛠 Skills
- Java
- Kotlin

## APIs available

- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/ResourcesProvider.md">**ResourcesProvider**</a> allows you to manage the static resources to serve to the clients
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/ConfigsGenerator.md">**ConfigsGenerator**</a> allows
  you to create automatically the configuration file for your backend
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/Requester.md">**Requester**</a> allows you to communicate with the backend from the clients
- <a href="https://github.com/N7ghtm4r3/Equinox/blob/main/documd/FetcherManager.md">**FetcherManager**</a> allows you to execute the refresh requests the data to display in the UI
  you to use the resources folder easily

The other apis will be gradually released

## Basic environment available

Equinox gives a basic _out-of-the-box_ environment to directly create a **SpringApplication** following the Equinox
implementation philosophy
to automatically manage some recurring operations such the users related requests or the inputs validation.
This environment will be constantly updated. You can take a
look [here](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/EquinoxEnvironment.md) to check how it works and how
to implement
it on your own backend

## Jetpack Compose version

There is library dedicated to Jetpack Compose clients, take a look [here](https://github.com/N7ghtm4r3/Equinox-Compose)!

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

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

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
