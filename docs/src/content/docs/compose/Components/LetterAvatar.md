---
title: "LetterAvatar"
slug: "compose/Components/LetterAvatar"
---

This component allows to display the letters of a provided name in the format: 

```text
Name Second-name Third-name -> (NST)
```

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
            LetterAvatar(
                name = "John Doe",
                size = 75.dp, // custom size
            )
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Param                    | Description                                                                                                  |
|--------------------------|--------------------------------------------------------------------------------------------------------------|
| `modifier`               | The modifier to apply to the component                                                                       |
| `shape`                  | The shape to apply to the component                                                                          |
| `elevation`              | The elevation to apply to the component                                                                      |
| `uppercaseFormat`        | Whether the letters must be displayed in uppercase format or displayed as provided                           |
| `name`                   | The name of the avatar                                                                                       |
| `defaultBackgroundColor` | The default color when the `name` is empty                                                                   |
| `backgroundColor`        | The color of the background, default color is resolved with [resolveAvatarColor](#resolveavatarcolor) method |
| `style`                  | The style of the displayed letters                                                                           |

## Appearance

#### Mobile

<img src="/Equinox/compose/Components/assets/images/letteravatar/letter_avatar-android.png" alt="letter_avatar-android.png" class="shadow mobile-appearance" loading="lazy">

#### Desktop & Web

<img src="/Equinox/compose/Components/assets/images/letteravatar/letter_avatar-desktop.png" alt="letter_avatar-desktop.png" class="shadow" loading="lazy">

## Public methods

### resolveAvatarColor

#### Description

Method used to resolve the background color for this component

#### Usage

```kotlin
val avatarColor: Color = resolveAvatarColor(
    name = "John Doe"
)

println(avatarColor.toHex()) // e.g #A55C80
```