package com.tecknobit.equinoxcompose.utilities

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium
import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.*

/**
 * `ResponsiveClass` list of the available responsive content classes
 */
internal enum class ResponsiveClass {

    /**
     * `EXPANDED_CONTENT` represents the content to be displayed on a device
     * that belongs to an expanded width class and an expanded height class
     */
    EXPANDED_CONTENT,

    /**
     * `EXPANDED_MEDIUM_CONTENT` represents the content to be displayed on a device
     * that belongs to an expanded width class and a medium height class
     */
    EXPANDED_MEDIUM_CONTENT,

    /**
     * `EXPANDED_COMPACT_CONTENT` represents the content to be displayed on a device
     * that belongs to an expanded width class and a compact height class
     */
    EXPANDED_COMPACT_CONTENT,

    /**
     * `MEDIUM_CONTENT` represents the content to be displayed on a device
     * that belongs to a medium width class and a medium height class
     */
    MEDIUM_CONTENT,

    /**
     * `MEDIUM_CONTENT` represents the content to be displayed on a device
     * that belongs to a medium width class and an expanded height class
     */
    MEDIUM_EXPANDED_CONTENT,

    /**
     * `MEDIUM_COMPACT_CONTENT` represents the content to be displayed on a device
     * that belongs to a medium width class and a compact height class
     */
    MEDIUM_COMPACT_CONTENT,

    /**
     * `COMPACT_CONTENT` represents the content to be displayed on a device
     * that belongs to a compact width class and a compact height class
     */
    COMPACT_CONTENT,

    /**
     * `COMPACT_EXPANDED_CONTENT` represents the content to be displayed on a device
     * that belongs to a compact width class and an expanded height class
     */
    COMPACT_EXPANDED_CONTENT,

    /**
     * `COMPACT_MEDIUM_CONTENT` represents the content to be displayed on a device
     * that belongs to a compact width class and a medium height class
     */
    COMPACT_MEDIUM_CONTENT;

    companion object {

        /**
         * Method to categorize the content to display based of the size classes currently the device has
         *
         * @param widthSizeClass The current width class of the window
         * @param heightSizeClass The current height class of the window
         *
         * @return the responsive class as [ResponsiveClass]
         */
        fun getResponsiveClass(
            widthSizeClass: WindowWidthSizeClass,
            heightSizeClass: WindowHeightSizeClass,
        ): ResponsiveClass {
            return when (widthSizeClass) {
                Expanded -> {
                    when (heightSizeClass) {
                        WindowHeightSizeClass.Expanded -> EXPANDED_CONTENT
                        WindowHeightSizeClass.Medium -> EXPANDED_MEDIUM_CONTENT
                        else -> EXPANDED_COMPACT_CONTENT
                    }
                }

                Medium -> {
                    when (heightSizeClass) {
                        WindowHeightSizeClass.Expanded -> MEDIUM_EXPANDED_CONTENT
                        WindowHeightSizeClass.Medium -> MEDIUM_CONTENT
                        else -> MEDIUM_COMPACT_CONTENT
                    }
                }

                else -> {
                    when (heightSizeClass) {
                        WindowHeightSizeClass.Expanded -> COMPACT_EXPANDED_CONTENT
                        WindowHeightSizeClass.Medium -> COMPACT_MEDIUM_CONTENT
                        else -> COMPACT_CONTENT
                    }
                }
            }
        }

    }

}

/**
 * Method to get the current window size class of the device
 *
 * @return the current window size class of the device as [WindowSizeClass]
 */
@Composable
@ExperimentalMultiplatform
expect fun currentSizeClass(): WindowSizeClass

/**
 * Method to get the current window width size class of the device
 *
 * @return the current window width size class of the device as [WindowWidthSizeClass]
 */
@Composable
@ExperimentalMultiplatform
fun currentWidthClass(): WindowWidthSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.widthSizeClass
}

/**
 * Method to get the current window height size class of the device
 *
 * @return the current window height size class of the device as [WindowHeightSizeClass]
 */
@Composable
@ExperimentalMultiplatform
fun currentHeightClass(): WindowHeightSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.heightSizeClass
}

/**
 * Display the correct content based on the current [ResponsiveClass] of the window.
 *
 * It requires the implementation of the official [org.jetbrains.compose.material3:material3-window-size-class](https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-170.html#material3-material3-window-size-class)
 * library
 *
 * @param onExpandedSizeClass The content to display when the device's window currently belongs to expanded class
 * @param onExpandedWidthMediumHeight The content to display when the device's window currently belongs to expanded width
 * class and medium height class
 * @param onExpandedWidthCompactHeight The content to display when the device's window currently belongs to expanded width
 * class and compact height class
 * @param onMediumSizeClass The content to display when the device's window currently belongs to medium class
 * @param onMediumWidthExpandedHeight The content to display when the device's window currently belongs to medium width
 * class and expanded height class
 * @param onMediumWidthCompactHeight The content to display when the device's window currently belongs to medium width
 * class and compact height class
 * @param onCompactSizeClass The content to display when the device's window currently belongs to compact class
 * @param onCompactWidthExpandedHeight The content to display when the device's window currently belongs to compact width
 * class and expanded height class
 * @param onCompactWidthMediumHeight The content to display when the device's window currently belongs to compact width
 * class and medium height class
 */
@Composable
@ExperimentalMultiplatform
fun ResponsiveContent(
    onExpandedSizeClass: @Composable () -> Unit,
    onExpandedWidthMediumHeight: (@Composable () -> Unit)? = null,
    onExpandedWidthCompactHeight: (@Composable () -> Unit)? = null,
    onMediumSizeClass: @Composable () -> Unit,
    onMediumWidthExpandedHeight: (@Composable () -> Unit)? = null,
    onMediumWidthCompactHeight: (@Composable () -> Unit)? = null,
    onCompactSizeClass: @Composable () -> Unit,
    onCompactWidthExpandedHeight: (@Composable () -> Unit)? = null,
    onCompactWidthMediumHeight: (@Composable () -> Unit)? = null,
) {
    val sizeClass = currentSizeClass()
    val responsiveClass = ResponsiveClass.getResponsiveClass(
        widthSizeClass = sizeClass.widthSizeClass,
        heightSizeClass = sizeClass.heightSizeClass
    )
    when (responsiveClass) {
        EXPANDED_CONTENT -> onExpandedSizeClass()
        EXPANDED_MEDIUM_CONTENT -> (onExpandedWidthMediumHeight ?: onExpandedSizeClass).invoke()
        EXPANDED_COMPACT_CONTENT -> (onExpandedWidthCompactHeight ?: onExpandedSizeClass).invoke()
        MEDIUM_CONTENT -> onMediumSizeClass()
        MEDIUM_EXPANDED_CONTENT -> (onMediumWidthExpandedHeight ?: onMediumSizeClass).invoke()
        MEDIUM_COMPACT_CONTENT -> (onMediumWidthCompactHeight ?: onMediumSizeClass).invoke()
        COMPACT_CONTENT -> onCompactSizeClass()
        COMPACT_EXPANDED_CONTENT -> (onCompactWidthExpandedHeight ?: onCompactSizeClass).invoke()
        COMPACT_MEDIUM_CONTENT -> (onCompactWidthMediumHeight ?: onCompactSizeClass).invoke()
    }
}