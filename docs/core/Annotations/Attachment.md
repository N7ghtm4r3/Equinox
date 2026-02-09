This annotation is useful to indicate those methods which provide an additional behavior to a component,
such for example requests to backend, data retrieval, etc... that need to be marked with the `Composable` annotation due
`LaunchedEffect` usage or similar

## Usage

```kotlin
@Composable
fun MyComponent() {
    retrieveData()
    // rest of the component
}

@Attachment
@Composable
@Suppress("ComposableNaming")
private fun retrieveData() {
    LaunchedEffect(Unit) {
        // your logic
    }
}
```