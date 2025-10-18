package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.ExperimentalComposeApi
import com.tecknobit.equinoxcompose.session.sessionflow.SessionStatus.SERVER_OFFLINE

/**
 * The `SessionStateFlowConsumer` interface allows consuming a [SessionFlowState] instance and provides methods
 * to handle its lifecycle with dedicated wrapper methods.
 *
 * This, for example, it is useful to apply to an [com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel] to directly
 * invoke the methods that handle the session state using the provided lifecycle methods
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.4
 */
@ExperimentalComposeApi
interface SessionStateFlowConsumer {

    /**
     * `sessionFlowState` the state used to manage the session lifecycle in the screen
     */
    var sessionFlowState: SessionFlowState

    /**
     * Method used to notify the [SERVER_OFFLINE] session status and to invoke the [performOnServerOffline] callback
     */
    fun notifyServerOffline() {
        sessionFlowState.notifyServerOffline()
        performOnServerOffline()
    }

    /**
     * Routine to perform when the server is currently offline
     */
    fun performOnServerOffline()

}