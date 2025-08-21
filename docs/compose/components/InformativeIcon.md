This component provides the possibility to display an informative text when the user interact with an icon

## Usage

```kotlin
class TestScreen : EquinoxNoModelScreen() {

    @Composable
    override fun ArrangeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // standard icon
            InformativeIcon(
                imageVector = Icons.Default.Coffee,
                infoText = "Drink a coffee!"
            )

            // customized icon
            val cappuccinoColor = "#9D7A5C".toColor()
            InformativeIcon(
                imageVector = Icons.Default.Coffee,
                size = 26.dp,
                infoText = "Drink a cappuccino!",
                infoTextColor = Color.LightGray,
                infoTextBackgroundColor = Color.Gray,
                tint = cappuccinoColor
            )
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Property                  | Description                                                                                   |
|---------------------------|-----------------------------------------------------------------------------------------------|
| `modifier`                | The `Modifier` to be applied to this icon                                                     |
| `infoTextColor`           | The color to apply to the `infoText`                                                          |
| `infoTextBackgroundColor` | The color to apply to the background of the `infoText`                                        |
| `size`                    | The size of the icon                                                                          |
| `tint`                    | The tint to be applied to `icon`. If `Color.Unspecified` is provided, then no tint is applied |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/informativeicon/informativeicon-mobile.webm" type="video/mp4">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/informativeicon/informativeicon-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>