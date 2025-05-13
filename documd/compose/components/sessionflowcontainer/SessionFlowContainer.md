# SessionFlowContainer

Component used to dynamically display the correct content based on the session status

## Usage

### The invokeOnUserDisconnected callback

You can set a custom callback to invoke when the user disconnected from the current session as follows:

```kotlin
@Composable
fun App() {
    SessionFlowState.invokeOnUserDisconnected {
        // your custom logic to handle user disconnection
    }
}
```

### Workflow

In this example the architecture is an `EquinoxScreen` and its related `EquinoxViewModel`

#### TestScreenViewModel

Create the viewmodel and the state used by the component

```kotlin
class TestScreenViewModel : EquinoxViewModel() {

    // declare the state used by the component
    lateinit var sessionFlowState: SessionFlowState

    fun screenRoutine() {
        // your custom routine (for the example will be forced the scenarios)
        viewModelScope.launch {
            delay(2000)

            // notify the server is currently offline
            sessionFlowState.notifyServerOffline()

            delay(2000)
            // notify the operational status of the session
            sessionFlowState.notifyOperational()

            // notify the user disconnection
            sessionFlowState.notifyUserDisconnected() // will be invoked the callback you set
        }
    }

}
```

#### TestScreen

Create the screen where the `SessionFlowContainer` will be displayed

```kotlin
class TestScreen : EquinoxScreen<TestScreenViewModel>(
    viewModel = TestScreenViewModel()
) {

    /**
     * Method used to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        SessionFlowContainer(
            modifier = Modifier
                .fillMaxSize(),
            state = viewModel.sessionFlowState,
            content = {
                // here you can display the main content to have in an operational status
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "OPERATIONAL",
                        style = AppTypography.displayLarge
                    )
                }
            }
        )
    }

    // invoke the routine
    override fun onStart() {
        super.onStart()
        viewModel.screenRoutine()
    }

    /**
     * Method used to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        // here instantiate the state
        viewModel.sessionFlowState = rememberSessionFlowState(
            initialStatus = // assign a custom status
        )
    }

}
```

## Appearance

### Mobile

[sessionflowcontainer-mobile](https://github.com/user-attachments/assets/b556fb90-165a-47cf-a2ee-0b2feb4ceaf6)

### Desktop & Web

[sessionflowcontainer-desktop](https://github.com/user-attachments/assets/ca770866-b7d5-472b-bcc0-6935deacba14)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit

