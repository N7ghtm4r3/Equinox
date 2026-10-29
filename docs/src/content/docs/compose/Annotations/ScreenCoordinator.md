---
title: "ScreenCoordinator"
slug: "compose/Annotations/ScreenCoordinator"
---

This annotation is useful to indicate a customization of an [EquinoxNoModelScreen](/Equinox/compose/APIs/EquinoxScreens/#equinoxnomodelscreen)
or [EquinoxScreen](/Equinox/compose/APIs/EquinoxScreens/#equinoxscreen) that provides a basic behavior that all the screens
must have, for example a screen of an application which has a specific layout architecture that all other screens of
the application must have

## Usage

```kotlin
@ScreenCoordinator
abstract class MyScreenApplication(
    private val title: String
) : EquinoxNoModelScreen() {

    @Composable
    override fun ArrangeScreenContent() {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = title
            )
            MyCustomScreenContent()
        }
    }

    @Composable
    abstract fun ColumnScope.MyCustomScreenContent()

}
```