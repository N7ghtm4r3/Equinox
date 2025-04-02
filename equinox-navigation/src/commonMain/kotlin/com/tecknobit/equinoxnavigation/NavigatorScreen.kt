package com.tecknobit.equinoxnavigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.utilities.LayoutCoordinator
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.*
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure

@Structure
@ExperimentalComposeApi
abstract class NavigatorScreen : EquinoxNoModelScreen() {

    /**
     *`activeNavigationTabIndex` the index of the current [NavigationTab] displayed
     */
    protected lateinit var activeNavigationTabIndex: MutableState<Int>

    protected val tabs: Array<NavigationTab> by lazy { navigationTabs() }

    @Composable
    @LayoutCoordinator
    @NonRestartableComposable
    protected fun NavigationContent(
        sideBarModifier: Modifier = Modifier,
        sideBarWidth: Dp = 185.dp,
        bottomBarModifier: Modifier = Modifier,
        backgroundTab: Color = MaterialTheme.colorScheme.background,
    ) {
        ResponsiveContent(
            onExpandedSizeClass = {
                SideNavigationArrangement(
                    modifier = sideBarModifier,
                    sideBarWidth = sideBarWidth,
                    backgroundTab = backgroundTab
                )
            },
            onMediumSizeClass = {
                SideNavigationArrangement(
                    modifier = sideBarModifier,
                    sideBarWidth = sideBarWidth,
                    backgroundTab = backgroundTab
                )
            },
            onMediumWidthExpandedHeight = {
                BottomNavigationArrangement(
                    modifier = bottomBarModifier,
                    backgroundTab = backgroundTab
                )
            },
            onCompactSizeClass = {
                BottomNavigationArrangement(
                    modifier = bottomBarModifier,
                    backgroundTab = backgroundTab
                )
            }
        )
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    private fun SideNavigationArrangement(
        modifier: Modifier,
        sideBarWidth: Dp,
        backgroundTab: Color,
    ) {
        Row {
            NavigationRail(
                modifier = modifier
                    .width(sideBarWidth),
                header = { SideNavigationHeaderContent() }
            ) {
                tabs.forEachIndexed { index, tab ->
                    SideNavigationItem(
                        index = index,
                        tab = tab
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    content = { SideNavigationFooterContent() }
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(backgroundTab)
            ) {
                ScreenTabContent(
                    paddingValues = PaddingValues(
                        top = 35.dp,
                        start = 35.dp,
                        end = 35.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun SideNavigationHeaderContent() {
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun SideNavigationFooterContent() {
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    protected open fun SideNavigationItem(
        index: Int,
        tab: NavigationTab,
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
            icon = {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.contentDescription
                )
            },
            label = {
                Text(
                    text = tab.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
    )
    private fun BottomNavigationArrangement(
        modifier: Modifier,
        backgroundTab: Color,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTab)
        ) {
            ScreenTabContent(
                paddingValues = PaddingValues(
                    top = 25.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 96.dp
                )
            )
            BottomAppBar(
                modifier = modifier
                    .align(Alignment.BottomCenter)
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

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
    )
    protected open fun RowScope.BottomNavigationItem(
        index: Int,
        tab: NavigationTab,
    ) {
        NavigationBarItem(
            selected = index == activeNavigationTabIndex.value,
            onClick = { activeNavigationTabIndex.value = index },
            icon = {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.contentDescription
                )
            },
            label = {
                Text(
                    text = tab.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }

    /**
     * The content of the tab
     *
     * @param paddingValues The values of the padding to apply to the content
     */
    @Composable
    @NonRestartableComposable
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
                    var screenTab by remember { mutableStateOf<EquinoxNoModelScreen?>(null) }
                    LaunchedEffect(Unit) {
                        screenTab = activeIndex.tabContent()
                    }
                    screenTab?.ShowContent()
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

    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        activeNavigationTabIndex = rememberSaveable { mutableStateOf(0) }
    }

    protected abstract fun navigationTabs(): Array<NavigationTab>

}