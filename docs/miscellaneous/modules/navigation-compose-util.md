# navigation-compose-util

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/equinoxmisc-navigation-compose-util.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/apple-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

**v1.0.0**

Integration of the [Navigation Compose](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation.html)
library with some useful utilities to simplify the sharing of the data between the destinations during the navigation

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
navigationCompose = "2.9.1"
navigationComposeUtil = "1.0.0"

[libraries]
navigation-compose = { module = "org.jetbrains.androidx.navigation:navigation-compose", version.ref = "navigationCompose" }
navigation-compose-util = { module = "io.github.n7ghtm4r3:equinoxmisc-navigation-compose-util", version.ref = "navigationComposeUtil" }
```

- `build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.navigation.compose.util)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")
    implementation("io.github.n7ghtm4r3:equinoxmisc-navigation-compose-util:1.0.0")
}
```

### Gradle Groovy

```groovy
dependencies {
    implementation 'org.jetbrains.androidx.navigation:navigation-compose:2.9.1'
    implementation 'io.github.n7ghtm4r3:equinoxmisc-navigation-compose-util:1.0.0'
}
```

## NavHostController util

### Usage

#### Navigating with data

With the `navWithData` method is possible to navigate to destinations and attach data that those destinations will use when 
visible (on **top** of the stack)

```kotlin
val navigator: NavHostController // an instance of the NavHostController

// attach data during the navigation
navigator.navWithData(
    route = "Home",
    data = buildMap {
        put("selectedTabTitle", "Settings")
    }
)
```

!!! Note

    In this example the destination has the `String` type, but this method has been overloaded 
    with all the supported types by the original Jetbrains API such as `NavUri`, `NavDeepLinkRequest`, etc...

#### Retrieving navigation data

The library provides two API to retrieve the navigation data attached to the current destination: `getDestinationNavData`
and `getAllDestinationNavData`

###### getDestinationNavData

```kotlin
@Composable
fun App() {
    // an instance of the NavHostController
    val navigator: NavHostController = rememberNavController()
    NavHost(
        navController = navigator,
        startDestination = "Splashscreen"
    ) {
        composable(
            route = "Splashscreen"
        ) {
            val splashscreen = equinoxScreen { Splashscreen() }
            splashscreen.ShowContent()
        }
        composable(
            route = "Home"
        ) {
            val selectedTabTitle: String? = navigator.getDestinationNavData(
                key = "selectedTabTitle",
                defaultValue = // custom value if no data has been shared with that key
            )
            val home = equinoxScreen() {
                Home(
                    selectedTabTitle = selectedTabTitle
                )
            }
            home.ShowContent()
        }
    }
}
```

###### getAllDestinationNavData

```kotlin
@Composable
fun App() {
    // an instance of the NavHostController
    val navigator: NavHostController = rememberNavController()
    NavHost(
        navController = navigator,
        startDestination = "Splashscreen"
    ) {
        composable(
            route = "Splashscreen"
        ) {
            val splashscreen = equinoxScreen { Splashscreen() }
            splashscreen.ShowContent()
        }
        composable(
            route = "Home"
        ) {
            val navData: Map<String, Any?> = navigator.getAllDestinationNavData()
            // your logic to use the navData
        }
    }
}
```

!!! Note

    The `all destination data` means just to the data shared with the visible destination

## SavedStateHandle util

### Usage