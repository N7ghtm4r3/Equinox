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
    - **EquinoxAuthViewModel**  
      Prebuilt viewmodel to authenticate the user in the system

    - **EquinoxProfileViewModel**  
      Prebuilt viewmodel to manage the user account settings and preferences

- [EquinoxScreens](APIs/EquinoxScreens.md)  
  APIs used to create screens with lifecycle management similar to Android activities

- [EquinoxWindowKit](APIs/EquinoxWindowKit.md)  
  APIs useful to handle the responsive layouts

## Components available

??? texts "Texts"
    - [TextDivider](components/TextDivider.md)  
    Allows to divide sections with a representative text

    - [SplitText](components/splittext/SplitText.md)  
      Allows the user to insert a split text such OTP codes, PIN or any other texts of this type
    
    - [ExpandableText](components/expandabletext/ExpandableText.md)  
      Allows to dynamically display a long expanded text initially collapsed
    
    - [ChameleonText](components/chameleontext/ChameleonText.md)  
      Allows to change the text color dynamically based on the background of the container where the text is above
     
    - [BadgeText](components/BadgeText.md)  
      Allows to display a customizable badge in your UI

??? abstract "Inputs"
    - [EquinoxInputs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxInputs.kt)  
      a
    
    - [DebouncedInputs](components/DebouncedInputs.md)  
      A

??? info "Feedback & Status"
    - [EquinoxDialogs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxDialogs.kt)  
      A

    - [EquinoxUIs](src/commonMain/kotlin/com/tecknobit/equinoxcompose/components/EquinoxUIs.kt)  
      A
    
    - [ProgressBars](components/ProgressBars.md)  
      A

    - [InformativeIcon](components/InformativeIcon.md)  
      A


??? interactive "Interactive"

    - [TabSelector](components/TabSelector.md)  
      Custom tab selector allows to select the tab to display
    
    - [Tile](components/Tile.md)  
      Allows users to quickly understand options and interact with them and can group related actions or information

    - [Stepper](components/Stepper.md)  
      Allows to create a dynamic interaction with the user dividing for example a long procedure such item
      creation, customization, etc... in different specific steps where the user can interact

    - [QuantityPicker](components/QuantityPicker.md)  
        A

??? interactive "Containers"

    - [SessionFlowContainer](components/SessionFlowContainer.md)  
      A       
    
    - [Tile](components/Tile.md)  
      Allows users to quickly understand options and interact with them and can group related actions or information

    - [Stepper](components/Stepper.md)  
      Allows to create a dynamic interaction with the user dividing for example a long procedure such item
      creation, customization, etc... in different specific steps where the user can interact

    - [QuantityPicker](components/QuantityPicker.md)  
        A