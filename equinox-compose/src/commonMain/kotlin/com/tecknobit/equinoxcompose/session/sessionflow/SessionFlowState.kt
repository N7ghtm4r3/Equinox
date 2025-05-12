package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*

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
    status: SessionStatus,
) {

    companion object {

        var onUserDisconnected: (() -> Unit)? = null

    }

    private var previousStatus: SessionStatus = status

    val currentStatus = mutableStateOf(status)

    fun notifyAsOperational() {
        previousStatus = currentStatus.value
        currentStatus.value = OPERATIONAL
    }

    fun notifyUserDisconnected() {
        previousStatus = currentStatus.value
        currentStatus.value = USER_DISCONNECTED
    }

    fun notifyServerOffline() {
        previousStatus = currentStatus.value
        currentStatus.value = SERVER_OFFLINE
    }

    fun handleConnectivityStatus(
        isConnected: Boolean,
    ) {
        if (currentStatus.value != NO_INTERNET_CONNECTION)
            previousStatus = currentStatus.value
        if (isConnected)
            currentStatus.value = previousStatus
        else
            currentStatus.value = NO_INTERNET_CONNECTION
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