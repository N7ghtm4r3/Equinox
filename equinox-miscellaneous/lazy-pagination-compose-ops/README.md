# lazy-pagination-compose-ops

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/equinoxmisc-lazy-pagination-compose-ops.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

**v1.0.1**

Integration of the [lazy-pagination-compose](https://github.com/Ahmad-Hamwi/lazy-pagination-compose) library with the
`in-place`
operations such `addInPlace, removeInPlace`, etc...

## Implementation

### Version catalog

- `libs.version.toml`

```gradle
[versions]
lazyPaginationCompose = "1.7.0"
lazyPaginationComposeOps = "1.0.1"

[libraries]
lazy-pagination-compose = { module = "io.github.ahmad-hamwi:lazy-pagination-compose", version.ref = "lazyPaginationCompose" }
lazy-pagination-compose-ops = { module = "io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops", version.ref = "lazyPaginationComposeOps" }
```

- `build.gradle.kts`

 ```gradle
dependencies {
    implementation(libs.lazy.pagination.compose)
    implementation(libs.lazy.pagination.compose.ops)
}
```

### Gradle (Kotlin)

```gradle
dependencies {
    implementation("io.github.ahmad-hamwi:lazy-pagination-compose:1.7.0")
    implementation("io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops:1.0.1")
}
```

### Gradle Groovy

```gradle
dependencies {
    implementation 'io.github.ahmad-hamwi:lazy-pagination-compose:1.7.0'
    implementation 'io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops:1.0.1'
}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%2Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

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

Copyright © 2025 Tecknobit
