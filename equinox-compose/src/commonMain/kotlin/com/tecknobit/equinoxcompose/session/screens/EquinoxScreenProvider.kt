package com.tecknobit.equinoxcompose.session.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
@ExperimentalStdlibApi
inline fun <reified T : EquinoxNoModelScreen> equinoxScreen(
    crossinline factory: () -> T,
): T {
    return remember { factory() }
}