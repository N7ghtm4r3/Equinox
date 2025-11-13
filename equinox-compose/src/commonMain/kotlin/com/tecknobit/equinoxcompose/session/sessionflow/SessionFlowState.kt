package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.*
import com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.time.TimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Method used to create and remember during the recompositions the state for the [SessionFlowContainer] component
 *
 * @param initialStatus Arbitrary value to assign to the state as initial status
 *
 * @return the state as [SessionFlowState]
 */
@Composable
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
 * @param status The current status of the session handled by the component
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.2
 */
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
    internal val _currentStatus = MutableStateFlow(
        value = status
    )
    val currentStatus: StateFlow<SessionStatus> = _currentStatus.asStateFlow()

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
     * `customErrorExtra` the latest extra value set related to custom error used to display the specific content
     */
    internal lateinit var customErrorExtra: Any

    /**
     * `loadingRoutineTrigger` trigger used to automatically invoke the
     * [com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer]'s loading routine
     */
    internal val loadingRoutineTrigger: MutableLongState = mutableLongStateOf(TimeFormatter.currentTimestamp())

    /**
     * `isLoading` Whether the session is currently loading data.
     *
     * Its value will be `true` just when the [_currentStatus] is [SessionStatus.OPERATIONAL] and have been specified
     * a `loadingRoutine` that is currently performing, otherwise will be ever `false`. It can be considered a `pseudo-state`
     * of the session and it is internally handled
     *
     * @since 1.1.8
     */
    private val _isLoading = MutableStateFlow(
        value = false
    )
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
    @Wrapper
    fun notifyOperational() {
        setAndRestart(
            status = OPERATIONAL
        )
    }

    /**
     * Method used to notify the [USER_DISCONNECTED] session status
     */
    @Wrapper
    fun notifyUserDisconnected() {
        setAndSuspend(
            status = USER_DISCONNECTED
        )
    }

    /**
     * Method used to notify the [SERVER_OFFLINE] session status
     */
    @Wrapper
    fun notifyServerOffline() {
        setAndSuspend(
            status = SERVER_OFFLINE
        )
    }

    /**
     * Method used to notify the [CUSTOM] session status
     *
     * @param errorExtra Extra value related to custom error used to display the specific content
     *
     * @since 1.1.4
     */
    @Wrapper
    fun notifyCustomError(
        errorExtra: Any,
    ) {
        setAndSuspend(
            status = CUSTOM,
            onSet = {
                customErrorExtra = errorExtra
            }
        )
    }

    /**
     * Method used to set a status and optionally suspend the [EquinoxViewModel]'s routine
     *
     * @param status The status to set
     * @param onSet The callback to invoke when the status has been set
     */
    private fun setAndSuspend(
        status: SessionStatus,
        onSet: (() -> Unit)? = null,
    ) {
        whenNetworkAvailable {
            onSet?.invoke()
            previousStatus = _currentStatus.value
            _currentStatus.value = status
            viewModel?.suspendRetriever()
            notifyLoadingEnd()
        }
    }

    /**
     * Method used to set a status and optionally restart the [EquinoxViewModel]'s routine
     *
     * @param status The status to set
     * @param onSet The callback to invoke when the status has been set
     */
    private fun setAndRestart(
        status: SessionStatus,
        onSet: (() -> Unit)? = null,
    ) {
        whenNetworkAvailable {
            onSet?.invoke()
            previousStatus = _currentStatus.value
            _currentStatus.value = status
            viewModel?.restartRetriever()
        }
    }

    /**
     * Container method used to safely handle the session status changes when the
     * network connection is available
     *
     * @param then Callback invoked when the network connection is available
     */
    private inline fun whenNetworkAvailable(
        then: () -> Unit,
    ) {
        if (_currentStatus.value != NO_NETWORK_CONNECTION)
            then()
    }

    /**
     * Method used to handle the current network status
     *
     * @param isConnected Whether the network connection is currently available
     */
    fun handleConnectivityStatus(
        isConnected: Boolean,
    ) {
        if (_currentStatus.value != NO_NETWORK_CONNECTION)
            previousStatus = _currentStatus.value
        if (isConnected) {
            if (_currentStatus.value == NO_NETWORK_CONNECTION) {
                viewModel?.restartRetriever()
                onReconnection?.invoke()
            }
            _currentStatus.value = previousStatus
        } else {
            _currentStatus.value = NO_NETWORK_CONNECTION
            viewModel?.suspendRetriever()
            notifyLoadingEnd()
        }
    }

    /**
     * Method used to update the value of the [loadingRoutineTrigger] to automatically invoke the
     * [com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer]'s loading routine
     *
     * @param onReload Callback to invoke when [loadingRoutineTrigger] has been updated
     */
    fun reload(
        onReload: (() -> Unit)? = null,
    ) {
        onReload?.invoke()
        loadingRoutineTrigger.value = TimeFormatter.currentTimestamp()
    }

    /**
     * Utility method used to check whether the [_currentStatus] is currently [OPERATIONAL].
     * This helps avoid repeating the same status check in multiple places throughout the code.
     *
     * @return whether the current status is [OPERATIONAL] as [Boolean]
     *
     * @since 1.1.8
     */
    fun isOperational(): Boolean {
        return _currentStatus.value == OPERATIONAL
    }

    /**
     * Utility method used to check whether the [_currentStatus] is currently [SERVER_OFFLINE].
     * This helps avoid repeating the same status check in multiple places throughout the code.
     *
     * @return whether the current status is [SERVER_OFFLINE] as [Boolean]
     *
     * @since 1.1.8
     */
    fun isServerOffline(): Boolean {
        return _currentStatus.value == SERVER_OFFLINE
    }

    /**
     * Utility method used to check whether the [_currentStatus] is currently [NO_NETWORK_CONNECTION].
     * This helps avoid repeating the same status check in multiple places throughout the code.
     *
     * @return whether the current status is [NO_NETWORK_CONNECTION] as [Boolean]
     *
     * @since 1.1.8
     */
    fun isNoNetworkConnection(): Boolean {
        return _currentStatus.value == NO_NETWORK_CONNECTION
    }

    /**
     * Utility method used to check whether the [_currentStatus] is currently [CUSTOM].
     * This helps avoid repeating the same status check in multiple places throughout the code.
     *
     * @return whether the current status is [CUSTOM] as [Boolean]
     *
     * @since 1.1.8
     */
    fun isOnCustomError(): Boolean {
        return _currentStatus.value == CUSTOM
    }

    /**
     * Method used to notify that is currently performing a `loading routine`, setting the [_isLoading] on `true`
     *
     * @since 1.1.8
     */
    internal fun notifyLoading() {
        _isLoading.value = true
    }

    /**
     * Method used to notify that is not more performing a `loading routine`, setting the [_isLoading] on `false`.
     *
     * That routine could be successfully completed or cancelled due network connection or other error states have been set
     *
     * @since 1.1.8
     */
    internal fun notifyLoadingEnd() {
        _isLoading.value = false
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
        return value._currentStatus.value
    }

}