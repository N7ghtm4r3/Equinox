The following components provide feedback to the user about the current state of the application and allow them to interact 
with it to continue their experience

## LoadingItemUI

Component used to display a loading state

### Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        LoadingItemUI(
            loadingRoutine = {
                // your loading routine, then its return
                true
            },
            contentLoaded = {
                // your loaded content
            }
        )
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property                     | Description                                                        |
|------------------------------|--------------------------------------------------------------------|
| `triggers`                   | The triggers to use to automatically reinvoke the `loadingRoutine` |
| `containerModifier`          | The modifier to apply to the container column                      |
| `animations`                 | The set of the animations to use to animate the layout             |
| `textStyle`                  | The style to apply to the text                                     |
| `loadingRoutine`             | The routine used to load the data                                  |
| `initialDelay`               | An initial delay to apply to the `loadingRoutine` before to start  |
| `loadingIndicatorBackground` | The background to apply to the `loadingIndicator` content          |
| `loadingIndicator`           | The loading indicator to display                                   |
| `contentLoaded`              | The content to display when the data have been loaded              |
| `themeColor`                 | The color to use into the composable                               |

## EmptyListUI

Component used to display a layout when a list of values is empty

### Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        EmptyListUI(
            icon = Icons.AutoMirrored.Filled.List, // representative icon
            subText = "No items in the list" // representative icon
        )
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property            | Description                                            |
|---------------------|--------------------------------------------------------|
| `containerModifier` | The modifier to apply to the container column          |
| `imageModifier`     | The modifier to apply to the image icon                |
| `animations`        | The set of the animations to use to animate the layout |
| `textStyle`         | The style to apply to the text                         |
| `icon`              | The icon to display                                    |
| `themeColor`        | The color to use into the composable                   |
| `subText`           | The description of the layout                          |

## ErrorUI

Component used to display a layout when an error occurred

### Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        ErrorUI(
            errorIcon = Icons.Default.Error, // representative icon
            errorMessage = "Ops, something goes wrong!" // representative error message
        )
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property            | Description                                                                             |
|---------------------|-----------------------------------------------------------------------------------------|
| `containerModifier` | The modifier to apply to the container column                                           |
| `imageModifier`     | The modifier to apply to the image icon                                                 |
| `animations`        | The set of the animations to use to animate the layout                                  |
| `textStyle`         | The style to apply to the text                                                          |
| `backgroundColor`   | The color of the background                                                             |
| `errorIcon`         | The error icon used, as default is used the `Icons.Default.Error`                       |
| `errorColor`        | The error color used, as default is used the `MaterialTheme.colorScheme.errorContainer` |
| `errorMessage`      | The error that occurred or to indicate a generic error                                  |
| `retryContent`      | The content to retry the failed operation                                               |

## EmptyState

Component for displaying custom empty state graphics

### Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        EmptyState(
            resource = Res.drawable.item_added, // representative graphic
            contentDescription = "Item added!", // representative description
        )
    }

}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property             | Description                                                                                                                           |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| `animations`         | The set of the animations to use to animate the layout                                                                                |
| `containerModifier`  | The modifier to apply to the container `Column`                                                                                       |
| `resourceModifier`   | The modifier to apply to the `Image`                                                                                                  |
| `resourceSize`       | The size occupied by the empty state                                                                                                  |
| `lightResource`      | The empty state resource to display when is the light theme used                                                                      |
| `darkResource`       | The empty state resource to display when is the dark theme used                                                                       |
| `useDarkResource`    | Whether to use the `lightResource` or the `darkResource` one                                                                          |
| `contentDescription` | The content description                                                                                                               |
| `verticalSpacing`    | The vertical spacing applied to the `title` and `subTitle` texts                                                                      |
| `title`              | Not mandatory representative title                                                                                                    |
| `titleStyle`         | The style to apply to the `title`                                                                                                     |
| `subTitle`           | Not mandatory representative subtitle                                                                                                 |
| `subTitleStyle`      | The style to apply to the `subTitle`                                                                                                  |
| `action`             | Custom content used to allow the user to react to the empty state shown as needed, for example create new item, change search, etc... |
