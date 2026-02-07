This annotation is useful for indicating the methods or components that provide the real implementation
of the logic or behavior of a non-exposed method or component that wraps the real one

## Usage

```kotlin
@Composable
fun CancelButton() {
    ActionButtonImpl(
        onAction = {
            cancel()
        }
    )
}

@Composable
fun ConfirmButton() {
    ActionButtonImpl(
        onAction = {
            confirm()
        }
    )
}

@Composable
@Implementation
private fun ActionButtonImpl(
    onAction: () -> Unit
) {
    // actual button implementation
}
```