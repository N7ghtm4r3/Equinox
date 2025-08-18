# NavigatorScreen

Built-in responsive navigation system fully customizable as needed based on
the [EquinoxScreens](../../compose/APIs/EquinoxScreens.md) APIs. The `NavigatorScreen` follows
the [Template Method pattern](https://en.wikipedia.org/wiki/Template_method_pattern#:~:text=The%20template%20method%20is%20a,class%20as%20the%20template%20method)
to be flexible and fully customizable

## Integration

### Select the NavigatorTab

Select properly the `NavigatorTab` the `NavigatorScreen` have to use:

- `NavigationTab` for a simple navigation
- `I18nNavigationTab` for an internationalized navigation

### Create the concrete implementation of the Navigator

The example is valid for all the NavigatorTab(s) you selected, but for this example will be used the `NavigationTab`

#### Out-of-the-box implementation

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

#### Add custom header to the sidebar

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the `SideNavigationHeaderContent` method where you can place the header content

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

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the `SideNavigationHeaderContent` method where you can place the footer content

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

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the `SideNavigationItem` method where you can customize the navigation item for sidebar

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

#### Customize the sidebar navigation item

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the `SideNavigationItem` method where you can customize the navigation item for sidebar

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

The structure is the same of the [Out-of-the-box implementation](#out-of-the-box-implementation), but you need to
override the `BottomNavigationItem` method where you can customize the navigation item for bottom bar

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

### Display the screen

For example from the `App` function you can show the screen created before:

```kotlin
@Composable
fun App() {
    val home = HomeScreen()
    home.ShowContent()
}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | 3H3jyCzcRmnxroHthuXh22GXXSmizin2yp               | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | 0x1b45bc41efeb3ed655b078f95086f25fc83345c4       | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit
