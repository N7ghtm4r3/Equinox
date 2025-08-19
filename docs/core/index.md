# Overview

**v1.1.5**

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/backend-7d7d7d?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

Core utilities for **Compose Multiplatform** and **Spring** technologies

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
equinox = "1.1.5"

[libraries]
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox" }
```

- `build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.equinox.core)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("io.github.n7ghtm4r3:equinox-core:1.1.5")
}
```

### Gradle Groovy

```groovy
dependencies {
    implementation 'io.github.n7ghtm4r3:equinox-core:1.1.5'
}
```

## APIs available

- [Timeformatter](APIs/Timeformatter.md) allows you to format the temporal values and calculate temporal gap
- [Requester](APIs/Requester.md) allows you to communicate with your backends providing the basic methods to
  build
  your own customized requester following the **Equinox**'s philosophy
- [Retriever](APIs/Retriever.md) allows you to handle repetitive retrieving routines and execute them in
  background
