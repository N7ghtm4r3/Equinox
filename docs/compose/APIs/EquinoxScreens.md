These APIs are used to create screens with lifecycle management similar to Android activities

## Lifecycle handled methods

- **onInit** invoked when the screen has been instantiated

- **onCreate** invoked when the screen has been created

- **onStart** invoked when the screen has been started

- **onResume** invoked when the screen has been resumed

- **onPause** invoked when the screen has been paused

- **onStop** invoked when the screen has been stopped

- **onDestroy** invoked when the screen has been destroyed

- **onAny** invoked when in the screen has occurred any of the possible events

## Implementation

### EquinoxNoModelScreen

Create a concrete custom screen with custom specifications you need without having a support viewModel

```kotlin
class TestScreen : EquinoxNoModelScreen(
    loggerEnabled = true, // whether the logger is enabled
) {

    init {
        onInit()
    }

    // place the content to display in the screen
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
        // your on init logic
    }

    override fun onStart() {
        super.onStart()
        // your on start logic
    }

    override fun onStop() {
        super.onStop()
        // your on stop logic
    }

    // where the states of the screen can be collected or instantiated
    @Composable
    override fun CollectStates() {
        // collect your states
    }

}
```

### EquinoxScreen

Create a concrete custom screen with custom specifications you need and then create the related `viewModel`.
In the lifecycle methods will be automatically handled the lifecycle of the [Retriever](Retriever.md) attached to the
**viewModel**, but you can override them and then implement your own logic

```kotlin
class TestScreen : EquinoxScreen<TestViewModel>( // specific the viewModel
    loggerEnabled = true, // whether the logger is enabled
    viewModel = TestViewModel(
        snackbarHostState = SnackbarHostState()
    ) // instantiate the viewModel
) {

    private lateinit var time: State<String>

    init {
        onInit()
    }

    // place the content to display in the screen
    @Composable
    override fun ArrangeScreenContent() {
        Scaffold(
            snackbarHost = { SnackbarHost(viewModel.snackbarHostState!!) }
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
        // your on init logic
    }

    override fun onStart() {
        super.onStart()

        // this lifecycle event cannot be automatically managed because the refresh routine is custom
        viewModel.refreshTime()
    }

    // where the states of the screen can be collected or instantiated
    @Composable
    override fun CollectStates() {
        time = viewModel.time.collectAsState()
    }

}
```

## Usage

### Display the screen

For example from the `App` function you can show the created screen as follows:

```kotlin
@Composable
fun App() {
    val screen = TestScreen()
    screen.ShowContent()
}
```

### Avoid multiple instantiations

To avoid instantiating a screen multiple times, you can use the following method to remember a screen instance across
recompositions

```kotlin
@Composable
fun App() {
    val screen = equinoxScreen { TestScreen() }
    screen.ShowContent()
}
```

## Additional usage

### CollectStatesAfterLoading method

You can use the `CollectStatesAfterLoading` method to collect states after a loading phase.  
This method can be invoked in an arbitrary way whenever needed. Next an example of its usage:

```kotlin
@Composable
override fun ArrangeScreenContent() {
    LoadingItemUI(
        loadingRoutine = {
            // your loading routine
        },
        contentLoaded = {
            // invoke this method
            CollectStatesAfterLoading()
            // rest of the content
        }
    )
}

// override and customize it with your logic
@Composable
override fun CollectStatesAfterLoading() {
    // initialize your states depending of the loaded value
}
```