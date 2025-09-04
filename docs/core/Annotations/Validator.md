This annotation is applied to those methods which are used to check the validity of an input, and it is
useful to provide additional information about the annotated method such the validity case of the input checked

## Usage

```kotlin
@Validator(
    validWhen = "The input is valid when is not null" // not mandatory
)
fun inputValid(
    input: Any?
) : Boolean {
    return input != null
}
```