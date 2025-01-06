# SessionManager

This API allows the user to manage the content displayed in base of the current scenario occurred

## Implementation guide

To use the SessionManager API you have to follow this guide for a correct implementation

### Creation of a viewmodel

To manage the scenarios available with the SessionManager you can create the logic in a routine of an EquinoxViewModel

```kotlin
class TestViewModel : EquinoxViewModel() {

    fun refreshRoutine() {
        execRefreshingRoutine(
            currentContext = Test::class.java,
            routine = {

                // your refresh routine

                // your logic to determine if the server is offline 
                val isServerOffline = boolean_value
                // then set that result with the below method
                SessionManager.setServerOfflineValue(isServerOffline)

                // your logic to determine if the user has been disconnected
                val hasBeenDisconnected = boolean_value
                // then set that result with the below method
                SessionManager.setHasBeenDisconnectedValue(hasBeenDisconnected)
            }
        )
    }

}
```

### Set up the session

#### Android

- In the `AndroidManifest` file you must declare the Internet permission:

    ```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    ```

- For one time only, using for example a custom `SplashScreen`, properly set up the session:

    ```kotlin
    @SuppressLint("CustomSplashScreen")
    class SplashScreen : ComponentActivity() {
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
 
            // enable the StrictMode
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
  
            setContent {
  
                // set up and customize the session 
                SessionManager.setUpSession(
                    serverOfflineMessage = "server_currently_offline_message",
                    serverOfflineMessageIcon = /* you can customize the icon if needed */
                    noInternetConnectionMessage = "no_internet_message",
                    noInternetConnectionIcon = ImageVector.vectorResource(id = R.drawable.no_internet),
                    hasBeenDisconnectedAction = {
                        // the action to execute when the user has been disconnected
                    }
                )
                
                // ... rest of the application
  
            }
        }
    
    }
    ```

  To use the `no_internet` drawable given by the library you have to download it and place in the `drawable` folder of
  your android application
  ``` bash
  res
   |-- drawable
       |-- no_internet.xml
  ```

  You can download
  it [here](https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/library/src/commonMain/composeResources/drawable/no_internet.xml)

- Use the SessionManager API

  ```kotlin
   class Test {
       
        private val viewModel = TestViewModel()

        init {
            viewModel.setActiveContext(this::class.java)
            viewModel.refreshingRoutine()
        }

        @Composable
        fun Screen() {
  
            // invoke this method to use the SessionManager API automatically
            ManagedContent(
                viewModel = viewModel,
                content = {
                    // the normal content to display
                }
            )
        }
   
  }        
  ```

  <details>
  <summary>Appearance (the theme is relative)</summary>
    <img src="https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/images/no_internet_mobile.png" alt="no_internet"/>
    <img src="https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/images/server_offline_mobile.png" alt="server_offline"/>
  </details>

#### Desktop/Jvm

- In the `build.gradle.kts` file you have to implement the `kotlinx-coroutines-swing` library:

    ```groovy
    desktopMain.dependencies {      
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.1")
    }
    ```

- For one time only, placing for example the code in the `Main.kt` file, properly set up the session:

    ```kotlin
    fun main() = application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "app_name"
        ) {
  
            // set up the session with the default values 
            SessionManager.setUpSession()
  
            // or
  
            // set up and customize the session 
            SessionManager.setUpSession(
                serverOfflineMessage = "server_currently_offline_message",
                serverOfflineMessageIcon = /* you can customize the icon if needed */
                noInternetConnectionMessage = "no_internet_message",
                noInternetConnectionIcon = /* you can customize the icon if needed */,
                hasBeenDisconnectedAction = {
                    // the action to execute when the user has been disconnected
                }
            )
  
            // then invoke the App method to launch the application
            App()
        }
    }
    ```

- Use the SessionManager API

  ```kotlin
   class Test {
       
        private val viewModel = TestViewModel()

        init {
            viewModel.setActiveContext(this::class.java)
            viewModel.refreshingRoutine()
        }

        @Composable
        fun Screen() {
  
            // invoke this method to use the SessionManager API automatically
            ManagedContent(
                viewModel = viewModel,
                content = {
                    // the normal content to display
                }
            )
        }
   
  }        
  ```

  <details>
  <summary>Appearance (the theme is relative)</summary>
    <img src="https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/images/no_internet_desktop.png" alt="no_internet"/>
    <img src="https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/images/server_offline_desktop.png" alt="server_offline"/>
  </details>

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox-Compose/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                        | Network  |
|-----------------------------------------------------------------------------------------------------|------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**         | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4** | Ethereum |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2024 Tecknobit

