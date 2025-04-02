package com.tecknobit.equinoxcompose.utilities

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium
import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.*
import com.tecknobit.equinoxcore.annotations.Returner

/**
 * `ResponsiveClass` list of the available responsive content classes
 */
enum class ResponsiveClass {

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
         * Method used to categorize the content to display based of the size classes currently the device has
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
 * The `LayoutCoordinator` annotation is useful to indicate the components which are just coordinator of the specific
 * components designed for the specific size [classes]
 *
 * #### Usage example
 *
 * ```kotlin
 * @Composable
 * @NonRestartableComposable
 * @LayoutCoordinator(
 *      classes = [EXPANDED_CONTENT, MEDIUM_CONTENT, MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT] // not mandatory
 * )
 * fun Passwords() {
 *     ResponsiveContent(
 *         onExpandedSizeClass = {
 *             PasswordsGrid()
 *         },
 *         onMediumSizeClass = {
 *             PasswordsGrid()
 *         },
 *         onMediumWidthExpandedHeight = {
 *             PasswordsList()
 *         },
 *         onCompactSizeClass = {
 *             PasswordsList()
 *         }
 *     )
 * }
 *
 * @Composable
 * @NonRestartableComposable
 * @ResponsiveClassComponent(
 *     classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
 * )
 * private fun PasswordsGrid() {
 *     // the specific code of the component
 * }
 *
 * @Composable
 * @NonRestartableComposable
 * @ResponsiveClassComponent(
 *     classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
 * )
 * private fun PasswordsList() {
 *     // the specific code of the component
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.0
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(value = AnnotationRetention.SOURCE)
annotation class LayoutCoordinator(

    /**
     * The specific size classes which the coordinator has to manage
     */
    val classes: Array<ResponsiveClass> = [],
)

/**
 * The `ExpandedClassComponent` annotation is useful to indicate the components which are shown on those devices which
 * belong to the [Expanded] class
 *
 * ### Note:
 * This annotation is based only on the [WindowWidthSizeClass] categorization, to include also the [WindowHeightSizeClass]
 * please use the [ResponsiveClassComponent] annotation instead
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 *
 * @see MediumClassComponent
 * @see CompactClassComponent
 * @see ResponsiveClassComponent
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.FILE])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ExpandedClassComponent

/**
 * The `MediumClassComponent` annotation is useful to indicate the components which are shown on those devices which
 * belong to the [Medium] class
 *
 * ### Note:
 * This annotation is based only on the [WindowWidthSizeClass] categorization, to include also the [WindowHeightSizeClass]
 * please use the [ResponsiveClassComponent] annotation instead
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 *
 * @see ExpandedClassComponent
 * @see CompactClassComponent
 * @see ResponsiveClassComponent
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.FILE])
@Retention(value = AnnotationRetention.SOURCE)
annotation class MediumClassComponent

/**
 * The `CompactClassComponent` annotation is useful to indicate the components which are shown on those devices which
 * belong to the [WindowWidthSizeClass.Compact] class
 *
 * ### Note:
 * This annotation is based only on the [WindowWidthSizeClass] categorization, to include also the [WindowHeightSizeClass]
 * please use the [ResponsiveClassComponent] annotation instead
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 *
 * @see ExpandedClassComponent
 * @see MediumClassComponent
 * @see ResponsiveClassComponent
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.FILE])
@Retention(value = AnnotationRetention.SOURCE)
annotation class CompactClassComponent

/**
 * The `ResponsiveClassComponent` annotation is useful to indicate the components which are shown on those devices which
 * belong to the specified [classes]. This annotation can include both the [WindowWidthSizeClass] and [WindowHeightSizeClass]
 * categorizations
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 *
 * @see ExpandedClassComponent
 * @see MediumClassComponent
 * @see CompactClassComponent
 */
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.FILE])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ResponsiveClassComponent(

    /**
     * The specific size classes where the component is shown
     */
    val classes: Array<ResponsiveClass>,

    )

/**
 * Method used to get the current window size class of the device
 *
 * @return the current window size class of the device as [WindowSizeClass]
 */
@Composable
expect fun currentSizeClass(): WindowSizeClass

/**
 * Method used to get the current window width size class of the device
 *
 * @return the current window width size class of the device as [WindowWidthSizeClass]
 */
@Composable
fun currentWidthClass(): WindowWidthSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.widthSizeClass
}

/**
 * Method used to get the current window height size class of the device
 *
 * @return the current window height size class of the device as [WindowHeightSizeClass]
 */
@Composable
fun currentHeightClass(): WindowHeightSizeClass {
    val currentSize = currentSizeClass()
    return currentSize.heightSizeClass
}

/**
 * Displays the correct content based on the current [ResponsiveClass] of the window
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
fun ResponsiveContent(
    onExpandedSizeClass: @Composable () -> Unit,
    onExpandedWidthMediumHeight: (@Composable () -> Unit)? = onExpandedSizeClass,
    onExpandedWidthCompactHeight: (@Composable () -> Unit)? = onExpandedSizeClass,
    onMediumSizeClass: @Composable () -> Unit,
    onMediumWidthExpandedHeight: (@Composable () -> Unit)? = onMediumSizeClass,
    onMediumWidthCompactHeight: (@Composable () -> Unit)? = onMediumSizeClass,
    onCompactSizeClass: @Composable () -> Unit,
    onCompactWidthExpandedHeight: (@Composable () -> Unit)? = onCompactSizeClass,
    onCompactWidthMediumHeight: (@Composable () -> Unit)? = onCompactSizeClass,
) {
    val responsiveClass = calculateResponsiveClass()
    when (responsiveClass) {
        EXPANDED_CONTENT -> onExpandedSizeClass()
        EXPANDED_MEDIUM_CONTENT -> onExpandedWidthMediumHeight?.invoke()
        EXPANDED_COMPACT_CONTENT -> onExpandedWidthCompactHeight?.invoke()
        MEDIUM_CONTENT -> onMediumSizeClass()
        MEDIUM_EXPANDED_CONTENT -> onMediumWidthExpandedHeight?.invoke()
        MEDIUM_COMPACT_CONTENT -> onMediumWidthCompactHeight?.invoke()
        COMPACT_CONTENT -> onCompactSizeClass()
        COMPACT_EXPANDED_CONTENT -> onCompactWidthExpandedHeight?.invoke()
        COMPACT_MEDIUM_CONTENT -> onCompactWidthMediumHeight?.invoke()
    }
}

/**
 * Executes an action based on the current [ResponsiveClass] of the window
 *
 * @param onExpandedSizeClass The action to execute when the device's window currently belongs to expanded class
 * @param onExpandedWidthMediumHeight The action to execute when the device's window currently belongs to expanded width
 * class and medium height class
 * @param onExpandedWidthCompactHeight The action to execute when the device's window currently belongs to expanded width
 * class and compact height class
 * @param onMediumSizeClass The action to execute when the device's window currently belongs to medium class
 * @param onMediumWidthExpandedHeight The action to execute when the device's window currently belongs to medium width
 * class and expanded height class
 * @param onMediumWidthCompactHeight The action to execute when the device's window currently belongs to medium width
 * class and compact height class
 * @param onCompactSizeClass The action to execute when the device's window currently belongs to compact class
 * @param onCompactWidthExpandedHeight The action to execute when the device's window currently belongs to compact width
 * class and expanded height class
 * @param onCompactWidthMediumHeight The action to execute when the device's window currently belongs to compact width
 * class and medium height class
 */
@Composable
fun responsiveAction(
    onExpandedSizeClass: () -> Unit,
    onExpandedWidthMediumHeight: (() -> Unit)? = onExpandedSizeClass,
    onExpandedWidthCompactHeight: (() -> Unit)? = onExpandedSizeClass,
    onMediumSizeClass: () -> Unit,
    onMediumWidthExpandedHeight: (() -> Unit)? = onMediumSizeClass,
    onMediumWidthCompactHeight: (() -> Unit)? = onMediumSizeClass,
    onCompactSizeClass: () -> Unit,
    onCompactWidthExpandedHeight: (() -> Unit)? = onCompactSizeClass,
    onCompactWidthMediumHeight: (() -> Unit)? = onCompactSizeClass,
) {
    val responsiveClass = calculateResponsiveClass()
    when (responsiveClass) {
        EXPANDED_CONTENT -> onExpandedSizeClass()
        EXPANDED_MEDIUM_CONTENT -> onExpandedWidthMediumHeight?.invoke()
        EXPANDED_COMPACT_CONTENT -> onExpandedWidthCompactHeight?.invoke()
        MEDIUM_CONTENT -> onMediumSizeClass()
        MEDIUM_EXPANDED_CONTENT -> onMediumWidthExpandedHeight?.invoke()
        MEDIUM_COMPACT_CONTENT -> onMediumWidthCompactHeight?.invoke()
        COMPACT_CONTENT -> onCompactSizeClass()
        COMPACT_EXPANDED_CONTENT -> onCompactWidthExpandedHeight?.invoke()
        COMPACT_MEDIUM_CONTENT -> onCompactWidthMediumHeight?.invoke()
    }
}

/**
 * Assigns a specific value based on the current [ResponsiveClass] of the window
 *
 * @param onExpandedSizeClass The value to assign when the device's window currently belongs to expanded class
 * @param onExpandedWidthMediumHeight The value to assign when the device's window currently belongs to expanded width
 * class and medium height class
 * @param onExpandedWidthCompactHeight The value to assign when the device's window currently belongs to expanded width
 * class and compact height class
 * @param onMediumSizeClass The value to assign when the device's window currently belongs to medium class
 * @param onMediumWidthExpandedHeight The value to assign when the device's window currently belongs to medium width
 * class and expanded height class
 * @param onMediumWidthCompactHeight The value to assign when the device's window currently belongs to medium width
 * class and compact height class
 * @param onCompactSizeClass The value to assign when the device's window currently belongs to compact class
 * @param onCompactWidthExpandedHeight The value to assign when the device's window currently belongs to compact width
 * class and expanded height class
 * @param onCompactWidthMediumHeight The value to assign when the device's window currently belongs to compact width
 * class and medium height class
 */
@Composable
fun <T> responsiveAssignment(
    onExpandedSizeClass: () -> T,
    onExpandedWidthMediumHeight: (() -> T) = onExpandedSizeClass,
    onExpandedWidthCompactHeight: (() -> T) = onExpandedSizeClass,
    onMediumSizeClass: () -> T,
    onMediumWidthExpandedHeight: (() -> T) = onMediumSizeClass,
    onMediumWidthCompactHeight: (() -> T) = onMediumSizeClass,
    onCompactSizeClass: () -> T,
    onCompactWidthExpandedHeight: (() -> T) = onCompactSizeClass,
    onCompactWidthMediumHeight: (() -> T) = onCompactSizeClass,
): T {
    val responsiveClass = calculateResponsiveClass()
    return when (responsiveClass) {
        EXPANDED_CONTENT -> onExpandedSizeClass()
        EXPANDED_MEDIUM_CONTENT -> onExpandedWidthMediumHeight()
        EXPANDED_COMPACT_CONTENT -> onExpandedWidthCompactHeight()
        MEDIUM_CONTENT -> onMediumSizeClass()
        MEDIUM_EXPANDED_CONTENT -> onMediumWidthExpandedHeight()
        MEDIUM_COMPACT_CONTENT -> onMediumWidthCompactHeight()
        COMPACT_CONTENT -> onCompactSizeClass()
        COMPACT_EXPANDED_CONTENT -> onCompactWidthExpandedHeight()
        COMPACT_MEDIUM_CONTENT -> onCompactWidthMediumHeight()
    }
}

/**
 * Method used to calculate the current [ResponsiveClass] based on the screen dimension
 *
 * @return the current responsive class as [ResponsiveClass]
 */
@Returner
@Composable
private fun calculateResponsiveClass(): ResponsiveClass {
    val sizeClass = currentSizeClass()
    return ResponsiveClass.getResponsiveClass(
        widthSizeClass = sizeClass.widthSizeClass,
        heightSizeClass = sizeClass.heightSizeClass
    )
}