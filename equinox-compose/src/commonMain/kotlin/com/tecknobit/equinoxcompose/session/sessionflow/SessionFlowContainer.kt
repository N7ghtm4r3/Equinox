@file:OptIn(ExperimentalComposeApi::class)

package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.animation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.tecknobit.equinoxcompose.components.ErrorUI
import com.tecknobit.equinoxcompose.components.LoadingItemUI
import com.tecknobit.equinoxcompose.resources.Res
import com.tecknobit.equinoxcompose.resources.no_network_connection
import com.tecknobit.equinoxcompose.resources.server_currently_offline
import com.tecknobit.equinoxcompose.session.createConnectivity
import com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowState.Companion.onUserDisconnected
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import dev.jordond.connectivity.Connectivity
import org.jetbrains.compose.resources.vectorResource

/**
 * Component used to display the correct content based on the [SessionStatus] value
 *
 * @param triggers The triggers to use to automatically reinvoke the [loadingRoutine]
 * @param modifier The modifier to apply to the component
 * @param state The state used to autonomously display the correct content
 * @param viewModel If passed will be used to autonomously suspend and restart the [com.tecknobit.equinoxcompose.session.Retriever]'s
 * routine
 * @param enterTransition The transition to apply when a new content is displayed
 * @param exitTransition The transition to apply when a content is hidden
 * @param initialLoadingRoutineDelay Delay to apply to the [loadingRoutine] before starts
 * @param loadingRoutine Routine used to load the elements displayed in the [content]
 * @param content The main content displayed when the [SessionStatus] is [OPERATIONAL]
 * @param statusTextStyle The style to apply to the texts of the callback contents
 * @param statusContainerColor The color to apply to the background of the callback contents
 * @param loadingContentColor The color to apply to the loading content
 * @param loadingIndicator The indicator used during the [loadingRoutine] execution
 * @param fallbackContentColor The color to apply to the fallback contents
 * @param retryFailedFlowContent The content displayed to allow the user to retry a failed operation
 * @param onServerOffline The content displayed when the [SessionStatus] is [SERVER_OFFLINE]
 * @param onNoNetworkConnection The content displayed when the [SessionStatus] is [NO_NETWORK_CONNECTION]
 *
 * @since 1.1.2
 */
@Composable
@ExperimentalComposeApi
fun SessionFlowContainer(
    vararg triggers: Any?,
    modifier: Modifier = Modifier,
    state: SessionFlowState,
    viewModel: EquinoxViewModel? = null,
    enterTransition: EnterTransition = fadeIn(),
    exitTransition: ExitTransition = fadeOut(),
    initialLoadingRoutineDelay: Long? = null,
    loadingRoutine: (suspend () -> Boolean)? = null,
    content: @Composable () -> Unit,
    statusTextStyle: TextStyle = LocalTextStyle.current,
    statusContainerColor: Color = MaterialTheme.colorScheme.background,
    loadingContentColor: Color = contentColorFor(statusContainerColor),
    loadingIndicator: @Composable () -> Unit = {
        LoadingItemUI(
            state.loadingRoutineTrigger.value, triggers,
            containerModifier = modifier,
            initialDelay = initialLoadingRoutineDelay,
            loadingRoutine = loadingRoutine!!,
            contentLoaded = content,
            loadingIndicatorBackground = statusContainerColor,
            themeColor = loadingContentColor,
            textStyle = statusTextStyle,
        )
    },
    fallbackContentColor: Color = MaterialTheme.colorScheme.error,
    retryFailedFlowContent: @Composable (() -> Unit)? = null,
    onServerOffline: @Composable () -> Unit = {
        ErrorUI(
            containerModifier = modifier,
            errorIcon = Icons.Default.Warning,
            errorMessage = Res.string.server_currently_offline,
            textStyle = statusTextStyle,
            backgroundColor = statusContainerColor,
            errorColor = fallbackContentColor,
            retryContent = retryFailedFlowContent
        )
    },
    onNoNetworkConnection: @Composable () -> Unit = {
        ErrorUI(
            containerModifier = modifier,
            errorIcon = vectorResource(Res.drawable.no_network_connection),
            errorMessage = Res.string.no_network_connection,
            textStyle = statusTextStyle,
            backgroundColor = statusContainerColor,
            errorColor = fallbackContentColor,
            retryContent = retryFailedFlowContent
        )
    },
) {
    val connectionState = remember { createConnectivity() }
    LaunchedEffect(Unit) {
        state.attachViewModel(
            viewModel = viewModel
        )
    }
    monitorConnection(
        connectionState = connectionState,
        state = state
    )
    AnimatedContent(
        targetState = state.currentStatus.value,
        transitionSpec = {
            enterTransition.togetherWith(
                exit = exitTransition
            )
        }
    ) { status ->
        when (status) {
            OPERATIONAL -> {
                if (loadingRoutine != null)
                    loadingIndicator.invoke()
                else
                    content()
            }

            SERVER_OFFLINE -> onServerOffline()
            NO_NETWORK_CONNECTION -> onNoNetworkConnection()
            USER_DISCONNECTED -> {
                LaunchedEffect(Unit) {
                    onUserDisconnected?.invoke()
                }
            }
        }
    }
}

/**
 * Method used to monitor the network connection status
 *
 * @param connectionState The state used to detect the connection status
 * @param state The state of the [SessionFlowContainer]
 */
@Composable
@Suppress("ComposableNaming")
private fun monitorConnection(
    connectionState: Connectivity,
    state: SessionFlowState,
) {
    LaunchedEffect(connectionState) {
        connectionState.statusUpdates.collect { status ->
            state.handleConnectivityStatus(
                isConnected = status.isConnected
            )
        }
    }
}