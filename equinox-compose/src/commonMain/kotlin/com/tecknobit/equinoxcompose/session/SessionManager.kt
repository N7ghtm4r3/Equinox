package com.tecknobit.equinoxcompose.session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.tecknobit.equinoxcompose.components.ErrorUI
import com.tecknobit.equinoxcompose.components.LoadingItemUI
import com.tecknobit.equinoxcompose.resources.Res
import com.tecknobit.equinoxcompose.resources.no_internet
import com.tecknobit.equinoxcompose.resources.no_internet_connection
import com.tecknobit.equinoxcompose.resources.server_currently_offline
import com.tecknobit.equinoxcompose.session.SessionStatus.*
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.vectorResource

/**
 * The **SessionSetup** class is useful to create a setup for the current session
 *
 * @param serverOfflineMessage The message to use when the server is offline
 * @param serverOfflineIcon The icon to use when the server is offline
 * @param noInternetConnectionMessage The message to use when the internet connection is not available
 * @param noInternetConnectionIcon The icon to use when the internet connection is not available
 * @param hasBeenDisconnectedAction The action to execute when the user has been disconnected
 */
data class SessionSetup(
    val serverOfflineMessage: StringResource,
    val serverOfflineIcon: ImageVector,
    val noInternetConnectionMessage: StringResource,
    val noInternetConnectionIcon: ImageVector,
    val hasBeenDisconnectedAction: () -> Unit,
)

/**
 * List of the possible statuses of the session
 */
enum class SessionStatus {

    /**
     * `OPERATIONAL` the normal status of the session
     */
    OPERATIONAL,

    /**
     * `SERVER_OFFLINE` the status of the session when the related server is offline
     */
    SERVER_OFFLINE,

    /**
     * `NO_INTERNET_CONNECTION` the status of the session when there is no internet connection
     */
    NO_INTERNET_CONNECTION,

    /**
     * `USER_DISCONNECTED` the status of the session when the user has been disconnected
     */
    USER_DISCONNECTED

}

/**
 * `sessionSetup` the setup for the session
 */
private lateinit var sessionSetup: SessionSetup

/**
 * `sessionStatus` the current session status
 */
private lateinit var sessionStatus: MutableState<SessionStatus>

/**
 * `isServerOffline` state to manage the server offline scenario
 */
private lateinit var isServerOffline: MutableState<Boolean>

/**
 * `noInternetConnection` state to manage the no internet connection scenario
 */
private lateinit var noInternetConnection: MutableState<Boolean>

/**
 * `hasBeenDisconnectedAction` when the account has been deleted and the session needs to
 * be detached from the device
 */
private lateinit var hasBeenDisconnected: MutableState<Boolean>

/**
 * Method used to set up the [sessionSetup] instance
 *
 * @param serverOfflineMessage The message to use when the server is offline
 * @param serverOfflineIcon The icon to use when the server is offline
 * @param noInternetConnectionMessage The message to use when the internet connection is not available
 * @param noInternetConnectionIcon The icon to use when the internet connection is not available
 * @param hasBeenDisconnectedAction The action to execute when the user has been disconnected
 */
@Composable
fun setUpSession(
    serverOfflineMessage: StringResource = Res.string.server_currently_offline,
    serverOfflineIcon: ImageVector = Icons.Default.Warning,
    noInternetConnectionMessage: StringResource = Res.string.no_internet_connection,
    noInternetConnectionIcon: ImageVector = vectorResource(Res.drawable.no_internet),
    hasBeenDisconnectedAction: () -> Unit,
) {
    setUpSession(
        sessionSetupValue = SessionSetup(
            serverOfflineMessage = serverOfflineMessage,
            serverOfflineIcon = serverOfflineIcon,
            noInternetConnectionMessage = noInternetConnectionMessage,
            noInternetConnectionIcon = noInternetConnectionIcon,
            hasBeenDisconnectedAction = hasBeenDisconnectedAction
        )
    )
}

/**
 * Method used to set up the [sessionSetup] instance
 *
 * @param sessionSetupValue The setup to use for the current session
 */
fun setUpSession(
    sessionSetupValue: SessionSetup,
) {
    sessionSetup = sessionSetupValue
}

