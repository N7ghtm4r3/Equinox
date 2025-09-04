package com.tecknobit.equinoxcompose.annotations

import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import kotlin.reflect.KClass

/**
 * The `DestinationScreen` annotation is useful to indicate which [EquinoxNoModelScreen] a navigation method reaches.
 * Its purpose is to improve the readability of the code
 *
 * #### Usage example
 *
 * ```kotlin
 * // Home extends EquinoxNoModelScreen
 * @DestinationScreen(Home::class)
 * fun navToHomeScreen() {
 *     // your logic of the navigation method
 * }
 * ```
 *
 * @property destination The screen to which the method navigates
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxNoModelScreen
 * @see com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
 * 
 * @since 1.1.6
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(value = AnnotationRetention.SOURCE)
annotation class DestinationScreen(
    val destination: KClass<out EquinoxNoModelScreen>
)