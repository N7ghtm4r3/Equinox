package com.tecknobit.equinoxcompose.session.sessionflow

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