/**
 * Method used to set the value of the [isServerOffline] state, when the value is _true_ will be invoked
 * the [ServerOfflineUi] method, when _false_ will be displayed the normal content
 *
 * @param isServerOfflineValue The value to set
 */
fun setServerOfflineValue(
    isServerOfflineValue: Boolean,
) {
    if (::isServerOffline.isInitialized) {
        MainScope().launch {
            isServerOffline.value = isServerOfflineValue
        }
    }
}

/**
 * Method used to set the value of the [hasBeenDisconnectedAction] state, when the value is _true_ will be invoked
 * the [hasBeenDisconnectedAction] method, when _false_ will be displayed the normal content
 *
 * @param hasBeenDisconnectedValue The value to set
 */
fun setHasBeenDisconnectedValue(
    hasBeenDisconnectedValue: Boolean,
) {
    if (::hasBeenDisconnected.isInitialized) {
        MainScope().launch {
            hasBeenDisconnected.value = hasBeenDisconnectedValue
        }
    }
}

/**
 * Method used to display the correct content based on the current scenario such server offline or
 * device disconnected or no internet connection available
 *
 * @param content The content to display in a normal scenario
 * @param viewModel The viewmodel used by the context where this method has been invoked, this is
 * used to stop the refreshing routine when the internet connection is not available by the [NoInternetConnectionUi]
 * @param loadingRoutine The routine used to load the data
 * @param initialDelay An initial delay to apply to the [loadingRoutine] before to start
 * @param serverOfflineUiDefaults The style to apply to the [ServerOfflineUi] fallback page
 * @param serverOfflineRetryText The informative text for the user
 * @param serverOfflineRetryAction The action to retry the connection to the server
 * @param noInternetConnectionUiDefaults The style to apply to the [NoInternetConnectionUi] fallback page
 * @param noInternetConnectionRetryText The informative text for the user
 * @param noInternetConnectionRetryAction The action to retry the internet connection scan
 */
@Composable
fun ManagedContent(
    content: @Composable () -> Unit,
    viewModel: EquinoxViewModel,
    loadingRoutine: (suspend () -> Boolean)? = null,
    initialDelay: Long? = null,
    serverOfflineUiDefaults: FallbackUiDefaults = createFallbackUiAppearance(),
    serverOfflineRetryText: StringResource? = null,
    serverOfflineRetryAction: @Composable (() -> Unit)? = null,
    noInternetConnectionUiDefaults: FallbackUiDefaults = createFallbackUiAppearance(),
    noInternetConnectionRetryText: StringResource? = null,
    noInternetConnectionRetryAction: @Composable (() -> Unit)? = null,
) {
    InstantiateSessionInstances()
    AnimatedVisibility(
        visible = isServerOffline.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        sessionStatus.value = SERVER_OFFLINE
        ServerOfflineUi(
            uiDefaults = serverOfflineUiDefaults,
            retryText = serverOfflineRetryText,
            retryAction = serverOfflineRetryAction
        )
    }
    AnimatedVisibility(
        visible = noInternetConnection.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        sessionStatus.value = NO_INTERNET_CONNECTION
        NoInternetConnectionUi(
            uiDefaults = noInternetConnectionUiDefaults,
            viewModel = viewModel,
            retryText = noInternetConnectionRetryText,
            retryAction = noInternetConnectionRetryAction
        )
    }
    AnimatedVisibility(
        visible = !isServerOffline.value && !noInternetConnection.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (hasBeenDisconnected.value) {
            sessionStatus.value = USER_DISCONNECTED
            hasBeenDisconnectedAction()
        } else {
            sessionStatus.value = OPERATIONAL
            viewModel.restartRetriever()
            if (loadingRoutine != null) {
                LoadingItemUI(
                    loadingRoutine = loadingRoutine,
                    initialDelay = initialDelay,
                    contentLoaded = content
                )
            } else
                content()
        }
    }
}

/**
 * Method used to instantiate the session instances to manage the different scenarios
 */
