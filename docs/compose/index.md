# Overview

**v1.1.6**

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
equinox = "1.1.6"

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
    implementation("io.github.n7ghtm4r3:equinox-compose:1.1.6")
    
    // implement the core utilities
    implementation("io.github.n7ghtm4r3:equinox-core:1.1.6")
}
```

### Gradle Groovy

```groovy
dependencies {
   
    // implement the compose utilities
    implementation 'io.github.n7ghtm4r3:equinox-compose:1.1.6'
    
    // implement the core utilities
    implementation 'io.github.n7ghtm4r3:equinox-core:1.1.6'
}
```

## APIs available

- [EquinoxViewModel](APIs/EquinoxViewModel.md) provides:
    - **EquinoxAuthViewModel**  
      Prebuilt viewmodel to authenticate the user in the system

    - **EquinoxProfileViewModel**  
      Prebuilt viewmodel to manage the user account settings and preferences

- [EquinoxScreens](APIs/EquinoxScreens.md)  
  APIs used to create screens with lifecycle management similar to Android activities

- [EquinoxWindowKit](APIs/EquinoxWindowKit.md)  
  APIs useful to handle the responsive layouts

- [EquinoxLocalUser](../compose/APIs/EquinoxLocalUser.md)  
  Helper for managing a local user session in client applications

## Components available

??? texts "Texts"
    - [TextDivider](components/TextDivider.md)  
    Allows to divide sections with a representative text

    - [SplitText](components/SplitText.md)  
      Allows the user to insert a split text such OTP codes, PIN or any other texts of this type
    
    - [ExpandableText](components/ExpandableText.md)  
      Allows to dynamically display a long expanded text initially collapsed
    
    - [ChameleonText](components/ChameleonText.md)  
      Allows to change the text color dynamically based on the background of the container where the text is above
     
    - [BadgeText](components/BadgeText.md)  
      Allows to display a customizable badge in your UI

??? inputs "Inputs"
    - [EquinoxInputs](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxInputs.kt)  
      Wrapper components of the official `TextField` and `OutlinedTextField` provided by `Material` which provides same 
      extra features such input validation or `onValue` callback behavior defined by default
    
    - [DebouncedInputs](components/DebouncedInputs.md)  
      Allow to implement a debounce routine handled when the user stop to typing in the inputs fields

??? feedback "Feedback & Status"
    - [EquinoxDialogs](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxDialogs.kt)  
      Wrapper components of the official `AlertDialog` provided by `Material` with the [EquinoxViewModel](APIs/EquinoxViewModel.md)
      lifecycle handling

    - [EquinoxUIs](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxUIs.kt)   
      Are prebuilt UIs to display a representative scenario like errors, loadings, etc
    
    - [ProgressBars](components/ProgressBars.md)  
      Allow to display progress values on bars

    - [InformativeIcon](components/InformativeIcon.md)  
      Provides the possibility to display an informative text when the user interact with an icon

??? interactive "Interactive"

    - [TabSelector](components/TabSelector.md)  
      Custom tab selector allows to select the tab to display
    
    - [Tile](components/Tile.md)  
      Allows users to quickly understand options and interact with them and can group related actions or information

    - [Stepper](components/Stepper.md)  
      Allows to create a dynamic interaction with the user dividing for example a long procedure such item
      creation, customization, etc... in different specific steps where the user can interact

    - [QuantityPicker](components/QuantityPicker.md)  
      Allows to pick a numerical quantity value

??? containers "Containers"

    - [SessionFlowContainer](components/SessionFlowContainer.md)  
      Allows to dynamically display the correct content based on the session status

## Annotations available

This module provides a set of annotations that can be used to improve code readability and maintainability.
You can explore them [here](Annotations/DestinationScreen.md)