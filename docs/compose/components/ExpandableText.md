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