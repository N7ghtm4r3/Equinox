This annotation is useful to manage the custom parameters order for a better readability
of the code and to work with that parameters correctly

## Usage

For example the `insertNewUser` method of the [EquinoxLocalUser](../../compose/APIs/EquinoxLocalUser.md)

```kotlin
@CustomParametersOrder(
    order = ["currency"]
)
override fun insertNewUser(
    hostAddress: String,
    name: String,
    surname: String,
    email: String,
    language: String,
    response: JsonHelper,
    vararg custom: Any
) {
    // required to store and assign the standard properties
    super.insertNewUser(hostAddress, name, surname, email, language, response)
    currency = custom.extractsCustomValue(
        itemPosition = 0
    )
}
```