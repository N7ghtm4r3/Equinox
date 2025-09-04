This annotation is useful to indicate which [Equinox screen](../APIs/EquinoxScreens.md) a navigation method reaches.
Its purpose is to improve the readability of the code

## Usage

```kotlin
// Home extends EquinoxNoModelScreen
@DestinationScreen(Home::class)
fun navToHomeScreen() {
    // your logic of the navigation method
}
```