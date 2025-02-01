# AppContextInitializer

![Static Badge](https://img.shields.io/badge/android-4280511051)

Initializes the `AppContext` during the application's startup phase using AndroidX's `Initializer` interface.

This class is responsible for setting up the `AppContext` by providing the application-wide `Context` early
in the app's lifecycle. It is designed to work with AndroidX's `App Startup` library, which allows you to
initialize components as soon as the app starts, without having to modify the `Application` class

### Implementation

- Implement the official `androidx startup` library in your `build.gradle.kts` file (`android target`):

```kotlin
sourceSets {

    androidMain.dependencies {
        implementation("androidx.startup:startup-runtime:1.2.0")
    }

}
```

- Then declare the related `provider` in the `AndroidManifest` file

```xml

<application>

    ...

    <provider
            android:name="androidx.startup.InitializationProvider"
            android:authorities="${applicationId}.androidx-startup"
            android:exported="false"
            tools:node="merge">
        <meta-data
                android:name="com.tecknobit.equinoxcore.utilities.context.AppContextInitializer"
                android:value="androidx.startup"/>
    </provider>

    ...

</application>
```

- Use the application context initialized by the `AppContextInitializer`

```kotlin
actual fun someActualFun() {
    val applicationContext = AppContext.get()
}
```



## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | 3H3jyCzcRmnxroHthuXh22GXXSmizin2yp               | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | 0x1b45bc41efeb3ed655b078f95086f25fc83345c4       | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit

