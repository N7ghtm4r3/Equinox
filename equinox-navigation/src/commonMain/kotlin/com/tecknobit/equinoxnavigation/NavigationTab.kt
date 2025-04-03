package com.tecknobit.equinoxnavigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

/**
 * The `NavigatorTab` interface allows to customize the tabs the [NavigatorScreen] have to use to handle the navigation
 * inside the application
 *
 * @param T The type for the [title] property
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.0
 */
interface NavigatorTab<T> {

    /**
     *`title` the title of the tab
     */
    val title: T

    /**
     *`icon` the representative icon of the tab
     */
    val icon: ImageVector

    /**
     *`contentDescription` the content description for the accessibility
     */
    val contentDescription: String

}

/**
 * The `NavigationTab` data class represents the information about a tab used to navigate between the screens
 * of the application
 *
 * @param title The title of the tab
 * @param icon The representative icon of the tab
 * @param contentDescription The content description for the accessibility
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see NavigatorTab
 *
 * @since 1.0.0
 */
data class NavigationTab(
    override val title: String,
    override val icon: ImageVector,
    override val contentDescription: String,
) : NavigatorTab<String>

/**
 * The `I18nNavigationTab` data class represents the information about a tab used to navigate between the screens
 * of the application, it allows the I18n string for the [title] using the [StringResource]
 *
 * @param title The title of the tab
 * @param icon The representative icon of the tab
 * @param contentDescription The content description for the accessibility
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see NavigatorTab
 *
 * @since 1.0.0
 */
data class I18nNavigationTab(
    override val title: StringResource,
    override val icon: ImageVector,
    override val contentDescription: String,
) : NavigatorTab<StringResource>