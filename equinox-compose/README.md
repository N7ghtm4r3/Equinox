# Equinox Compose

**v1.0.7**

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

Utilities for **Compose Multiplatform** applications such components, apis for all supported platforms

## Temporarily workaround

Because conflicting dependencies due the `Equinox-Compose` migration as module, at the moment, to use correctly this
library instead the old one you need to exclude it from you configuration if you're targeting `android`'s target, add
this
in your `build.gradle.kts` file:

```kotlin
configurations.all {
  exclude("io.github.n7ghtm4r3", "Equinox-Compose-android")
}
```

## Implementation

### Version catalog

- `libs.version.toml`

```gradle
[versions]
equinox = "1.0.7"

[libraries]
equinox-compose = { module = "io.github.n7ghtm4r3:equinox-compose", version.ref = "equinox" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
```

- `build.gradle.kts`

```gradle
dependencies {

    // implement the compose utilities
    implementation(libs.equinox.compose)

    // implement the core utilities
    implementation(libs.equinox.core)
}
```

### Gradle (Kotlin)

```gradle
dependencies {
    
    // implement the compose utilities
    implementation("io.github.n7ghtm4r3:equinox-compose:1.0.7")
    
    // implement the core utilities
    implementation("io.github.n7ghtm4r3:equinox-core:1.0.7")
}
```

### Gradle Groovy

```gradle
dependencies {
   
    // implement the compose utilities
    implementation 'io.github.n7ghtm4r3:equinox-compose:1.0.7'
    
    // implement the core utilities
    implementation 'io.github.n7ghtm4r3:equinox-core:1.0.7'
}
```

## APIs available
- [EquinoxViewModel](../documd/compose/apis/EquinoxViewModel.md)
    - [EquinoxAuthViewModel](src/commonMain/kotlin/com/tecknobit/equinoxcompose/helpers/viewmodels/EquinoxAuthViewModel.kt) ->
      prebuilt viewmodel to authenticate the user in the system
    - [EquinoxProfileViewModel](src/commonMain/kotlin/com/tecknobit/equinoxcompose/helpers/viewmodels/EquinoxProfileViewModel.kt) ->
      prebuilt viewmodel to manage the user account settings and preferences
- [SessionManager](../documd/compose/apis/SessionManager.md)
- [EquinoxScreens](../documd/compose/apis/EquinoxScreens.md)

The other apis will be gradually released

## Components available

- [EquinoxDialogs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxDialogs.kt)
- [EquinoxInputs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxInputs.kt)
- [EquinoxUIs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxUIs.kt)
- [TextDivider](../documd/compose/components/textdivider/TextDivider.md)
- [WriteableText](../documd/compose/components/writeabletext/WriteableText.md)
- [Tile](../documd/compose/components/tile/Tile.md)
- [SplitText](../documd/compose/components/splittext/SplitText.md)
- [ExpandableText](../documd/compose/components/expandabletext/ExpandableText.md)
- [TabSelector](../documd/compose/components/tabselector/TabSelector.md)
- [ChameleonText](../documd/compose/components/chameleontext/ChameleonText.md)
- [Stepper](../documd/compose/components/stepper/Stepper.md)

The others components will be gradually released



## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
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

Copyright © 2025 Tecknobit
