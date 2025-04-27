package com.tecknobit.equinoxcompose.annotations

/**
 * The `ScreenCoordinator` annotation is useful to indicate a customization of an
 * [com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen]
 * or [com.tecknobit.equinoxcompose.session.screens.EquinoxScreen] that provides a basic behavior that all the screens
 * must have, for example a screen of an application which has a specific layout architecture that all other screens of
 * the application must have
 *
 * #### Usage example
 *
 * ```kotlin
 * @ScreenCoordinator
 * abstract class MyScreenApplication(
 *    private val title: String
 * ) : EquinoxNoModelScreen() {
 *
 *     @Composable
 *     override fun ArrangeScreenContent() {
 *         Column (
 *             modifier = Modifier
 *                 .fillMaxSize()
 *         ) {
 *             Text(
 *                 text = title
 *             )
 *             MyCustomScreenContent()
 *         }
 *     }
 *
 *     @Composable
 *     abstract fun ColumnScope.MyCustomScreenContent()
 *
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.1
 */
@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ScreenCoordinator