This component allows to display a customizable badge in your UI.
It is useful for showing labels, states or tags in a visually distinct way

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
            BadgeText(
                badgeText = "MyBadge",
                badgeColor = MaterialTheme.colorScheme.primary
            )
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Property         | Description                                                                                                                                                                                                                      |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `modifier`       | The `Modifier` to be applied to the component                                                                                                                                                                                    |
| `shape`          | The shape of the badge                                                                                                                                                                                                           |
| `padding`        | The padding applied from the text to outside the badge                                                                                                                                                                           |
| `elevation`      | The elevation to apply to the badge                                                                                                                                                                                              |
| `fontSize`       | The size of glyphs to use when painting the text. See `TextStyle.fontSize`                                                                                                                                                       |
| `fontStyle`      | The typeface variant to use when drawing the letters (e.g., italic). See `TextStyle.fontStyle`                                                                                                                                   |
| `fontWeight`     | The typeface thickness to use when painting the text (e.g., `FontWeight.Bold`)                                                                                                                                                   |
| `fontFamily`     | The font family to be used when rendering the text. See `TextStyle.fontFamily`                                                                                                                                                   |
| `letterSpacing`  | The amount of space to add between each letter. See `TextStyle.letterSpacing`                                                                                                                                                    |
| `textDecoration` | The decorations to paint on the text (e.g., an underline). See `TextStyle.textDecoration`                                                                                                                                        |
| `lineHeight`     | Line height for the `Paragraph` in `TextUnit` unit, e.g. SP or EM. See `TextStyle.lineHeight`                                                                                                                                    |
| `softWrap`       | Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space                                                                                |
| `onTextLayout`   | Callback that is executed when a new text layout is calculated. A `TextLayoutResult` object provided contains paragraph info, text size, baselines, and more. Useful for adding decorations or selection highlights around text. |
| `badgeTextStyle` | The style configuration for the text                                                                                                                                                                                             |
| `textColor`      | The color of the text based on the current `badgeColor`                                                                                                                                                                          |

## Appearance

### Mobile

![badgetext-android.png](assets/images/badgetext/badgetext-android.png){ .shadow .mobile-appearance }

### Desktop & Web

![badgetext-desktop.png](assets/images/badgetext/badgetext-desktop.png){ .shadow }