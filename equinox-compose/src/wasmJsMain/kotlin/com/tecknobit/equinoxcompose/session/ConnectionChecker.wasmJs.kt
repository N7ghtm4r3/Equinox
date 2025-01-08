package com.tecknobit.equinoxcompose.session

import dev.jordond.connectivity.Connectivity
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.fetch.*
import io.ktor.http.*

/**
 * Method to create a monitor connectivity instance specific for each platform.
 * It is useful to monitor the connection status and adapt the content by the [com.tecknobit.equinoxcompose.helpers.session.ManagedContent]
 * component
 *
 * @return the connectivity instance as [Connectivity]
 */
actual fun createConnectivity(): Connectivity {
    return Connectivity(
        httpClient = HttpClient(Js) {
            // FIXME: TO FIX CORS ISSUES
        }
    ) {
        autoStart = true
        pollingIntervalMs = 2.seconds
    }
}