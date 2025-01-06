# Equinox-Compose

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/Equinox-Compose.svg?label=Maven/0Central)

## This library will be integrated as module in the [Equinox](https://github.com/N7ghtm4r3/Equinox) library in the next version

**v1.0.3**

Utilities for clients with an architecture based on SpringBoot and Jetpack Compose frameworks. Is a support library
to implement some utilities for the clients and some default composable such OutlinedTextField, AlertDialogs and
different others. Based on the main library [Equinox](https://github.com/N7ghtm4r3/Equinox)

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
        implementation 'io.github.n7ghtm4r3:Equinox:1.0.4'
        implementation 'com.github.N7ghtm4r3:APIManager:2.2.4'
        implementation 'com.github.N7ghtm4r3:Equinox-Compose:1.0.3' {
            // you need to exclude the Android library artifacts on a not-Android environment
            exclude("io.github.n7ghtm4r3:Equinox-Compose", "library-android")

            // or
    
            // you need to exclude the JVM library artifacts on an Android environment
            exclude("io.github.n7ghtm4r3:Equinox-Compose", "library-jvm")
        }
    }
    ```

  #### Gradle (Kotlin)

    ```gradle
    dependencies {
        implementation("io.github.n7ghtm4r3:Equinox:1.0.4")
        implementation("com.github.N7ghtm4r3:APIManager:2.2.4")
        implementation("io.github.n7ghtm4r3:Equinox-Compose:1.0.3") {
            // you need to exclude the Android library artifacts on a not-Android environment
            exclude("io.github.n7ghtm4r3:Equinox-Compose", "library-android")

            // or
    
            // you need to exclude the JVM library artifacts on an Android environment
            exclude("io.github.n7ghtm4r3:Equinox-Compose", "library-jvm")
        }
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

- Add the dependencies

```xml

<dependency>
    <groupId>io.github.n7ghtm4r3</groupId>
    <artifactId>Equinox</artifactId>
    <version>1.0.4</version>
</dependency>
```

```xml

<dependency>
    <groupId>com.github.N7ghtm4r3</groupId>
    <artifactId>APIManager</artifactId>
    <version>2.2.3</version>
</dependency>
```

```xml

<dependency>
    <groupId>io.github.n7ghtm4r3</groupId>
    <artifactId>Equinox-Compose</artifactId>
    <version>1.0.3</version>
</dependency>
```

## 🛠 Skills

- Java
- Kotlin

## APIs available

- [EquinoxViewModel](../documd/compose/apis/EquinoxViewModel.md)
    - [EquinoxAuthViewModel](library/src/commonMain/kotlin/com/tecknobit/equinoxcompose/helpers/viewmodels/EquinoxAuthViewModel.kt) ->
      prebuilt viewmodel to authenticate the user in the system
    - [EquinoxProfileViewModel](library/src/commonMain/kotlin/com/tecknobit/equinoxcompose/helpers/viewmodels/EquinoxProfileViewModel.kt) ->
      prebuilt viewmodel to manage the user account settings and preferences
- [SessionManager](../documd/compose/apis/SessionManager.md)
- [EquinoxScreen](../documd/compose/apis/EquinoxScreen.md)

The other apis will be gradually released

## Components available

- [EquinoxDialogs](library/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxDialogs.kt)
- [EquinoxInputs](library/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxInputs.kt)
- [EquinoxUIs](library/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxUIs.kt)
- [TextDivider](../documd/compose/components/textdivider/TextDivider.md)
- [WriteableText](../documd/compose/components/writeabletext/WriteableText.md)
- [Tile](../documd/compose/components/tile/Tile.md)
- [SplitText](../documd/compose/components/splittext/SplitText.md)
- [ExpandableText](../documd/compose/components/expandabletext/ExpandableText.md)
- [TabSelector](../documd/compose/components/tabselector/TabSelector.md)
- [ChameleonText](../documd/compose/components/chameleontext/ChameleonText.md)

The others components will be gradually released

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox-Compose/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack/0Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2024 Tecknobit
