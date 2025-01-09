package com.tecknobit.equinoxcompose.utilities

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

@Composable
@ExperimentalMaterial3WindowSizeClassApi
actual fun currentSizeClass(): WindowSizeClass {
    return calculateWindowSizeClass()
}