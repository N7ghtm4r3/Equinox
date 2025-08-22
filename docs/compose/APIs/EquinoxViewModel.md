This API is a wrapper around
the [Compose ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html).  
It provides a simple way to delegate user interactions, such as communicating with the backend or executing retrieval
routines to update UI data from [EquinoxScreens](EquinoxScreens.md)

## Implementation

In this example will be created a custom viewmodel, extending the **EquinoxViewModel**, to retrieve data from backend
in a **background coroutine** using the provided **retrieve** method

```kotlin
class TestViewModel : EquinoxViewModel() {

    fun retrieveRoutine() {
        retrieve(
            currentContext = Test::class, // the current screen displayed 
            routine = {
                // logic to retrieve data
            }
        )
    }

}
```

## Usage

Create a related screen with attached the [TestViewModel](#implementation) and use it as follows:

```kotlin
class Test : EquinoxScreen(
    viewModel = TestViewModel()
) {

    @Composable
    override fun ArrangeScreenContent() {
        // some state to control the visibility of an element on screen
        val show = remember { mutableStateOf(false) }
        // an example composable linked to 'show' state
        EquinoxAlertDialog(
            show = show,
            title = "Any title",
            text = "Any text",
            viewModel = viewModel, // passing the viewmodel allows the component to automatically suspend or restart the refresher
            onDismissAction = {

                // your code

                show.value = false // the refresh routine will be restated
            },
            dismissText = "Dismiss",
            confirmText = "Confimer",
            confirmAction = {
                // your code
                show.value = false // the refresh routine will be restated
            }
        )
        Button(
            onClick = {
                // your code
                show.value = true // the refresh routine will be suspended
            }
        ) {
            Text(
                text = "Show"
            )
        }
    }

    override fun onStart() {
        // start the retrieve routine
        viewModel.retrieveRoutine()
    }

}
```