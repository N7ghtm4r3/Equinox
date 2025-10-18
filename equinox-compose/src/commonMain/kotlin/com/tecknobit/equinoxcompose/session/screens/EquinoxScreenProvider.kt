package com.tecknobit.equinoxcompose.session.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Method used to create an instance of an [EquinoxNoModelScreen] and remember it across the recompositions
 *
 * @param factory The factory used to create the screen's instance
 * @param T The type of the screen to create and remember
 *
 * @return the screen's instance as [T]
 *
 * @since 1.1.5
 */
@Composable
inline fun <T : EquinoxNoModelScreen> equinoxScreen(
    crossinline factory: () -> T,
): T {
    return remember { factory() }
}