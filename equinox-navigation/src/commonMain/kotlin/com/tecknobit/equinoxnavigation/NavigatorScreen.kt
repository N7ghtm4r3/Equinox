@file:OptIn(ExperimentalStdlibApi::class)

package com.tecknobit.equinoxnavigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.equinoxScreen
import com.tecknobit.equinoxcompose.utilities.LayoutCoordinator
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.*
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Returner
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxnavigation.NavigationMode.BOTTOM_NAVIGATION
import com.tecknobit.equinoxnavigation.NavigationMode.SIDE_NAVIGATION
import org.jetbrains.compose.resources.stringResource

/**
 * The `NavigatorScreen` provides a responsive navigation system that dynamically adjusts the navigation bars and content
 * of each screen based on the current [com.tecknobit.equinoxcompose.utilities.ResponsiveClass]
 *
 * Related documentation: [NavigatorScreen.md](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/navigation/NavigatorScreen.md)
 *
 * @property loggerEnabled Whether enabled the logging to log the events occurred in the [ShowContent] composable,
 * it is suggested to disable it in production
 *
 * @param T The type of the [NavigatorTab] used by the navigator
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxNoModelScreen
 *
 * @since 1.0.0
 *
 */
@Structure
@ExperimentalComposeApi
abstract class NavigatorScreen<T : NavigatorTab<*>>(
    loggerEnabled: Boolean = true,
) : EquinoxNoModelScreen(
    loggerEnabled = loggerEnabled
) {

    companion object {

        /**
         *`DefaultSideNavigationContentPadding` the padding values to apply when the navigation is side-arranged
         */
        val DefaultSideNavigationContentPadding = PaddingValues(
            top = 35.dp,
            start = 35.dp,
            end = 35.dp,
            bottom = 16.dp
        )

        /**
         *`DefaultBottomNavigationContentPadding` the padding values to apply when the navigation is bottom-arranged
         */
        val DefaultBottomNavigationContentPadding = PaddingValues(
            top = 25.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 96.dp
        )

    }

    /**
     *`activeNavigationTabIndex` the index of the current [NavigatorTab] displayed
     */
    protected lateinit var activeNavigationTabIndex: MutableState<Int>

    /**
     *`tabs` the reachable destinations tabs
     */
    protected val tabs: Array<T> by lazy { navigationTabs() }

    /**
     *`navigationMode` current navigation mode adopted by the navigator
     */
    private lateinit var navigationMode: MutableState<NavigationMode>

    /**
     * Method used to retrieve the tabs to assign to the [tabs] array
     *
     * @return the tabs used by the [NavigatorScreen] as [Array] of [T]
     */
    protected abstract fun navigationTabs(): Array<T>

    /**
     * Responsive navigation system based on the current [com.tecknobit.equinoxcompose.utilities.ResponsiveClass]
     *
     * @param sideBarModifier The modifier to apply to the [SideNavigationArrangement] bar
     * @param sideBarWidth The default width of the [SideNavigationArrangement] bar
     * @param bottomBarModifier The modifier to apply to the [BottomNavigationArrangement] bar
     * @param navigationBarColor The color to apply to the navigation bars
     * @param backgroundTab The color to apply as background of the tabs
     */
    @Composable
    @LayoutCoordinator
    protected fun NavigationContent(
        sideBarModifier: Modifier = Modifier,
        sideBarWidth: Dp = 185.dp,
        bottomBarModifier: Modifier = Modifier,
        navigationBarColor: Color = BottomAppBarDefaults.containerColor,
        backgroundTab: Color = MaterialTheme.colorScheme.background,
    ) {
        ResponsiveContent(
            onExpandedSizeClass = {
                SideNavigationArrangement(
                    modifier = sideBarModifier,
                    sideBarWidth = sideBarWidth,
                    navigationBarColor = navigationBarColor,
                    backgroundTab = backgroundTab
                )
            },
            onMediumSizeClass = {
                SideNavigationArrangement(
                    modifier = sideBarModifier,
                    sideBarWidth = sideBarWidth,
                    navigationBarColor = navigationBarColor,
                    backgroundTab = backgroundTab
                )
            },
            onMediumWidthExpandedHeight = {
                BottomNavigationArrangement(
                    modifier = bottomBarModifier,
                    navigationBarColor = navigationBarColor,
                    backgroundTab = backgroundTab
                )
            },
            onCompactSizeClass = {
                BottomNavigationArrangement(
                    modifier = bottomBarModifier,
                    navigationBarColor = navigationBarColor,
                    backgroundTab = backgroundTab
                )
            }
        )
    }

    /**
     * Custom [NavigationRail] displayed on the [EXPANDED_CONTENT] and [MEDIUM_CONTENT] responsive screen classes
     *
     * @param modifier The modifier to apply to the navigation bar
     * @param sideBarWidth The default width of the navigation bar
     * @param navigationBarColor The color to apply to the navigation bars
     * @param backgroundTab The color to apply as background of the tabs
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    private fun SideNavigationArrangement(
        modifier: Modifier,
        sideBarWidth: Dp,
        navigationBarColor: Color,
        backgroundTab: Color,
    ) {
        navigationMode.value = SIDE_NAVIGATION
        Row {
            NavigationRail(
                modifier = modifier
                    .width(sideBarWidth),
                containerColor = navigationBarColor,
                header = { SideNavigationHeaderContent() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2.7f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            SideNavigationItem(
                                index = index,
                                tab = tab
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                vertical = 16.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        content = { SideNavigationFooterContent() }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(backgroundTab)
            ) {
                ScreenTabContent(
                    paddingValues = sideNavigationContentPadding()
                )
            }
        }
    }

    /**
     * Method used to customize the padding values to apply when the navigation is side-arranged
     *
     * @return the padding values to apply as [PaddingValues]
     */
    @Returner
    protected open fun sideNavigationContentPadding(): PaddingValues {
        return DefaultSideNavigationContentPadding
    }

    /**
     * Custom header content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun ColumnScope.SideNavigationHeaderContent() {
    }

    /**
     * Custom footer content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun ColumnScope.SideNavigationFooterContent() {
    }

    /**
     * The navigation item of the [SideNavigationArrangement] bar
     *
     * @param index The index related to the item in the [tabs] array
     * @param tab The related tab of the [index]
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun SideNavigationItem(
        index: Int,
        tab: T,
    ) {
        NavigationDrawerItem(
            modifier = Modifier
                .padding(
                    end = 10.dp
                ),
            selected = index == activeNavigationTabIndex.value,
            onClick = { activeNavigationTabIndex.value = index },
            shape = RoundedCornerShape(
                topEnd = 10.dp,
                bottomEnd = 10.dp
            ),
            colors = sideNavigationItemColors(),
            icon = {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.resolveContentDescription()
                )
            },
            label = {
                Text(
                    text = tab.resolveTitle(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }

    /**
     * Method used to customize the colors of the [SideNavigationItem] component
     *
     * @return the color to apply to the navigation item as [NavigationDrawerItemColors]
     */
    @Returner
    @Composable
    protected open fun sideNavigationItemColors(): NavigationDrawerItemColors {
        return NavigationDrawerItemDefaults.colors()
    }

    /**
     * Custom [BottomAppBar] displayed on the [MEDIUM_EXPANDED_CONTENT] and [COMPACT_CONTENT] responsive screen classes
     *
     * @param modifier The modifier to apply to the navigation bar
     * @param navigationBarColor The color to apply to the navigation bars
     * @param backgroundTab The color to apply as background of the tabs
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
    )
    private fun BottomNavigationArrangement(
        modifier: Modifier,
        navigationBarColor: Color,
        backgroundTab: Color,
    ) {
        navigationMode.value = BOTTOM_NAVIGATION
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTab)
        ) {
            ScreenTabContent(
                paddingValues = bottomNavigationContentPadding()
            )
            BottomAppBar(
                modifier = modifier
                    .align(Alignment.BottomCenter),
                containerColor = navigationBarColor
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        index = index,
                        tab = tab
                    )
                }
            }
        }
    }

    /**
     * Method used to customize the padding values to apply when the navigation is bottom-arranged
     *
     * @return the padding values to apply as [PaddingValues]
     */
    @Returner
    protected open fun bottomNavigationContentPadding(): PaddingValues {
        return DefaultBottomNavigationContentPadding
    }

    /**
     * The navigation item of the [BottomNavigationItem] bar
     *
     * @param index The index related to the item in the [tabs] array
     * @param tab The related tab of the [index]
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
    )
    protected open fun RowScope.BottomNavigationItem(
        index: Int,
        tab: T,
    ) {
        NavigationBarItem(
            selected = index == activeNavigationTabIndex.value,
            onClick = { activeNavigationTabIndex.value = index },
            icon = {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.resolveContentDescription()
                )
            },
            colors = bottomNavigationItemColors(),
            label = {
                Text(
                    text = tab.resolveTitle(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }

    /**
     * Method used to customize the colors of the [BottomNavigationItem] component
     *
     * @return the color to apply to the navigation item as [NavigationBarItemColors]
     */
    @Returner
    @Composable
    protected open fun bottomNavigationItemColors(): NavigationBarItemColors {
        return NavigationBarItemDefaults.colors()
    }

    /**
     * Method used to resolve the [NavigationTab.title] value to display on the [SideNavigationItem] or
     * [BottomNavigationItem] based on the [NavigatorTab] used by the navigator
     *
     * @return the title for the tab as [String]
     *
     * @since 1.0.3
     */
    @Composable
    protected open fun NavigatorTab<*>.resolveTitle(): String {
        return when (this) {
            is NavigationTab -> title
            is I18nNavigationTab -> stringResource(title)
            else -> title.toString()
        }
    }

    /**
     * Method used to resolve the [NavigationTab.contentDescription] value to display on the [SideNavigationItem] or
     * [BottomNavigationItem] based on the [NavigatorTab] used by the navigator
     *
     * @return the title for the tab as [String]
     *
     * @since 1.0.3
     */
    @Composable
    protected open fun NavigatorTab<*>.resolveContentDescription(): String {
        return when (this) {
            is NavigationTab -> contentDescription
            is I18nNavigationTab -> stringResource(contentDescription)
            else -> contentDescription.toString()
        }
    }

    /**
     * The content of the tab
     *
     * @param paddingValues The values of the padding to apply to the content
     */
    @Composable
    private fun ScreenTabContent(
        paddingValues: PaddingValues,
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Column {
                AnimatedContent(
                    targetState = activeNavigationTabIndex.value
                ) { activeIndex ->
                    val tab = equinoxScreen { activeIndex.tabContent() }
                    tab.ShowContent()
                }
            }
        }
    }

    /**
     * Method used to instantiate the related screen based on the current [activeNavigationTabIndex]
     *
     * @return the screen as [EquinoxNoModelScreen]
     */
    protected abstract fun Int.tabContent(): EquinoxNoModelScreen

    /**
     * Method used to check whether the current [navigationMode] is [SIDE_NAVIGATION]
     *
     * @return whether the navigation mode is [SIDE_NAVIGATION] as [Boolean]
     */
    protected fun isSideNavigationMode(): Boolean {
        return navigationMode.value == SIDE_NAVIGATION
    }

    /**
     * Method used to check whether the current [navigationMode] is [BOTTOM_NAVIGATION]
     *
     * @return whether the navigation mode is [BOTTOM_NAVIGATION] as [Boolean]
     */
    protected fun isBottomNavigationMode(): Boolean {
        return navigationMode.value == BOTTOM_NAVIGATION
    }

    /**
     * Method used to collect or instantiate the states of the screen
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        activeNavigationTabIndex = rememberSaveable { mutableStateOf(0) }
        navigationMode = rememberSaveable { mutableStateOf(BOTTOM_NAVIGATION) }
    }

}