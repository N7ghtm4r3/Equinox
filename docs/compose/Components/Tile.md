This component allows users to quickly understand options and interact with them and can group related actions or
information

## Classic Tile

### Usage

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
            Tile(
                icon = Icons.Default.Settings, // representative icon
                text = "Settings", // representative text
                contentColor = Color.White, // customize the content color
                onClick = {
                    // your action
                }
            )
        }
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Parameter        | Description                                   |
|------------------|-----------------------------------------------|
| `modifier`       | The modifier to apply to the container `Card` |
| `size`           | The size of the tile                          |
| `shape`          | The shape of the tile                         |
| `containerColor` | The color scheme to apply to the tile         |
| `contentColor`   | The color of the content, icon, and the text  |
| `elevation`      | The elevation of the tile                     |
| `iconSize`       | The size of the `icon`                        |
| `fontWeight`     | The weight to apply to the `text`             |
| `textStyle`      | The style to apply to the `text`              |

### Appearance

#### Mobile

![classic_tile-android.png](assets/images/tile/classic_tile-android.png){ .shadow .mobile-appearance }

#### Desktop & Web

![classic_tile-desktop.png](assets/images/tile/classic_tile-desktop.png){ .shadow }

## Dashed Tile

### Usage

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
            DashedTile(
                icon = Icons.Default.Settings, // representative icon
                text = "Settings", // representative text
                onClick = {
                    // your action
                }
            )
        }
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Parameter        | Description                                           |
|------------------|-------------------------------------------------------|
| `modifier`       | The modifier to apply to the container `Card`         |
| `strokeWidth`    | The stroke width to apply as dashed effect            |
| `intervals`      | The number of the interval from each part of the line |
| `phase`          | The pixel offset for the intervals                    |
| `size`           | The size of the tile                                  |
| `cornerRadius`   | The radius of the tile                                |
| `containerColor` | The color scheme to apply to the tile                 |
| `contentColor`   | The color of the content, icon, and the text          |
| `iconSize`       | The size of the `icon`                                |
| `fontWeight`     | The weight to apply to the `text`                     |
| `textStyle`      | The style to apply to the `text`                      |

### Appearance

#### Mobile

![dashed_tile-android.png](assets/images/tile/dashed_tile-android.png){ .shadow .mobile-appearance }

#### Desktop & Web

![dashed_tile-desktop.png](assets/images/tile/dashed_tile-desktop.png){ .shadow }

## Dynamic Tile

Useful to execute action when clicked and dynamically change its style to best represent the action or the subject that
the tile represents

### TileStyle

| Style     | Description                                                                                           |
|-----------|-------------------------------------------------------------------------------------------------------|
| `DEFAULT` | The default style to draw a tile for general purposes                                                 |
| `DASHED`  | The dashed style to draw a dashed-borders tile, for example to represent an unavailable action etc... |

### Usage

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
            var settingsAvailable by remember { mutableStateOf(true) }
            DynamicTile(
                icon = Icons.Default.Settings, // representative icon
                text = "Settings", // representative text
                applyStyle = {
                    // logic to apply the style
                    if (settingsAvailable)
                        TileStyle.DEFAULT
                    else
                        TileStyle.DASHED
                },
                onClick = {
                    // your action
                    settingsAvailable = !settingsAvailable
                }
            )
        }
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Parameter             | Description                                                        |
|-----------------------|--------------------------------------------------------------------|
| `modifier`            | The modifier to apply to the container `Card`                      |
| `strokeWidth`         | The stroke width to apply as dashed effect                         |
| `intervals`           | The number of the interval from each part of the line              |
| `phase`               | The pixel offset for the intervals                                 |
| `size`                | The size of the tile                                               |
| `cornerRadius`        | The radius of the tile                                             |
| `containerColor`      | The colors scheme to apply to the tile                             |
| `contentColor`        | The color of the content, icon and the text                        |
| `elevation`           | The elevation of the tile                                          |
| `icon`                | The representative icon                                            |
| `iconSize`            | The size of the `icon`                                             |
| `text`                | The representative text                                            |
| `fontWeight`          | The weight to apply to the `text`                                  |
| `textStyle`           | The style to apply to the `text`                                   |
| `applyStyle`          | The logic to apply the style to the tile                           |
| `onClick`             | The action to execute when the tile has been clicked               |
| `outerModifier`       | The modifier to apply to the outer `Column` made-up this component |
| `innerModifier`       | The modifier to apply to the inner `Column` made-up this component |
| `verticalArrangement` | The vertical arrangement of the `content`                          |
| `content`             | The content to display inside the container                        |

### Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/tile/dynamictile-mobile.webm" type="video/webm">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/tile/dynamictile-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>