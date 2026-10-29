---
title: "ScreenSection"
slug: "compose/Annotations/ScreenSection"
---

This annotation is useful for indicating an inner section of an [EquinoxNoModelScreen](/Equinox/compose/APIs/EquinoxScreens/#equinoxnomodelscreen)
or [EquinoxScreen](/Equinox/compose/APIs/EquinoxScreens/#equinoxscreen)

## Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        Title()
    }

    @Composable
    @ScreenSection(
        description = """
            A not mandatory description about the purpose of the section
        """
    )
    private fun Title() {
        // the content of the section
    }

}
```