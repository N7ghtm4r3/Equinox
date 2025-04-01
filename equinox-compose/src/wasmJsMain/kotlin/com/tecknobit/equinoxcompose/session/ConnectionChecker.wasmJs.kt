package com.tecknobit.equinoxcompose.session

import dev.jordond.connectivity.Connectivity
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.fetch.*
import io.ktor.client.plugins.*
import io.ktor.http.*

/**
 * `POLLING_URL` the url to use for the polling operations
 */
private const val POLLING_URL = "https://api.binance.com/api/v3/ping"

/**
 * Method used to create a monitor connectivity instance specific for each platform.
 * It is useful to monitor the connection status and adapt the content by the [com.tecknobit.equinoxcompose.helpers.session.ManagedContent]
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