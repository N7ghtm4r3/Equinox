# lazy-pagination-compose-ops

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/equinoxmisc-lazy-pagination-compose-ops.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

**v1.0.1**

Integration of the [lazy-pagination-compose](https://github.com/Ahmad-Hamwi/lazy-pagination-compose) library with the
[in-place operations ](#usage)

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
lazyPaginationCompose = "1.7.0"
lazyPaginationComposeOps = "1.0.1"

[libraries]
lazy-pagination-compose = { module = "io.github.ahmad-hamwi:lazy-pagination-compose", version.ref = "lazyPaginationCompose" }
lazy-pagination-compose-ops = { module = "io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops", version.ref = "lazyPaginationComposeOps" }
```

- `build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.lazy.pagination.compose)
    implementation(libs.lazy.pagination.compose.ops)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("io.github.ahmad-hamwi:lazy-pagination-compose:1.7.0")
    implementation("io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops:1.0.1")
}
```

### Gradle Groovy

```groovy
dependencies {
    implementation 'io.github.ahmad-hamwi:lazy-pagination-compose:1.7.0'
    implementation 'io.github.n7ghtm4r3:equinoxmisc-lazy-pagination-compose-ops:1.0.1'
}
```

## Usage

| Method                | Description                                                                                                                                                                                                                                                                                             |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `addInPlace`          | Add in-place a new item to the current `PaginationState.allItems` list                                                                                                                                                                                                                                  |
| `addAllInPlace`       | Add in-place a collection of items to the current `PaginationState.allItems` list                                                                                                                                                                                                                       |
| `removeInPlace`       | Remove in-place an item from the current `PaginationState.allItems` list                                                                                                                                                                                                                                |
| `removeAllInPlace`    | Remove in-place a collection of items from the current `PaginationState.allItems` list                                                                                                                                                                                                                  |
| `updateInPlace`       | Update in-place the current `PaginationState.allItems` list                                                                                                                                                                                                                                             |
| `retainAndAllInPlace` | Split in two phases: first remove all the items from the current `PaginationState.allItems` that are not present in the `items` collection, then add all the items present in the same items collection but not in the main one. Duplicate values are considered as one element, so they will be merged |
| `mergeIfNotContained` | Merge items from the `items` collection that are not present in the `PaginationState.allItems`. Duplicate values are considered as one element, so they will be merged                                                                                                                                  |
| `toggle`              | Add in-place an item to the current `PaginationState.allItems` if it is not already contained, or remove it if already contained. Can be used to dynamically insert or remove an element from the collection, e.g., with checkbox selection or button clicking                                          |
