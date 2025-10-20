package com.tecknobit.equinoxmisc.navigationcomposeutil

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

//fun NavHostController.navWithData(
//    deepLink: NavUri,
//    vararg data: Pair<String, *>
//) {
//    fillStateHandleThenNav(
//        navigator = this,
//        data = data,
//        onNav = {
//            navigate(
//                deepLink = deepLink
//            )
//        }
//    )
//}
//
//fun NavHostController.navWithData(
//    deepLink: NavUri,
//    navOptions: NavOptions?,
//    vararg data: Pair<String, *>
//) {
//    fillStateHandleThenNav(
//        navigator = this,
//        data = data,
//        onNav = {
//            navigate(
//                deepLink = deepLink,
//                navOptions = navOptions
//            )
//        }
//    )
//}
//
//fun NavHostController.navWithData(
//    deepLink: NavUri,
//    navOptions: NavOptions?,
//    navigatorExtras: Navigator.Extras?,
//    vararg data: Pair<String, *>
//) {
//    fillStateHandleThenNav(
//        navigator = this,
//        data = data,
//        onNav = {
//            navigate(
//                deepLink = deepLink,
//                navOptions = navOptions,
//                navigatorExtras = navigatorExtras
//            )
//        }
//    )
//}



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