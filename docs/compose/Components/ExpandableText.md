This component allows to dynamically display a long expanded text initially collapsed

## Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {
    
    @Composable
    override fun ArrangeScreenContent() {
        val longText = "..."
        ExpandableText(
            containerModifier = Modifier
                .widthIn(
                    max = 750.dp
                ),
            textModifier = Modifier
                .padding(
                    horizontal = 16.dp
                ),
            text = longText,
            textStyle = TextStyle(
                textAlign = TextAlign.Justify,
            )
        )
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Property            | Description                                              |
|---------------------|----------------------------------------------------------|
| `containerModifier` | The modifier to apply to the container `Column`          |
| `textModifier`      | The modifier to apply to the `Text` component            |
| `textStyle`         | The text style to customize the appearance of the text   |
| `maxLines`          | The maximum number of lines to display in collapsed mode |
| `expandedMaxLines`  | The maximum number of lines to display in expanded mode  |
| `overflow`          | The behavior of the text in case of overflow             |
| `iconSize`          | The size of the icon indicator                           |
| `expandedIcon`      | The icon displayed when the text is in expanded mode     |
| `collapsedIcon`     | The icon displayed when the text is in collapsed mode    |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/expandabletext/expandabletext-android.mp4" type="video/mp4">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/expandabletext/expandabletext-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>