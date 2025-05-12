package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel

@Composable
@ExperimentalComposeApi
fun rememberSessionFlowState(
    initialStatus: SessionStatus = OPERATIONAL,
): SessionFlowState {
    val sessionFlowState = rememberSaveable(
        stateSaver = SessionFlowSaver
    ) {
        mutableStateOf(
            SessionFlowState(
                status = initialStatus
            )
        )
    }
    return sessionFlowState.value
}

@ExperimentalComposeApi
class SessionFlowState internal constructor(
    internal var viewModel: EquinoxViewModel? = null,
    status: SessionStatus,
) {

    companion object {

        fun invokeOnUserDisconnected(
            onDisconnection: () -> Unit,
        ) {
            this.onUserDisconnected = onDisconnection
        }

        internal var onUserDisconnected: (() -> Unit)? = null

    }

    private var previousStatus: SessionStatus = status

    val currentStatus = mutableStateOf(status)

    fun notifyAsOperational() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = OPERATIONAL
            viewModel?.restartRetriever()
        }
    }

    fun notifyUserDisconnected() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = USER_DISCONNECTED
            viewModel?.suspendRetriever()
        }
    }

    fun notifyServerOffline() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = SERVER_OFFLINE
            viewModel?.suspendRetriever()
        }
    }

    fun handleConnectivityStatus(
        isConnected: Boolean,
    ) {
        if (currentStatus.value != NO_NETWORK_CONNECTION)
            previousStatus = currentStatus.value
        if (isConnected) {
            currentStatus.value = previousStatus
            viewModel?.restartRetriever()
        } else {
            currentStatus.value = NO_NETWORK_CONNECTION

        }
    }

    private fun whenNetworkAvailable(
        then: () -> Unit,
    ) {
        if (currentStatus.value != NO_NETWORK_CONNECTION)
            then()
    }

}

@ExperimentalComposeApi
internal object SessionFlowSaver : Saver<SessionFlowState, SessionStatus> {

    override fun restore(
        value: SessionStatus,
    ): SessionFlowState {
        return SessionFlowState(
            status = value
        )
    }

    override fun SaverScope.save(
        value: SessionFlowState,
    ): SessionStatus {
        return value.currentStatus.value
    }

}