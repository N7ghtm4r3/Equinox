package com.tecknobit.equinoxcompose.session.sessionflow

import androidx.compose.runtime.ExperimentalComposeApi

/**
 * List of the possible statuses of the session
 *
 * @since 1.1.2
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
     * `NO_NETWORK_CONNECTION` the status of the session when there is no network connection
     */
    NO_NETWORK_CONNECTION,

    /**
     * `USER_DISCONNECTED` the status of the session when the user has been disconnected
     */
    USER_DISCONNECTED,

    /**
     * `CUSTOM` status that allows to customize the notification of error or any custom status as needed
     *
     * @since 1.1.4
     */
    @ExperimentalComposeApi
    CUSTOM

}