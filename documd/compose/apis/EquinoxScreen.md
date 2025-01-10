# EquinoxScreen

This API is useful to create a screen with a lifecycle management similar to the Android's activities

## Usage

To use the EquinoxScreen API you have to follow this guide for a correct implementation

### Creation of concrete custom screen

#### With related ViewModel

Create a concrete custom screen with the custom specifications you need and pass as parameter its viewmodel.

In the lifecycle methods will be automatically managed the `refreshinRoutine` of the `viewModel`, but you can override
them and implement your logic to manage that routine

```kotlin
class TestScreen : EquinoxScreen<TestViewModel>( // related viewmodel of TestScreen
    loggerEnabled = true, // whether the logger is enabled
    viewModel = TestViewModel(
        snackbarHostState = SnackbarHostState()
    ) // its related viewmodel
) {

    private lateinit var time: State<String>

    init {
        onInit()
    }

    @Composable
    override fun ArrangeScreenContent() {
        Scaffold(
            snackbarHost = { SnackbarHost(viewModel!!.snackbarHostState!!) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextDivider(
                        text = time.value,
                        textStyle = TextStyle(
                            fontSize = 40.sp
                        )
                    )
                }
            }
        }
    }

    override fun onInit() {
        super.onInit()
        // your on init content
    }

    override fun onStart() {
        super.onStart()

        // this lifecycle event cannot be automatically managed because the refresh routine is custom
        viewModel!!.refreshTime()
    }

    // where the states of the screen can be collected or instantiated
    @Composable
    override fun CollectStates() {
        time = viewModel!!.time.collectAsState()
    }

}
```

#### With no related ViewModel

Create a concrete custom screen with the custom specifications you need without passing the viewmodel

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>(
    // this is required and cannot be omitted, but will not be used any viewmodel
    loggerEnabled = true, // whether the logger is enabled
) {

    init {
        onInit()
    }

    @Composable
    override fun ArrangeScreenContent() {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextDivider(
                        text = "text",
                        textStyle = TextStyle(
                            fontSize = 40.sp
                        )
                    )
                }
            }
        }
    }

    override fun onInit() {
        super.onInit()
        // your on init content
    }

    override fun onStart() {
        super.onStart()
        // your on start content
    }

    override fun onStop() {
        super.onStop()
        // your on stop content
    }

    // where the states of the screen can be collected or instantiated
    @Composable
    override fun CollectStates() {
        // collect your states
    }

}
```

### Display the screen

For example from the `App` function you can show the screen created before:

```kotlin
@Composable
fun App() {
    val screen = TestScreen()
    screen.ShowContent()
}
```



## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%2Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

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

