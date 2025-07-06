package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.time.TimeFormatter

/**
 * Method used to create and remember during the recompositions the state for the [SessionFlowContainer] component
 *
 * @param initialStatus Arbitrary value to assign to the state as initial status
 *
 * @return the state as [SessionFlowState]
 */
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

/**
 * The `SessionFlowState` class is useful to handle the [SessionFlowContainer] component lifecycle
 *
 * @property status The current status of the session handled by the component
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.2
 */
@ExperimentalComposeApi
class SessionFlowState internal constructor(
    status: SessionStatus,
) {

    companion object {

        /**
         * Method used to set the callback to execute when the [SessionStatus] is [USER_DISCONNECTED]
         *
         * @param onDisconnection The callback to invoke
         */
        fun invokeOnUserDisconnected(
            onDisconnection: () -> Unit,
        ) {
            this.onUserDisconnected = onDisconnection
        }

        /**
         * `onUserDisconnected` callback invoked when the user disconnected
         */
        internal var onUserDisconnected: (() -> Unit)? = null

    }

    /**
     * `previousStatus` the previous status set before [NO_NETWORK_CONNECTION] event registered
     */
    private var previousStatus: SessionStatus = status

    /**
     * `currentStatus` the current status of the session
     */
    internal val currentStatus = mutableStateOf(status)

    /**
     * `viewModel` used to autonomously suspend and restart the [com.tecknobit.equinoxcompose.session.Retriever]'s
     * routine
     */
    private var viewModel: EquinoxViewModel? = null

    /**
     * `onReconnection` optional callback to invoke after the connection has been reestablished
     */
    private var onReconnection: (() -> Unit)? = null

    /**
     * `loadingRoutineTrigger` trigger used to automatically invoke the
     * [com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer]'s loading routine
     */
    @ExperimentalComposeApi
    internal val loadingRoutineTrigger: MutableLongState = mutableLongStateOf(TimeFormatter.currentTimestamp())

    /**
     * Method used to attach the viewmodel to the state
     *
     * @param viewModel The viewmodel to attach
     */
    fun attachViewModel(
        viewModel: EquinoxViewModel?,
    ) {
        this.viewModel = viewModel
    }

    /**
     * Method to set an optional callback to invoke after the connection has been reestablished
     * @param onReconnection Optional callback to invoke after the connection has been reestablished
     */
    fun performOnReconnection(
        onReconnection: (() -> Unit)?,
    ) {
        this.onReconnection = onReconnection
    }

    /**
     * Method used to notify the [OPERATIONAL] session status
     */
    fun notifyOperational() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = OPERATIONAL
            viewModel?.restartRetriever()
        }
    }

    /**
     * Method used to notify the [USER_DISCONNECTED] session status
     */
    fun notifyUserDisconnected() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = USER_DISCONNECTED
            viewModel?.suspendRetriever()
        }
    }

    /**
     * Method used to notify the [SERVER_OFFLINE] session status
     */
    fun notifyServerOffline() {
        whenNetworkAvailable {
            previousStatus = currentStatus.value
            currentStatus.value = SERVER_OFFLINE
            viewModel?.suspendRetriever()
        }
    }

    /**
     * Method used to handle the current network status
     *
     * @param isConnected Whether the network connection is currently available
     */
    fun handleConnectivityStatus(
        isConnected: Boolean,
    ) {
        if (currentStatus.value != NO_NETWORK_CONNECTION)
            previousStatus = currentStatus.value
        if (isConnected) {
            if (currentStatus.value == NO_NETWORK_CONNECTION) {
                viewModel?.restartRetriever()
                onReconnection?.invoke()
            }
            currentStatus.value = previousStatus
        } else {
            currentStatus.value = NO_NETWORK_CONNECTION
            viewModel?.suspendRetriever()
        }
    }

    /**
     * Method used to update the value of the [loadingRoutineTrigger] to automatically invoke the
     * [com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer]'s loading routine
     *
     * @param onReload Callback to invoke when [loadingRoutineTrigger] has been updated
     */
    @ExperimentalComposeApi
    fun reload(
        onReload: (() -> Unit)? = null,
    ) {
        onReload?.invoke()
        loadingRoutineTrigger.value = TimeFormatter.currentTimestamp()
    }

    /**
     * Container method used to safely handle the session status changes when the
     * network connection is available
     *
     * @param then Callback invoked when the network connection is available
     */
    private fun whenNetworkAvailable(
        then: () -> Unit,
    ) {
        if (currentStatus.value != NO_NETWORK_CONNECTION)
            then()
    }

}

/**
 * The `SessionFlowSaver` custom saver allows to keep in memory the [SessionFlowState] during the recompositions
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see Saver
 *
 * @since 1.1.2
 */
@ExperimentalComposeApi
internal object SessionFlowSaver : Saver<SessionFlowState, SessionStatus> {

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun restore(
        value: SessionStatus,
    ): SessionFlowState {
        return SessionFlowState(
            status = value
        )
    }

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun SaverScope.save(
        value: SessionFlowState,
    ): SessionStatus {
        return value.currentStatus.value
    }

}