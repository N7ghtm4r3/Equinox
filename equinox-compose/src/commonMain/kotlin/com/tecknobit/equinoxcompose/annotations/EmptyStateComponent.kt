package com.tecknobit.equinoxcompose.annotations

/**
 * The `EmptyStateComponent` annotation is used to mark a custom standalone
 * composable that wraps and implements an [com.tecknobit.equinoxcompose.components.EmptyState].
 *
 * It indicates that the annotated composable provides the specific,
 * custom details (resources, styles, layout, behavior) for an `EmptyState`,
 * improving code readability and making the underlying `EmptyState`
 * implementation explicit and self-descriptive.
 *
 * #### Usage example
 *
 * ```kotlin
 * @Composable
 * @EmptyStateComponent
 * fun MyEmptyState() {
 *     val title = stringResource(Res.string.my_title)
 *     EmptyState(
 *         containerModifier = // customModifier,
 *         resourceSize = // custom sizing,
 *         lightResource = Res.drawable.light_resource,
 *         darkResource = Res.drawable.dark_resource,
 *         useDarkResource = // custom theming logic,
 *         title = title,
 *         titleStyle = EmptyStateTitleStyle,
 *         contentDescription = title
 *     )
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see com.tecknobit.equinoxcompose.components.EmptyState
 * 
 * @since 1.1.9
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(value = AnnotationRetention.SOURCE)
annotation class EmptyStateComponent