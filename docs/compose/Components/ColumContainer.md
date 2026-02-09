Layout composable used to create a **horizontal-centered** container to place the content in the middle of the screen.
Its width is internally handled using the [responsiveMaxWidth](../APIs/EquinoxWindowKit.md#responsive-max-width) API
making the component responsive by default

## Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        ColumnContainer(
            innerModifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            // content example
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = List(10) { it }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = "Item $it"
                        )
                    }
                }
            }
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Parameter             | Description                                                        |
|-----------------------|--------------------------------------------------------------------|
| `modifier`            | The modifier to apply to the container `Card`                      |
| `outerModifier`       | The modifier to apply to the outer `Column` made-up this component |
| `innerModifier`       | The modifier to apply to the inner `Column` made-up this component |
| `verticalArrangement` | The vertical arrangement of the `content`                          |

## Appearance

### Mobile

![columncontainer-mobile](assets/images/columncontainer/columncontainer-mobile.png){ .shadow .mobile-appearance }

### Desktop & Web

![columncontainer-desktop](assets/images/columncontainer/columncontainer-desktop.png){ .shadow }