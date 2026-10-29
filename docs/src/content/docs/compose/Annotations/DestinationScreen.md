---
title: "DestinationScreen"
slug: "compose/Annotations/DestinationScreen"
---

This annotation is useful to indicate which [Equinox screen](/Equinox/compose/APIs/EquinoxScreens/) a navigation method reaches.
Its purpose is to improve the readability of the code

## Usage

```kotlin
// Home extends EquinoxNoModelScreen
@DestinationScreen(
    route = "Home" // optional
    destination = Home::class
)
fun navToHomeScreen() {
    // your logic of the navigation method
}
```