@Composable
private fun InstantiateSessionInstances() {
    isServerOffline = remember { mutableStateOf(false) }
    noInternetConnection = remember { mutableStateOf(false) }
    hasBeenDisconnected = remember { mutableStateOf(false) }
    sessionStatus = remember { mutableStateOf(OPERATIONAL) }
    val state by lazy { createConnectivity() }
    MainScope().launch {
        state.statusUpdates.collect { status ->
            noInternetConnection.value = status.isDisconnected
        }
    }
}

/**
 * Method used to display the content when the server is offline
 *
 * @param uiDefaults The style for this fallback page
 * @param retryText The informative text for the user
 * @param retryAction The action to retry the connection to the server
 */
@Composable
@NonRestartableComposable
private fun ServerOfflineUi(
    uiDefaults: FallbackUiDefaults,
    retryText: StringResource?,
    retryAction: @Composable (() -> Unit)?,
) {
    ErrorUI(
        backgroundColor = uiDefaults.containerColor,
        textStyle = uiDefaults.textStyle,
        errorColor = uiDefaults.contentColor,
        errorIcon = try {
            sessionSetup.serverOfflineIcon
        } catch (e: Exception) {
            throw Exception("You must setup the session before, this using the setUpSession() method")
        },
        errorMessage = try {
            sessionSetup.serverOfflineMessage
        } catch (e: Exception) {
            throw Exception("You must setup the session before, this using the setUpSession() method")
        },
        retryText = retryText,
        retryAction = retryAction
    )
}

/**
 * Method used to display the content when the internet connection missing
 *
 * @param viewModel The viewmodel used by the context from the [ManagedContent] method has been invoked, this is
 * used to stop the refreshing routine when the internet connection is not available
 * @param uiDefaults The style for this fallback page
 * @param retryText The informative text for the user
 * @param retryAction The action to retry the internet connection scan
 *
 */
@Composable
@NonRestartableComposable
private fun NoInternetConnectionUi(
    viewModel: EquinoxViewModel,
    uiDefaults: FallbackUiDefaults,
    retryText: StringResource?,
    retryAction: @Composable (() -> Unit)?,
) {
    viewModel.suspendRetriever()
    ErrorUI(
        backgroundColor = uiDefaults.containerColor,
        textStyle = uiDefaults.textStyle,
        errorColor = uiDefaults.contentColor,
        errorIcon = try {
            sessionSetup.noInternetConnectionIcon
        } catch (e: Exception) {
            throw Exception("You must setup the session before, this using the setUpSession() method")
        },
        errorMessage = try {
            sessionSetup.noInternetConnectionMessage
        } catch (e: Exception) {
            throw Exception("You must setup the session before, this using the setUpSession() method")
        },
        retryText = retryText,
        retryAction = retryAction
    )
}

/**
 * The `FallbackUiDefaults` allows to customize the fallback pages ([ServerOfflineUi] and [NoInternetConnectionUi]) style
 *
 * @property textStyle The style to apply to the text
 * @property containerColor The color of the container
 * @property contentColor The color of the content
 *
 * @author N7ghtm4r3
 *
 * @since 1.1.0
 */
data class FallbackUiDefaults(
    val textStyle: TextStyle,
    val containerColor: Color,
    val contentColor: Color,
)

/**
 * Method used to create a customization style for a fallback page
 *
 * @param textStyle The style to apply to the text
 * @param containerColor The color of the container
 * @param contentColor The color of the content
 *
 * @return the customization style for a fallback page as [FallbackUiDefaults]
 */
@Composable
fun createFallbackUiAppearance(
    textStyle: TextStyle = TextStyle.Default,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.error,
): FallbackUiDefaults {
    return FallbackUiDefaults(
        textStyle = textStyle,
        containerColor = containerColor,
        contentColor = contentColor
    )
}

/**
 * Method used to disconnect the current user from the session
 */
private fun hasBeenDisconnectedAction() {
    try {
        sessionSetup.hasBeenDisconnectedAction()
    } catch (e: Exception) {
        throw Exception("You must setup the session before, this using the setUpSession() method")
    }
}

/**
 * Method used to get the current status of the session as [SessionStatus]
 */
fun getCurrentSessionStatus(): SessionStatus {
    return sessionStatus.value
}