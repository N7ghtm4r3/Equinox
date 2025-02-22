# Equinox Window Kit

These APIs are useful to handle the responsive layouts from specific components creation to assign specific
value based on the
current [window-size-class](https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-170.html#material3-material3-window-size-class)

## Integration

### Responsive content

Using the `ResponsiveContent` method you can handle the different sizes of the screen where your application is running
on:

```kotlin
@Composable
fun App() {
    ResponsiveContent(
        onExpandedSizeClass = {
            ExpandedContent()
        },
        onMediumSizeClass = {
            MediumContent()
        },
        onCompactSizeClass = {
            CompactContent()
        }
    )
}

@Composable
@ExpandedClassComponent // not mandatory, but suggested for a better readability
fun ExpandedContent() {
    // specific content for the expanded screens
}

@Composable
@MediumClassComponent // not mandatory, but suggested for a better readability
fun MediumContent() {
    // specific content for the medium screens
}

@Composable
@CompactClassComponent // not mandatory, but suggested for a better readability
fun CompactContent() {
    // specific content for the compact screens
}
```

### Responsive assignment

Using the `responsiveAssignment` method you can assign a specific value based on the current screen size where your
application
is running on:

```kotlin
@Composable
fun App() {
    val text = responsiveAssignment(
        onExpandedSizeClass = {
            "Hello Expanded World!"
        },
        onMediumSizeClass = {
            "Hello Medium World!"
        },
        onCompactSizeClass = {
            "Hello Compact World!"
        }
    )
    Text(
        text = text
    )
}
```

### Responsive action

Using the `responsiveAction` method you can execute specific (non @Composable) actions based on the current screen size
where your application
is running on

```kotlin
@Composable
fun App() {
    responsiveAction(
        onExpandedSizeClass = {
            println("Hello Expanded World!")
        },
        onMediumSizeClass = {
            println("Hello Medium World!")
        },
        onCompactSizeClass = {
            println("Hello Compact World!")
        }
    )
}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%2Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | 3H3jyCzcRmnxroHthuXh22GXXSmizin2yp               | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | 0x1b45bc41efeb3ed655b078f95086f25fc83345c4       | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit