package com.tecknobit.equinoxcompose.components.tabselector

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Custom tab selector to select the tab to display
 *
 * @param modifier The modifier to apply to the component
 * @param state The state used to manage this component
 * @param tabs The list of the tab to display
 * @param initialTabShape The shape to use for the first tab of the selector
 * @param middleShape The shape to use for the middle tabs of the selector
 * @param lastTabShape The shape to use for the last tab of the selector
 * @param swipingEnabled Whether the horizontal swiping gesture is enabled, if yes the [tabContent] will be displayed
 * with the [HorizontalPager] component
 * @param tabContent The content of the tab
 */
@Composable
@NonRestartableComposable
@ExperimentalComposeUiApi
fun TabSelector(
    modifier: Modifier = Modifier,
    state: TabSelectorState,
    tabs: List<TabDetails>,
    initialTabShape: Shape = RoundedCornerShape(
        topStart = 0.dp,
        bottomStart = 10.dp
    ),
    middleShape: Shape = RectangleShape,
    lastTabShape: Shape = RoundedCornerShape(
        topEnd = 0.dp,
        bottomEnd = 10.dp
    ),
    swipingEnabled: Boolean = true,
    tabContent: @Composable (TabDetails, Int) -> Unit,
) {
    if (tabs.size > MAX_TABS_ALLOWED)
        throw IllegalArgumentException("Just $MAX_TABS_ALLOWED tabs are allowed")
    val pagerState = if (swipingEnabled) {
        rememberPagerState(
            initialPage = state.initialTabIndex,
            pageCount = { tabs.size }
        )
    } else
        null
    val firstEntry = tabs.first()
    val lastEntry = tabs.last()
    state.InitState()
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                pagerState = pagerState,
                state = state,
                tab = tab,
                currentIndex = index,
                firstEntry = firstEntry,
                lastEntry = lastEntry,
                initialTabShape = initialTabShape,
                middleShape = middleShape,
                lastTabShape = lastTabShape
            )
        }
    }
    if (swipingEnabled) {
        HorizontalPager(
            state = pagerState!!
        ) { page ->
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    state.setSelectedTab(page)
                }
            }
            tabContent.invoke(tabs[page], page)
        }
    } else {
        val selectedIndex = state.getSelectedTab()
        tabContent.invoke(tabs[selectedIndex], selectedIndex)
    }
}

/**
 * Custom button tab
 *
 * @param pagerState The state used to manage the [HorizontalPager]
 * @param state The state used to manage this component
 * @param tab The details of the tab
 * @param currentIndex The index related to the tab
 * @param firstEntry The first entry of the tabs list
 * @param lastEntry The last entry of the tabs list
 * @param initialTabShape The shape to use for the first tab of the selector
 * @param middleShape The shape to use for the middle tabs of the selector
 * @param lastTabShape The shape to use for the last tab of the selector
 */
@Composable
@NonRestartableComposable
private fun SingleChoiceSegmentedButtonRowScope.Tab(
    pagerState: PagerState?,
    state: TabSelectorState,
    tab: TabDetails,
    currentIndex: Int,
    firstEntry: TabDetails,
    lastEntry: TabDetails,
    initialTabShape: Shape,
    middleShape: Shape,
    lastTabShape: Shape,
) {
    val selected = state.isTheSelectedTab(
        currentIndex = currentIndex
    )
    SegmentedButton(
        selected = selected,
        icon = {
            if (tab.icon != null) {
                val customIcon = if (selected)
                    tab.selectedIcon
                else
                    tab.icon
                Icon(
                    imageVector = customIcon,
                    contentDescription = null
                )
            } else
                SegmentedButtonDefaults.Icon(selected)
        },
        label = {
            Text(
                text = tab.tabTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        shape = when (tab) {
            lastEntry -> lastTabShape
            firstEntry -> initialTabShape
            else -> middleShape
        },
        onClick = {
            pagerState?.let { pager ->
                MainScope().launch {
                    pager.scrollToPage(currentIndex)
                }
            }
            state.setSelectedTab(
                index = currentIndex
            )
        }
    )
}