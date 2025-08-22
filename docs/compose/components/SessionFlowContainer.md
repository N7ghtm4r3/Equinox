This component allows to dynamically display the correct content based on the session status

## Statuses

| Status                  | Description                                                                             |
|-------------------------|-----------------------------------------------------------------------------------------|
| `OPERATIONAL`           | The normal status of the session                                                        |
| `SERVER_OFFLINE`        | The status of the session when the related server is offline                            |
| `NO_NETWORK_CONNECTION` | The status of the session when there is no network connection                           |
| `USER_DISCONNECTED`     | The status of the session when the user has been disconnected                           |
| `CUSTOM`                | Status that allows customizing the notification of error or any custom status as needed |

## Usage

### Set invokeOnUserDisconnected callback

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

## Customization

Check out the table below to apply your customizations to the component:

| Property                     | Description                                                                                     |
|------------------------------|-------------------------------------------------------------------------------------------------|
| `triggers`                   | The triggers to use to automatically reinvoke the `loadingRoutine`                              |
| `modifier`                   | The modifier to apply to the component                                                          |
| `viewModel`                  | If passed, will be used to autonomously suspend and restart the Retriever's routine             |
| `onReconnection`             | An optional callback to invoke after the connection has been reestablished                      |
| `enterTransition`            | The transition to apply when a new content is displayed                                         |
| `exitTransition`             | The transition to apply when a content is hidden                                                |
| `initialLoadingRoutineDelay` | Delay to apply to the `loadingRoutine` before it starts                                         |
| `loadingRoutine`             | Routine used to load the elements displayed in the `content`                                    |
| `content`                    | The main content displayed when the `SessionStatus` is `OPERATIONAL`                            |
| `statusTextStyle`            | The style to apply to the texts of the callback contents                                        |
| `statusContainerColor`       | The color to apply to the background of the callback contents                                   |
| `loadingContentColor`        | The color to apply to the loading content                                                       |
| `loadingIndicator`           | The indicator used during the `loadingRoutine` execution                                        |
| `fallbackContentColor`       | The color to apply to the fallback contents                                                     |
| `retryFailedFlowContent`     | The content displayed to allow the user to retry a failed operation                             |
| `onServerOffline`            | The content displayed when the `SessionStatus` is `SERVER_OFFLINE`                              |
| `onCustomError`              | The content displayed when the `SessionStatus` is `CUSTOM` and related to the extra error value |
| `onNoNetworkConnection`      | The content displayed when the `SessionStatus` is `NO_NETWORK_CONNECTION`                       |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/sessionflowcontainer/sessionflowcontainer-mobile.webm" type="video/webm">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/sessionflowcontainer/sessionflowcontainer-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>