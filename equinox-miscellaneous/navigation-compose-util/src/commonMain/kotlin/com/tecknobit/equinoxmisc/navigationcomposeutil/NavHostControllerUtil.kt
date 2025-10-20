package com.tecknobit.equinoxmisc.navigationcomposeutil

import androidx.navigation.*
import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.Returner

/**
 * Method used to navigate to a destination with a [NavUri] and to attach data to share with that destination
 * 
 * @param deepLink The target destination
 * @param data The attached data to share with the target destination
 * @param navOptions Optional navigation options
 * @param navigatorExtras Optional extra to pass to the Navigator
 */
@ExperimentalStdlibApi
fun NavHostController.navWithData(
    deepLink: NavUri,
    data: Map<String, *>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    fillStateHandleThenNav(
        navigator = this,
        data = data,
        onNav = {
            navigate(
                deepLink = deepLink,
                navOptions = navOptions,
                navigatorExtras = navigatorExtras
            )
        }
    )
}

/**
 * Method used to navigate to a destination with a [NavDeepLinkRequest] and to attach data to share with that destination
 *
 * @param request The target destination
 * @param data The attached data to share with the target destination
 * @param navOptions Optional navigation options
 * @param navigatorExtras Optional extra to pass to the Navigator
 */
@ExperimentalStdlibApi
fun NavHostController.navWithData(
    request: NavDeepLinkRequest,
    data: Map<String, *>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    fillStateHandleThenNav(
        navigator = this,
        data = data,
        onNav = {
            navigate(
                request = request,
                navOptions = navOptions,
                navigatorExtras = navigatorExtras
            )
        }
    )
}

/**
 * Method used to navigate to a destination with a [String] and to attach data to share with that destination
 *
 * @param route The target destination
 * @param data The attached data to share with the target destination
 * @param builder Builder scope to use optional navigation options
 */
@ExperimentalStdlibApi
fun NavHostController.navWithData(
    route: String,
    data: Map<String, *>,
    builder: NavOptionsBuilder.() -> Unit,
) {
    fillStateHandleThenNav(
        navigator = this,
        data = data,
        onNav = {
            navigate(
                route = route,
                builder = builder
            )
        }
    )
}

/**
 * Method used to navigate to a destination with a [String] and to attach data to share with that destination
 *
 * @param route The target destination
 * @param data The attached data to share with the target destination
 * @param navOptions Optional navigation options
 * @param navigatorExtras Optional extra to pass to the Navigator
 */
@ExperimentalStdlibApi
fun NavHostController.navWithData(
    route: String,
    data: Map<String, *>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    fillStateHandleThenNav(
        navigator = this,
        data = data,
        onNav = {
            navigate(
                route = route,
                navOptions = navOptions,
                navigatorExtras = navigatorExtras
            )
        }
    )
}

/**
 * Method used to navigate to a destination with a [T] and to attach data to share with that destination
 *
 * @param route The target destination
 * @param data The attached data to share with the target destination
 * @param navOptions Optional navigation options
 * @param navigatorExtras Optional extra to pass to the Navigator
 */
@ExperimentalStdlibApi
fun <T: Any> NavHostController.navWithData(
    route: T,
    data: Map<String, *>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    fillStateHandleThenNav(
        navigator = this,
        data = data,
        onNav = {
            navigate(
                route = route,
                navOptions = navOptions,
                navigatorExtras = navigatorExtras
            )
        }
    )
}

/**
 * Method to fill the [androidx.lifecycle.SavedStateHandle] of the `currentBackStackEntry` of the [navigator] and then
 * nav to the selected target destination
 *
 * @param navigator The navigator instance to use to navigate between destinations
 * @param data The attached data to share with the target destination
 * @param onNav The callback invoked to navigate
 */
internal fun fillStateHandleThenNav(
    navigator: NavHostController,
    data: Map<String, *>,
    onNav: () -> Unit
) {
    val savedStateHandle = navigator.currentBackStackEntry?.savedStateHandle
    savedStateHandle?.let {
        data.forEach { entry ->
            savedStateHandle[entry.key] = entry.value
        }
    }
    onNav()
}

/**
 * Method used to get a data from the data attached during the navigation to the current visible destination
 *
 * @param key The key which identify a data in the attached ones
 * @param defaultValue A default value to use whether the key does not identify any data
 *
 * @return the specific data from the attached ones as nullable [T]
 *
 * @param T The type of the navigation data to retrieve
 */
@ExperimentalStdlibApi
@Returner
fun <T> NavHostController.getDestinationNavData(
    key: String,
    defaultValue: T? = null
): T? {
    val savedStateHandle = previousBackStackEntry?.savedStateHandle
    return savedStateHandle?.get(key) ?: defaultValue
}

/**
 * Method used to get all the attached data during the navigation to the current visible destination.
 *
 * The `all destination data` means just to the data shared with the visible destination
 *
 *
 * @return the all the attached data to the destination as [Map] of [String] and nullable [Any]
 */
@ExperimentalStdlibApi
@Assembler
fun NavHostController.getAllDestinationNavData(): Map<String, Any?> {
    val savedStateHandle = previousBackStackEntry?.savedStateHandle
    val navData = mutableMapOf<String, Any?>()
    savedStateHandle?.let {
        savedStateHandle.keys().forEach { key ->
            navData[key] = savedStateHandle[key]
        }
    }
    return navData
}

/**
 * Method used to clear all the navigation data attached to the last destination before the [NavHostController] performing
 * [NavHostController.popBackStack]
 *
 * @return list of removed items as [List] of [*]
 */
@ExperimentalStdlibApi
fun NavHostController.clearLastDestinationAllNavData() : List<*> {
    val savedKeys = currentBackStackEntry?.savedStateHandle?.keys().orEmpty()
    return clearLastDestinationNavData(
        *savedKeys.toTypedArray()
    )
}

/**
 * Method used to clear the navigation data, specified by the [keys], attached to the last destination before the
 * [NavHostController] performing [NavHostController.popBackStack]
 *
 * @param keys The keys of the attached data to remove
 *
 * @return list of removed items as [List] of [*]
 */
@ExperimentalStdlibApi
fun NavHostController.clearLastDestinationNavData(
    vararg keys: String
) : List<*> {
    val savedStateHandle = currentBackStackEntry?.savedStateHandle
    val removedItems = mutableListOf<Any?>()
    savedStateHandle?.let {
        keys.forEach { key ->
            val removedItem = savedStateHandle.remove<Any?>(
                key = key
            )
            removedItems.add(removedItem)
        }
    }
    return removedItems
}