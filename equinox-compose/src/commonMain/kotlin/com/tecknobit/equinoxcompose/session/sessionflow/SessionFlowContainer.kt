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
import com.tecknobit.equinoxcompose.resources.no_internet
import com.tecknobit.equinoxcompose.resources.no_internet_connection
import com.tecknobit.equinoxcompose.resources.server_currently_offline
import com.tecknobit.equinoxcompose.session.createConnectivity
import com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowState.Companion.onUserDisconnected
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*
import dev.jordond.connectivity.Connectivity
import org.jetbrains.compose.resources.vectorResource

@Composable
@ExperimentalComposeApi
fun SessionFlowContainer(
    modifier: Modifier = Modifier,
    state: SessionFlowState,
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
            containerModifier = modifier,
            initialDelay = initialLoadingRoutineDelay,
            loadingRoutine = loadingRoutine!!,
            contentLoaded = content,
            loadingIndicatorBackground = statusContainerColor,
            themeColor = loadingContentColor,
            textStyle = statusTextStyle
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
    onNoInternetConnection: @Composable () -> Unit = {
        ErrorUI(
            containerModifier = modifier,
            errorIcon = vectorResource(Res.drawable.no_internet),
            errorMessage = Res.string.no_internet_connection,
            textStyle = statusTextStyle,
            backgroundColor = statusContainerColor,
            errorColor = fallbackContentColor,
            retryContent = retryFailedFlowContent
        )
    },
) {
    val connectionState = remember { createConnectivity() }
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
            NO_INTERNET_CONNECTION -> onNoInternetConnection()
            USER_DISCONNECTED -> {
                LaunchedEffect(Unit) {
                    onUserDisconnected?.invoke()
                }
            }
        }
    }
}

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