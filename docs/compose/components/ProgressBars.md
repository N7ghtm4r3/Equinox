Components used to display progress values on bars

## HorizontalProgressBar

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
            // Your total value
            val total = 10

            // Arbitrary size to indicate the completion (width or height)
            val completionSize = 150.dp

            // any container
            Card(
                modifier = Modifier
                    .width(150.dp)
            ) {
                var progress = remember { 0 }
                HorizontalProgressBar(
                    containerModifier = Modifier
                        .padding(
                            all = 10.dp
                        ),
                    completionWidth = completionSize,
                    currentProgress = {
                        // your logic to retrieve the progress value
                        delay(1000)
                        if (progress < 10)
                            ++progress
                        else
                            progress++
                    },
                    total = total,
                    onCompletion = {
                        // a not mandatory completion callback
                        println("Completed!")
                    }
                )
            }
        }
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property              | Description                                                           |
|-----------------------|-----------------------------------------------------------------------|
| `containerModifier`   | The modifier to apply to the container                                |
| `progressBarModifier` | The modifier to apply to the progress line                            |
| `lineColor`           | The color to apply to the progress line                               |
| `cap`                 | The style to apply to the extremity of the progress line              |
| `strokeWidth`         | The width of the stroke of the progress bar                           |
| `onCompletion`        | Callback invoked when the `currentProgress` reaches the `total` value |
| `progressIndicator`   | The content used to display the progress                              |
| `animationSpec`       | Custom animations to apply to the progress line when it changes       |

### Appearance

#### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/progressbars/horizontal_progress_bar-android.webm" type="video/webm">
  Cannot play the video
</video>

#### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/progressbars/horizontal-progressbar-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>

## VerticalProgressBar

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
            // Your total value
            val total = 10

            // Arbitrary size to indicate the completion (width or height)
            val completionSize = 150.dp

            // any container
            Card(
                modifier = Modifier
                    .width(150.dp)
            ) {
                var progress = remember { 0 }
                VerticalProgressBar(
                    containerModifier = Modifier
                        .padding(
                            all = 10.dp
                        ),
                    completionHeight = completionSize,
                    currentProgress = {
                        // your logic to retrieve the progress value
                        delay(1000)
                        if (progress < 10)
                            ++progress
                        else
                            progress++
                    },
                    total = total,
                    onCompletion = {
                        // a not mandatory completion callback
                        println("Completed!")
                    }
                )
            }
        }
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property              | Description                                                           |
|-----------------------|-----------------------------------------------------------------------|
| `containerModifier`   | The modifier to apply to the container                                |
| `progressBarModifier` | The modifier to apply to the progress line                            |
| `lineColor`           | The color to apply to the progress line                               |
| `cap`                 | The style to apply to the extremity of the progress line              |
| `strokeWidth`         | The width of the stroke of the progress bar                           |
| `onCompletion`        | Callback invoked when the `currentProgress` reaches the `total` value |
| `progressIndicator`   | The content used to display the progress                              |
| `animationSpec`       | Custom animations to apply to the progress line when it changes       |

### Appearance

#### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/progressbars/vertical_progressbar-android.webm" type="video/webm">
  Cannot play the video
</video>

#### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/progressbars/vertical_progressbar-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>