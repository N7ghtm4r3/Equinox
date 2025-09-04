This annotation is useful to indicate an experimental api implemented in an external project outside
`Equinox` that can be potentially implemented in the official library

## Usage

```kotlin
@FutureEquinoxApi(
    protoBehavior = """
        At the moment the proto api behaves etc...
    """ // text block suggested,
    releaseVersion = "1.0.9",
    additionalNotes = """
        - More customization
        - Add more styles
        - ...
    """ // text block suggested
)
class PotentialApi {
    ... behavior
}
```