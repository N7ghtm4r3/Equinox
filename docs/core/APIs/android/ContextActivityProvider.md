![Static Badge](https://img.shields.io/badge/android-4280511051)

A singleton object that helps to maintain a weak reference to the current activity,
avoiding memory leaks by preventing strong references to an Activity

## Implementation

- Initialize it in your `MainActivity.kt` class

```kotlin
class MainActivity : ComponentActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        // attach the activity
        ContextActivityProvider.setCurrentActivity(this)

        ...
    }

}
```

## Usage

Use it in composable methods

```kotlin
@Composable
fun SomeComposable() {
    val activity = ContextActivityProvider.getCurrentActivity()
}
```
