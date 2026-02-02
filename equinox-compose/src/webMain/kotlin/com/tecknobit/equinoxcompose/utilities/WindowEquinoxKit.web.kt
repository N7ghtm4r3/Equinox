package com.tecknobit.equinoxcompose.utilities

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

/**
 * Method used to get the current window size class of the device
 *
 * @return the current window size class of the device as [WindowSizeClass]
 */
@Composable
@ExperimentalMaterial3WindowSizeClassApi
actual fun currentSizeClass(): WindowSizeClass {
    return calculateWindowSizeClass()
}