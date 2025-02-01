package com.tecknobit.equinoxcompose.components.tabselector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * `MAX_TABS_ALLOWED` the max number of allowed tabs for the [TabSelector]
 */
const val MAX_TABS_ALLOWED = 5

/**
 * The remember function used to create a [TabSelectorState] for a [TabSelector] component
 *
 * @param initialTabIndex The index value of the initial tab to display
 *
 * @return the state for the [TabSelector] as [TabSelectorState]
 */
@Composable
fun rememberTabSelectorState(
    initialTabIndex: Int = 0,
): TabSelectorState {
    val tabSelectorState = rememberSaveable(
        stateSaver = TabSelectorSaver
    ) {
        mutableStateOf(
            TabSelectorState(
                initialTabIndex = initialTabIndex
            )
        )
    }
    return tabSelectorState.value
}

/**
 * The **TabSelectorState** class is useful to manage a [TabSelector] component giving the details currently
 * used in that component
 *
 * @param initialTabIndex The index value of the initial tab to display
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class TabSelectorState internal constructor(
    val initialTabIndex: Int,
) {

    /**
     * `selectedTab` the currently selected tab
     */
    private lateinit var selectedTab: MutableState<Int>

    /**
     * The method to init the current state
     */
    @Composable
    internal fun InitState() {
        selectedTab = remember { mutableStateOf(initialTabIndex) }
    }

    /**
     * Method to check whether the [currentIndex] is the current [selectedTab]
     *
     * @param currentIndex The index to check
     *
     * @return whether the [currentIndex] is the current [selectedTab] as [Boolean]
     */
    internal fun isTheSelectedTab(
        currentIndex: Int,
    ): Boolean {
        return currentIndex == selectedTab.value
    }

    /**
     * Method to set the value of the selected tab
     *
     * @param index The index of the tab to set as the selected
     */
    internal fun setSelectedTab(
        index: Int,
    ) {
        selectedTab.value = index
    }

    /**
     * Method to get the current [selectedTab] value
     *
     * @return the current selected tab index value as [Int]
     */
    fun getSelectedTab(): Int {
        return selectedTab.value
    }

}

/**
 * The **TabSelectorSaver** custom saver allow the [TabSelectorState] to save and restore correctly the
 * values for the [TabSelector] component
 *
 * @see Saver
 *
 * @author N7ghtm4r3 - Tecknobit
 */
internal object TabSelectorSaver : Saver<TabSelectorState, Int> {

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun SaverScope.save(
        value: TabSelectorState,
    ): Int {
        return value.initialTabIndex
    }

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun restore(
        value: Int,
    ): TabSelectorState {
        return TabSelectorState(
            initialTabIndex = value
        )
    }

}

/**
 * Data class used to represent the details of a tab
 *
 * @property icon The representative icon for the tab
 * @property selectedIcon The icon to display when the tab is currently selected
 * @property tabTitle The title of the tab
 */
data class TabDetails(
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector = Icons.Default.Check,
    val tabTitle: String,
)