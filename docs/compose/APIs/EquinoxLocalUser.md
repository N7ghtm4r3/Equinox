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

!!! Warning

    It is suggested to not hardcode the `localStoragePath` value directly, but for example create a property in 
    `gradle.properties` file or similar and `do not share` in public repos

The lifecycle of the local user starts when the `insertNewUser` is invoked, that method locally store and then assign the 
values of each property:

```kotlin
localUser.insertNewUser(
    // the standard properties
)
```

!!! Note

    The `insertNewUser` method is designed to be invoked when the user signed in or signed up into the application

#### Updating existing properties

This API provides a way to dynamically update those properties which have been updated outside this local session, for example
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

To integrate a custom property, additional to the provided properties, the standard approach is the following one:

#### Declaring the property

```kotlin
var currency: String? = null
```

#### Creating the initialization method

It is suggested naming the method which initialize custom properties with the `init` prefix and the name of the property 
as the suffix:

```kotlin
 fun initCurrency(
    currency: String?
) { 
    // initialize the property 
    this.currency = currency
    // locally save the property
    savePreference(
        key = CURRENCY_KEY,
        value = currency
    )
}
```

The structure of the method is suggested to have a compact initialization of the property and a locally saving of the preference

!!! Note

    This method can be used to update the value of the property and the related local value currently saved

#### Adapting the insertNewUser method

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
    // value of the custom property
    val currencyFromCustomArray = custom[0].toString()
    // initialize and save the preference
    initCurrency(
        currency = currencyFromCustomArray
    )
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
    // value of the custom property
    val currencyFromCustomArray = custom.extractsCustomValue(
        itemPosition = 0
    )
    // initialize and save the preference
    initCurrency(
        currency = currencyFromCustomArray
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
    setPreference<String>( // or setNullSafePreference
        key = CURRENCY_KEY,
        prefInit = { currency ->
            this.currency = currency
        }
    )
}
```

The only suggested way to correctly retrieve and initialize the properties with the retrieved values is using those two 
methods because handle the decryption of the data when needed

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

## Sensitive data 

`EquinoxLocalUser` API provides a way to safeguard the sensitive data and correctly use those data decrypted thankful to
[Kassaforte](https://github.com/N7ghtm4r3/Kassaforte) library

### Declare the sensitive keys

Can be defined a set of keys related to the properties to consider as sensitive properties:

#### Full override

By default, the properties considered sensitive are contained in the `DEFAULT_SENSITIVE_KEYS` set which contains the `host_address`,
`id` and `token` keys

A full override allows to not consider those properties as sensitive data

```kotlin
val localUser = EquinoxLocalUser(
    localStoragePath = "local store path",
    sensitiveKeys = setOf("custom_key_one", "custom_key_two")
)
```

!!! Warning

    This type of override is discouraged due the sensitivity of the data considered sensitive by default

#### Partial override

This type of override allows to keep the `DEFAULT_SENSITIVE_KEYS` set and add custom keys of the properties to
consider sensitive based on the context of the developing application:

```kotlin
val localUser = EquinoxLocalUser(
    localStoragePath = "local store path",
    sensitiveKeys = buildSet {
        addAll(DEFAULT_SENSITIVE_KEYS) // keep default keys
        add(CURRENCY_KEY) // add custom keys
    }
)
```

!!! Danger

    Removing a property from the `sensitiveKeys` set once a release has been already published, will cause the incorrect 
    handling of the encryption and decryption of the preference indicated by that key, and it will cause an application
    crash. Pay attention when a similar scenario is to be handled