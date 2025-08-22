Built-in responsive navigation system fully customizable based on the [EquinoxScreens](../../compose/APIs/EquinoxScreens.md) APIs. 
The `NavigatorScreen` follows the [Template Method pattern](https://www.geeksforgeeks.org/system-design/template-method-design-pattern/) to be flexible and fully customizable

## Implementation

### Select the NavigatorTab

Select properly the `NavigatorTab` the `NavigatorScreen` have to use:

- `NavigationTab` for a simple navigation
- `I18nNavigationTab` for an internationalized navigation

### Out-of-the-box implementation

The example is valid for all the NavigatorTab(s) you selected, but for this example will be used the `NavigationTab`.

#### Declare the reachable tabs

Overriding the `navigationTabs` method you can declare which tabs can be reached during the navigation

```kotlin
/**
 * Method used to retrieve the tabs to assign to the [tabs] array
 *
 * @return the tabs used by the [NavigatorScreen] as [Array] of [T]
 */
override fun navigationTabs(): Array<NavigationTab> {
    // your tabs here
    return arrayOf(
        NavigationTab(
            title = "Screen A",
            icon = Icons.Default.Place,
            contentDescription = "The screen A"
        ),
        NavigationTab(
            title = "Screen B",
            icon = Icons.Default.Queue,
            contentDescription = "The screen B"
        )
    )
}
```

#### Display each tab

Overriding the `tabContent` method you can display the content of each tab 

```kotlin
/**
 * Method used to instantiate the related screen based on the current [activeNavigationTabIndex]
 *
 * @return the screen as [EquinoxNoModelScreen]
 */
override fun Int.tabContent(): EquinoxNoModelScreen {
    // here you can insert the custom logic to retrieve the specific content for the tab to display
    return when (this) {
        0 -> A() // your custom EquinoxNoModelScreen
        else -> B() // your custom EquinoxNoModelScreen
    }
}
```

#### Invoke the navigation system

To display and to use the navigation system you can invoke the `NavigationContent` as follows:

```kotlin
/**
 * Method used to arrange the content of the screen to display
 */
@Composable
override fun ArrangeScreenContent() {
    // here you can set the theme for example or any custom logic

    // invoke this component to display the navigation system
    NavigationContent(
        sideBarModifier =, // customize the modifier of the sidebar
        sideBarWidth =,  // customize the width of the sidebar
        bottomBarModifier =, // customize the modifier of the bottom bar
        backgroundTab =, // customize the color of the background for the tabs
    )
}
```

#### Overview

General overview about how the implementation should look like

```kotlin
@OptIn(ExperimentalComposeApi::class)
class HomeScreen : NavigatorScreen<NavigationTab>() {

    /**
     * Method used to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        // here you can set the theme for example or any custom logic

        // invoke this component to display the navigation system
        NavigationContent(
            sideBarModifier =, // customize the modifier of the sidebar
            sideBarWidth =,  // customize the width of the sidebar
            bottomBarModifier =, // customize the modifier of the bottom bar
            backgroundTab =, // customize the color of the background for the tabs
        )
    }

    /**
     * Method used to retrieve the tabs to assign to the [tabs] array
     *
     * @return the tabs used by the [NavigatorScreen] as [Array] of [T]
     */
    override fun navigationTabs(): Array<NavigationTab> {
        // your tabs here
        return arrayOf(
            NavigationTab(
                title = "Screen A",
                icon = Icons.Default.Place,
                contentDescription = "The screen A"
            ),
            NavigationTab(
                title = "Screen B",
                icon = Icons.Default.Queue,
                contentDescription = "The screen B"
            )
        )
    }

    /**
     * Method used to instantiate the related screen based on the current [activeNavigationTabIndex]
     *
     * @return the screen as [EquinoxNoModelScreen]
     */
    override fun Int.tabContent(): EquinoxNoModelScreen {
        // here you can insert the custom logic to retrieve the specific content for the tab to display
        return when (this) {
            0 -> A() // your custom EquinoxNoModelScreen
            else -> B() // your custom EquinoxNoModelScreen
        }
    }

}
```

### Customizing behavior

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the specific methods to apply the customizations

#### Add custom header to the sidebar

Overriding the `SideNavigationHeaderContent` method you can place a header content

```kotlin
@OptIn(ExperimentalComposeApi::class)
class HomeScreen : NavigatorScreen<NavigationTab>() {

    /**
     * Custom header content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    override fun SideNavigationHeaderContent() {
        // your custom header content
    }

}
```

#### Add custom footer to the sidebar

Overriding the `SideNavigationHeaderContent` method you can place a footer content

```kotlin
@OptIn(ExperimentalComposeApi::class)
class HomeScreen : NavigatorScreen<NavigationTab>() {

    /**
     * Custom footer content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    override fun SideNavigationFooterContent() {
        // your custom footer content
    }

}
```

#### Customize the sidebar navigation item

Overriding the `SideNavigationItem` method you can customize the navigation item displayed on the sidebar

```kotlin
@OptIn(ExperimentalComposeApi::class)
class HomeScreen : NavigatorScreen<NavigationTab>() {

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
    override fun SideNavigationItem(
        index: Int,
        tab: NavigationTab,
    ) {
        // custom sidebar navigation item here
    }

}
```

#### Customize the bottom bar navigation item

Overriding the `BottomNavigationItem` method you can customize the navigation item displayed on the bottom bar

```kotlin
@OptIn(ExperimentalComposeApi::class)
class HomeScreen : NavigatorScreen<NavigationTab>() {

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
    override fun RowScope.BottomNavigationItem(
        index: Int,
        tab: NavigationTab,
    ) {
        // custom bottom bar navigation item here
    }

}
```

## Usage

For example from the `App` function you can display the screen created before:

```kotlin
@Composable
fun App() {
    val home = equinoxScreen { HomeScreen() }
    home.ShowContent()
}
```