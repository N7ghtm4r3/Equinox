Custom tab selector allows to select the tab to display

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

            // define your tabs
            val tabs = listOf(
                TabDetails(
                    icon = Icons.Default.Home,
                    tabTitle = "Home"
                ),
                TabDetails(
                    icon = Icons.Default.Info,
                    tabTitle = "About"
                ),
                TabDetails(
                    icon = Icons.Default.Contacts,
                    tabTitle = "Contacts"
                )
            )

            // create the related state
            val state = rememberTabSelectorState(
                initialTabIndex = 0 // the default index value
            )

            // create the selector
            TabSelector(
                state = state,
                tabs = tabs
            ) { tab, index ->

                // the content of each tab
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = tab.tabTitle
                    )
                }
            }
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Parameter         | Description                                                                                                                       |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `modifier`        | The modifier to apply to the component                                                                                            |
| `initialTabShape` | The shape to use for the first tab of the selector                                                                                |
| `middleShape`     | The shape to use for the middle tabs of the selector                                                                              |
| `lastTabShape`    | The shape to use for the last tab of the selector                                                                                 |
| `swipingEnabled`  | Whether the horizontal swiping gesture is enabled, if yes the `tabContent` will be displayed with the `HorizontalPager` component |
| `tabContent`      | The content of the tab                                                                                                            |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/tabselector/tabselector-android.mp4" type="video/mp4">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/tabselector/tabselector-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>