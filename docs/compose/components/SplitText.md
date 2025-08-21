This component allows the user to insert a split text such OTP codes, PIN or any other texts of this type

## Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // create the dedicated state
            val splitTextState = rememberSplitTextState(
                splits = 6 // the number of the splits which compose the completed text
            )

            // create the related component
            SplitText(
                splitsTextState = splitTextState,
                infoText = InfoText( // if omitted will not be displayed nothing
                    text = "Informative text"
                )
            )

            // assemble and get the completed text
            Text(
                text = splitTextState.getCompleteText()
            )
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Parameter             | Description                                         |
|-----------------------|-----------------------------------------------------|
| `columnModifier`      | The modifier to apply to the `Column` container     |
| `rowModifier`         | The modifier to apply to the `LazyRow` container    |
| `spacingBetweenBoxes` | The spacing between the boxes                       |
| `boxShape`            | The shape to apply to the `SplitBox`                |
| `boxTextStyle`        | The text style to use for the `SplitBox`'s text     |
| `infoText`            | The informative text about what the component needs |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/splittext/splittext-android.webm" type="video/webm">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/splittext/splittext-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>