package com.tecknobit.equinoxnavigation

import androidx.compose.ui.graphics.vector.ImageVector

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
    val title: String,
    val icon: ImageVector,
    val contentDescription: String,
)