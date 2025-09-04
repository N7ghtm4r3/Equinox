This API is useful to represent a user in the client application and to manage his/her session in the client application

## Usage

This API provides the management of the following standard properties:

- `hostAddress`
- `userId`
- `userToken`
- `profilePic`
- `name`
- `surname`
- `email`
- `language`
- `theme`

It can be extended allowing a full customization based on the local session requirements for each application.

### Local user lifecycle

#### Instantiation

Can be created an instance of a new local user as follows:

```kotlin
val localUser = EquinoxLocalUser(
    localStoragePath = "local store path"
)
```

The lifecycle of the local user starts when the `insertNewUser` is invoked, that method locally store and then assign the 
values of each property:

```kotlin
localUser.insertNewUser(
    // the standard properties
)
```

#### Updating existing properties

The API provides a way to dynamically update those properties which have been updated outside this local session, for example
on another device with the same local user, this can be done as follows:

```kotlin
localUser.updateDynamicAccountData(
    dynamicData = // a json object with the account data changed
)
```

#### Logout

After the user logout the related information can be removed as follows:

```kotlin
localUser.clear()
```

## Customization

### Custom property integration

To store a property value locally, the standard approach is to create a setter for the property and, before assigning the 
value to the corresponding variable, call a method to save that value locally. 
For example, the theme property is managed as follows:

```kotlin
var theme: ApplicationTheme = Auto
    set(value) {
        setPreference(
            key = THEME_KEY,
            value = value
        )
        field = value
    }
```

A custom property could be integrated as follows:

```kotlin
var currency: String? = null
    set(value) {
        setPreference(
            key = "currency", 
            value = value
        )
        field = value
    }
```

### Adapt the insertNewUser method

To store and assign the custom property the `insertNewUser` must be adapted to use that custom property, this can be done
as follows:

```kotlin
@RequiresSuperCall
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
    currency = custom[0].toString() // value of the custom property
}
```

The retrieval of custom properties from the custom parameter depends on how the custom parameters are shared externally 
either using `*customParametersArray` or simply `customParametersArray`. When the first approach is used, you can call the 
`extractsCustomValue` method to retrieve the parameters:

```kotlin
@RequiresSuperCall
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

This is required because, if the array is passed without the `*` spread operator, Kotlin treats it as a single element of 
the `vararg`, effectively wrapping it inside another array and thus changing its dimension and the location of the custom parameters

### Initialize the user with a custom property

To correctly initialize the local user with the custom property, the `initLocalUser` method must be overridden to retrieve,
from the `localStoragePath`, the value of the custom property:

```kotlin
@RequiresSuperCall
override fun initLocalUser() {
    // required to assign the standard properties
    super.initLocalUser()
    currency = getPreference( // or getNullSafePreference
        key = "currency"
    )
}
```

## Recompose on changes

By default, when a property value changes, the UI does not recompose because the properties are not wrapped in a `State`.

To address this, you can define `observableKeys` when instantiating the local user and then observe them at runtime. 
This is made possible by the `State-Store` pattern used to manage this behavior.

### Declare the observable keys

Can be defined a set of keys related to the properties to observe as follows:

```kotlin
val localUser = EquinoxLocalUser(
    localStoragePath = "local store path",
    observableKeys = setOf("theme")
)
```

### Observe properties

Observing a property the UI can recompose when its value changes, and it can be done as follows:

```kotlin
@Composable
fun EquinoxAppTheme(
    darkTheme: Boolean = resolveTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
private fun resolveTheme(): Boolean {
    val localUserTheme by localUser.observe<ApplicationTheme>(
        key = "theme"
    )
    return when(localUserTheme) {
        Dark -> true
        Light -> false
        else -> isSystemInDarkTheme()
    }
}
```

Every time the value of the `localUserTheme` changes the UI will recompose