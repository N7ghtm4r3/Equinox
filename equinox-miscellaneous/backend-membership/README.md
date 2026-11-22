# backend-membership

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/equinoxmisc-backend-membership.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/Spring%20Boot-backend-6DB33F)
![Static Badge](https://img.shields.io/badge/JVM-Java%2021-007396)

**v1.0.0**

An optional extension of [Equinox Backend](../../equinox-backend/README.md) for managing users belonging to
projects, groups, organizations, or other resources in **Spring Boot** applications.

The module provides a base membership entity, member DTO, repository and service contracts, and reusable
membership checks. Your application defines the resource mapping and the query used to retrieve its members.

## Implementation

The module requires **Java 21** and the
[Equinox Backend environment](../../docs/backend/Environment.md).
Include the backend and core dependencies, whose types appear in the membership APIs.

### Version catalog

- `libs.versions.toml`

```toml
[versions]
equinoxBackendMembership = "1.0.0"
equinoxBackend = "2.0.0"
equinoxCore = "1.2.1"

[libraries]
equinox-backend-membership = { module = "io.github.n7ghtm4r3:equinoxmisc-backend-membership", version.ref = "equinoxBackendMembership" }
equinox-backend = { module = "io.github.n7ghtm4r3:equinox-backend", version.ref = "equinoxBackend" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinoxCore" }
```

- `build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.equinox.backend.membership)
    implementation(libs.equinox.backend)
    implementation(libs.equinox.core)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("io.github.n7ghtm4r3:equinoxmisc-backend-membership:1.0.0")
    implementation("io.github.n7ghtm4r3:equinox-backend:2.0.0")
    implementation("io.github.n7ghtm4r3:equinox-core:1.2.1")
}
```

### Gradle Groovy

```groovy
dependencies {
    implementation 'io.github.n7ghtm4r3:equinoxmisc-backend-membership:1.0.0'
    implementation 'io.github.n7ghtm4r3:equinox-backend:2.0.0'
    implementation 'io.github.n7ghtm4r3:equinox-core:1.2.1'
}
```

Repository configuration is covered by the
[backend installation guide](../../equinox-backend/README.md#implementation).

## Documentation

Check out the module documentation [here!](https://n7ghtm4r3.github.io/Equinox/miscellaneous/modules/backend-membership/)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot) [![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2026 Tecknobit
