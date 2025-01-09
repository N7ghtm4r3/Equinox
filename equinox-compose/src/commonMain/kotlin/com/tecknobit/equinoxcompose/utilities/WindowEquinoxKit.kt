package com.tecknobit.equinoxcompose.utilities

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Compact
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium
import androidx.compose.runtime.Composable

// TODO: IN THE DOCU MUST WARN ABOUT THE REQUIRED IMPLEMENTATION OF THE
// implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.3")

@Composable
expect fun currentSizeClass(): WindowSizeClass

@Composable
fun currentWidthClass(): WindowWidthSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.widthSizeClass
}

@Composable
fun currentHeightClass(): WindowHeightSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.heightSizeClass
}

@Composable
fun ResponsiveContent(
    onExpandedSizeClass: @Composable () -> Unit,
    onExpandedWidthMediumHeight: (@Composable () -> Unit)? = null,
    onExpandedWidthCompactHeight: (@Composable () -> Unit)? = null,
    onMediumWidthExpandedHeight: (@Composable () -> Unit)? = null,
    onMediumSizeClass: @Composable () -> Unit,
    onMediumWidthCompactHeight: (@Composable () -> Unit)? = null,
    onCompactWidthExpandedHeight: (@Composable () -> Unit)? = null,
    onCompactWidthMediumHeight: (@Composable () -> Unit)? = null,
    onCompactSizeClass: @Composable () -> Unit,
) {
    val sizeClass = currentSizeClass()
    val widthClass = sizeClass.widthSizeClass
    val heightClass = sizeClass.heightSizeClass
    if (widthClass == Expanded && heightClass == WindowHeightSizeClass.Expanded)
        onExpandedSizeClass()
    else if (widthClass == Expanded && heightClass == WindowHeightSizeClass.Medium) {
        if (onExpandedWidthMediumHeight != null)
            onExpandedWidthMediumHeight()
        else
            onExpandedSizeClass()
    } else if (widthClass == Expanded && heightClass == WindowHeightSizeClass.Compact) {
        if (onExpandedWidthCompactHeight != null)
            onExpandedWidthCompactHeight()
        else
            onExpandedSizeClass()
    } else if (widthClass == Medium && heightClass == WindowHeightSizeClass.Expanded) {
        if (onMediumWidthExpandedHeight != null)
            onMediumWidthExpandedHeight()
        else
            onMediumSizeClass()
    } else if (widthClass == Medium && heightClass == WindowHeightSizeClass.Medium)
        onMediumSizeClass.invoke()
    else if (widthClass == Medium && heightClass == WindowHeightSizeClass.Compact) {
        if (onMediumWidthCompactHeight != null)
            onMediumWidthCompactHeight()
        else
            onMediumSizeClass()
    } else if (widthClass == Compact && heightClass == WindowHeightSizeClass.Expanded) {
        if (onCompactWidthExpandedHeight != null)
            onCompactWidthExpandedHeight()
        else
            onCompactSizeClass()
    } else if (widthClass == Compact && heightClass == WindowHeightSizeClass.Medium) {
        if (onCompactWidthMediumHeight != null)
            onCompactWidthMediumHeight()
        else
            onCompactSizeClass()
    } else if (widthClass == Compact && heightClass == WindowHeightSizeClass.Compact)
        onCompactSizeClass()
}