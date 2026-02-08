The `EmptyStateComponent` annotation is used to mark a custom standalone composable that wraps and implements
an [EmptyState](../Components/EmptyState.md)

It indicates that the annotated composable provides the specific,
custom details (resources, styles, layout, behavior) for an `EmptyState`,
improving code readability and making the underlying `EmptyState` implementation explicit and self-descriptive.

## Usage

```kotlin
@Composable
@EmptyStateComponent
fun MyEmptyState() {
    val title = stringResource(Res.string.my_title)
    EmptyState(
        containerModifier = // customModifier,
            resourceSize = // custom sizing,
                lightResource = Res.drawable.light_resource,
        darkResource = Res.drawable.dark_resource,
        useDarkResource = // custom theming logic,
            title = title,
        titleStyle = EmptyStateTitleStyle,
        contentDescription = title
    )
}
```