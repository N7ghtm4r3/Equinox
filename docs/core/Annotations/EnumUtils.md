This annotation is applied to files that provide utility methods for enums, such as entry conversion, entry mapping,
and related operations

## Usage

```kotlin
@file:EnumUtils

@Returner
@Composable
fun DocumentType.resolveColor(): Color {
    return when (this) {
        UIUX -> MaterialTheme.colorScheme.tertiary
        TECHNICAL -> ice()
        LEGAL -> gray()
        DOCUMENTATION -> green()
        COMMERCIAL -> blue()
        MISCELLANEOUS -> neutral()
    }
}

@Returner
@Composable
fun Role.resolveColor(): Color {
    return when (this) {
        VENDOR -> MaterialTheme.colorScheme.primary
        CUSTOMER -> MaterialTheme.colorScheme.tertiary
    }
}
```