This component allows to create a dynamic interaction with the user dividing for example a long procedure such item
creation, customization, etc... in different specific steps where the user can interact

## Usage

### Create the steps

It is suggested to create the steps-array inside the `remember` method to avoid the recreation during the recompositions

```kotlin
@Composable
fun AnyComponent() {
    val steps = remember {
        arrayOf(
            Step(
                stepIcon = Icons.Default.Check, // representative icon
                title = Res.string.check, // title of the step
                content = {
                    Check() // content of the step
                }
            ),
            Step(
                stepIcon = Icons.Default.Commit,
                title = Res.string.commit,
                content = {
                    Commit()
                }
            ),
            Step(
                stepIcon = Icons.Default.Upload,
                title = Res.string.push,
                content = {
                    Push()
                },
                dismissAction = {
                    pushed.value = !pushed.value
                }
            )
        )
    }
}
```

### Customize the content of the step

```kotlin
@Composable
//not mandatory, but it is better for readability
@StepContent(
    number = 0,
    enabledWhen = "This step is enabled when etc..."
)
private fun Check() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
        )
        Text(
            text = "All code is okay"
        )
    }
}
```

### Use the Stepper

```kotlin
@Composable
fun AnyComponent() {
    // steps created
    Stepper(
        headerSection =  // custom header section
        startStepShape =  // custom start shape
        middleStepShape = // custom middle shape
        finalStepShape = // custom final shape
        confirmColor = // custom confirmation color
        errorColor = // custom error color
        stepBackgroundColor = // custom step background color
        expandsStepIcon = // custom icon used to expand the steps
        steps = steps
    )
}
```

## Customization

Check out the table below to apply your customizations to the component:

| Parameter             | Description                                            |
|-----------------------|--------------------------------------------------------|
| `containerModifier`   | The modifier to apply to the container `Column`        |
| `stepperModifier`     | The modifier to apply to the component                 |
| `headerSection`       | A custom header of the stepper                         |
| `startStepShape`      | The shape to apply to the first visible `Step`         |
| `middleStepShape`     | The shape to apply to those steps in the middle        |
| `finalStepShape`      | The shape to apply to the last visible `Step`          |
| `stepBackgroundColor` | The color to use as background of the `Step` container |
| `errorColor`          | The color used to indicate an error                    |
| `confirmColor`        | The color used to confirm an action                    |
| `expandsStepIcon`     | The icon used to expand each step                      |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/stepper/stepper-mobile.webm" type="video/webm">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/stepper/stepper-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>