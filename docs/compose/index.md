# Overview

**v1.1.5**

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

Utilities for **Compose Multiplatform** applications such components, APIs for all supported platforms

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
equinox = "1.1.5"

[libraries]
equinox-compose = { module = "io.github.n7ghtm4r3:equinox-compose", version.ref = "equinox" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
```

- `build.gradle.kts`

```kotlin
dependencies {

    // implement the compose utilities
    implementation(libs.equinox.compose)

    // implement the core utilities
    implementation(libs.equinox.core)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    
    // implement the compose utilities
    implementation("io.github.n7ghtm4r3:equinox-compose:1.1.5")
    
    // implement the core utilities
    implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
}
```

### Gradle Groovy

```groovy
dependencies {
   
    // implement the compose utilities
    implementation 'io.github.n7ghtm4r3:equinox-compose:1.1.5'
    
    // implement the core utilities
    implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'
}
```

## APIs available

- [EquinoxViewModel](APIs/EquinoxViewModel.md) provides:
    - **EquinoxAuthViewModel** is a prebuilt viewmodel to authenticate the user in the system
  - **EquinoxProfileViewModel** is a prebuilt viewmodel to manage the user account settings and preferences
- [EquinoxScreens](APIs/EquinoxScreens.md)
- [EquinoxWindowKit](APIs/EquinoxWindowKit.md)

## Components available

- [EquinoxDialogs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxDialogs.kt)
- [EquinoxInputs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxInputs.kt)
- [EquinoxUIs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxUIs.kt)
- [TextDivider](components/textdivider/TextDivider.md)
- [Tile](components/tile/Tile.md)
- [SplitText](components/splittext/SplitText.md)
- [ExpandableText](components/expandabletext/ExpandableText.md)
- [TabSelector](components/tabselector/TabSelector.md)
- [ChameleonText](components/chameleontext/ChameleonText.md)
- [Stepper](components/stepper/Stepper.md)
- [DebouncedInputs](components/debouncefields/DebouncedInputs.md)
- [QuantityPicker](components/quantitypicker/QuantityPicker.md)
- [BadgeText](components/badgetext/BadgeText.md)
- [ProgressBars](components/progressbars/ProgressBars.md)
- [SessionFlowContainer](components/sessionflowcontainer/SessionFlowContainer.md)
- [InformativeIcon](components/informativeicon/InformativeIcon.md)