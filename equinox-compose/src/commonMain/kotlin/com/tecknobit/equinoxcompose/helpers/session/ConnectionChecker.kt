package com.tecknobit.equinoxcompose.helpers.session

import androidx.compose.runtime.MutableState

/**
 * Function to check the internet connection availability
 *
 * @param noInternetConnectionState: state to manage the no internet connection scenario
 */
expect fun checkInternetConnection(
    noInternetConnectionState: MutableState<Boolean>,
)