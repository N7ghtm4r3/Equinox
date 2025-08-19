# Overview

**v1.0.3**

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

Responsive navigation systems based on the [EquinoxScreens](../compose/APIs/EquinoxScreens.md) APIs to handle the
navigation in **Compose Multiplatform applications**

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
equinox = "1.1.5"
equinox-navigation = "1.0.3"

[libraries]
equinox-compose = { module = "io.github.n7ghtm4r3:equinox-compose", version.ref = "equinox" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
equinox-navigation = { module = "io.github.n7ghtm4r3:equinox-navigation", version.ref = "equinox-navigation" }
```

- `build.gradle.kts`

```kotlin
dependencies {

    // implement the compose utilities
    implementation(libs.equinox.compose)

    // implement the core utilities
    implementation(libs.equinox.core)

    // implement the navigation utilities
    implementation(libs.equinox.navigation)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    
    // implement the compose utilities
    implementation("io.github.n7ghtm4r3:equinox-compose:1.1.5")
    
    // implement the core utilities
    implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
    
    // implement the navigation utilities
    implementation("io.github.n7ghtm4r3:equinox-navigation:1.0.3")
}
```

### Gradle Groovy

```groovy
dependencies {
   
    // implement the compose utilities
    implementation 'io.github.n7ghtm4r3:equinox-compose:1.1.5'
    
    // implement the core utilities
    implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'
    
    // implement the navigation utilities
    implementation 'io.github.n7ghtm4r3:equinox-navigation:1.0.3'
}
```

## APIs available

- [NavigatorScreen](APIs/NavigatorScreen.md) is a built-in responsive navigation system fully customizable as needed

The other apis will be gradually released