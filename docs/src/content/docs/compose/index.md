---
title: "Overview"
slug: "compose"
---

<span id="overview"></span>

**v1.2.0**

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tecknobit.ametista)
![Static Badge](https://img.shields.io/badge/apple-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/web-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

Utilities for **Compose Multiplatform** applications such components, APIs for all supported platforms

## Implementation

### Version catalog

- `libs.version.toml`

```toml
[versions]
equinox = "1.2.0"
equinox-core = "1.2.1"

[libraries]
equinox-compose = { module = "io.github.n7ghtm4r3:equinox-compose", version.ref = "equinox" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinox-core" }
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
    implementation("io.github.n7ghtm4r3:equinox-compose:1.2.0")
    
    // implement the core utilities
    implementation("io.github.n7ghtm4r3:equinox-core:1.2.1")
}
```

### Gradle Groovy

```groovy
dependencies {
   
    // implement the compose utilities
    implementation 'io.github.n7ghtm4r3:equinox-compose:1.2.0'
    
    // implement the core utilities
    implementation 'io.github.n7ghtm4r3:equinox-core:1.2.1'
}
```

## APIs available

- [EquinoxViewModel](/Equinox/compose/APIs/EquinoxViewModel/) provides:
    - **EquinoxAuthViewModel**  
      Prebuilt viewmodel to authenticate the user in the system

    - **EquinoxProfileViewModel**  
      Prebuilt viewmodel to manage the user account settings and preferences

- [EquinoxScreens](/Equinox/compose/APIs/EquinoxScreens/)  
  APIs used to create screens with lifecycle management similar to Android activities

- [EquinoxWindowKit](/Equinox/compose/APIs/EquinoxWindowKit/)  
  APIs useful to handle the responsive layouts

- [EquinoxLocalUser](/Equinox/compose/APIs/EquinoxLocalUser/)  
  Helper for managing a local user session in client applications

## Components available


<details class="equinox-callout texts">

<summary>Texts</summary>

- [TextDivider](/Equinox/compose/Components/TextDivider/)  
Allows to divide sections with a representative text

- [SplitText](/Equinox/compose/Components/SplitText/)  
  Allows the user to insert a split text such OTP codes, PIN or any other texts of this type

- [ExpandableText](/Equinox/compose/Components/ExpandableText/)  
  Allows to dynamically display a long expanded text initially collapsed

- [ChameleonText](/Equinox/compose/Components/ChameleonText/)  
  Allows to change the text color dynamically based on the background of the container where the text is above

- [BadgeText](/Equinox/compose/Components/BadgesText/)  
  Allows to display a customizable badge in your UI

</details>


<details class="equinox-callout inputs">

<summary>Inputs</summary>

- [EquinoxInputs](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/Components/EquinoxInputs.kt)  
  Wrapper components of the official `TextField` and `OutlinedTextField` provided by `Material` which provides same 
  extra features such input validation or `onValue` callback behavior defined by default

- [DebouncedInputs](/Equinox/compose/Components/DebouncedInputs/)  
  Allow to implement a debounce routine handled when the user stop to typing in the inputs fields

</details>


<details class="equinox-callout feedback">

<summary>Feedback &amp; Status</summary>

- [EquinoxDialogs](https://github.com/N7ghtm4r3/Equinox/blob/main/equinox-compose/src/commonMain/kotlin/com/tecknobit/equinoxcompose/Components/EquinoxDialogs.kt)  
  Wrapper components of the official `AlertDialog` provided by `Material` with the [EquinoxViewModel](/Equinox/compose/APIs/EquinoxViewModel/)
  lifecycle handling

- [EquinoxUIs](/Equinox/compose/Components/EquinoxUIs/)  
  Are prebuilt UIs to display a representative scenario like errors, loadings, etc

- [ProgressBars](/Equinox/compose/Components/ProgressBars/)  
  Allow to display progress values on bars

- [InformativeIcon](/Equinox/compose/Components/InformativeIcon/)  
  Provides the possibility to display an informative text when the user interact with an icon

</details>


<details class="equinox-callout interactive">

<summary>Interactive</summary>


- [TabSelector](/Equinox/compose/Components/TabSelector/)  
  Custom tab selector allows to select the tab to display

- [Tile](/Equinox/compose/Components/Tile/)  
  Allows users to quickly understand options and interact with them and can group related actions or information

- [Stepper](/Equinox/compose/Components/Stepper/)  
  Allows to create a dynamic interaction with the user dividing for example a long procedure such item
  creation, customization, etc... in different specific steps where the user can interact

- [QuantityPicker](/Equinox/compose/Components/QuantityPicker/)  
  Allows to pick a numerical quantity value

- [LetterAvatar](/Equinox/compose/Components/LetterAvatar/)  
  Allows to display the letters of a provided name like a user profile placeholder

- [Avatars](/Equinox/compose/Components/Avatars/)  
  Allows to creatively display a list of avatar items

</details>


<details class="equinox-callout containers">

<summary>Containers</summary>


- [SessionFlowContainer](/Equinox/compose/Components/SessionFlowContainer/)  
  Allows to dynamically display the correct content based on the session status

</details>

## Annotations available

This module provides a set of annotations that can be used to improve code readability and maintainability.
You can explore them [here](/Equinox/compose/Annotations/DestinationScreen/)