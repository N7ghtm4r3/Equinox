This annotation is used to indicate that a snippet of code, or an entire section,
still needs to be documented. This annotation serves as a reminder to complete the documentation
before publishing or using the code

## Usage

```kotlin
@RequiresDocumentation
fun newApi() {
     // code
}


@RequiresDocumentation(
     additionalNotes = """
         In the documentation must be included the release version
     """
)
fun newApiVersion() {
     // code
}
```