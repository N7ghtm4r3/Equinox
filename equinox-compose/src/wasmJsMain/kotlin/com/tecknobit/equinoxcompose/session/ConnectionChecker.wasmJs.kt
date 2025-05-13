package com.tecknobit.equinoxcompose.session

import dev.jordond.connectivity.Connectivity

/**
 * `POLLING_URL` the url to use for the polling operations
 */
private const val POLLING_URL = "https://api.binance.com/api/v3/ping"

/**
 * Method used to create a monitor connectivity instance specific for each platform.
 * It is useful to monitor the connection status and adapt the content by the [com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer]
 * component
 *
 * @return the connectivity instance as [Connectivity]
 */
actual fun createConnectivity(): Connectivity {
    return Connectivity {
        urls(POLLING_URL)
        autoStart = true
        pollingIntervalMs = 2.seconds
    }
}