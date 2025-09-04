This annotation is used to mark methods that are responsible for formatting and returning
data in a specified format.

This annotation is typically applied to methods whose sole purpose is to transform input data into a formatted version 
for use elsewhere in the application

## Usage

```kotlin
@Returner
fun convertToB(
    a: A
) : B {
    val b // logic to convert a to b
    return b
}
```