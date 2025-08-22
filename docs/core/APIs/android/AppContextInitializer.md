![Static Badge](https://img.shields.io/badge/android-4280511051)

Initializes the `AppContext` during the application's startup phase using AndroidX's `Initializer` interface.

This class is responsible for setting up the `AppContext` by providing the application-wide `Context` early
in the app's lifecycle. It is designed to work with AndroidX's `App Startup` library, which allows to
initialize components as soon as the app starts, without having to modify the `Application` class

## Implementation

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

## Usage

Use the application context initialized by the `AppContextInitializer` as follows:

```kotlin
actual fun someActualFun() {
    val applicationContext = AppContext.get()
}
```