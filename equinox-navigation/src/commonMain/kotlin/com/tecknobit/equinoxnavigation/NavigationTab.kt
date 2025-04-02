package com.tecknobit.equinoxnavigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

interface NavigatorTab<T> {

    val title: T

    val icon: ImageVector

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
 * @since 1.0.0
 */
data class NavigationTab(
    override val title: String,
    override val icon: ImageVector,
    override val contentDescription: String,
) : NavigatorTab<String>

data class I18nNavigationTab(
    override val title: StringResource,
    override val icon: ImageVector,
    override val contentDescription: String,
) : NavigatorTab<StringResource>