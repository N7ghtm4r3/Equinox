package com.tecknobit.equinoxcompose.annotations

import kotlin.annotation.AnnotationTarget.*

/**
 * The `ScreenSection` annotation is useful for indicating an inner section of an
 * [com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen]
 * or [com.tecknobit.equinoxcompose.session.screens.EquinoxScreen] that specializes in a smaller part of the entire screen
 *
 * #### Usage example
 * ```kotlin
 * class TestScreen : EquinoxScreen<EquinoxViewModel>() {
 *
 *     @Composable
 *     override fun ArrangeScreenContent() {
 *         Title()
 *     }
 *
 *     @Composable
 *     @ScreenSection(
 *         description = """
 *         A not mandatory description about the purpose of the section
 *         """
 *     )
 *     private fun Title() {
 *         // the content of the section
 *     }
 *
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.1
 */
@Target(allowedTargets = [FUNCTION, TYPE, TYPE_PARAMETER, PROPERTY_GETTER])
@Retention(AnnotationRetention.SOURCE)
annotation class ScreenSection(

    /**
     * The description about the purpose of the section
     */
    val description: String = "",

    )
