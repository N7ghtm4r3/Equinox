package com.tecknobit.equinoxcompose.helpers.session

import dev.jordond.connectivity.Connectivity

/**
 * Method to create a monitor connectivity instance specific for each platform.
 * It is useful to monitor the connection status and adapt the content by the [com.tecknobit.equinoxcompose.helpers.session.ManagedContent]
 * component
 *
 * @return the connectivity instance as [Connectivity]
 */
actual fun createConnectivity(): Connectivity {
    return Connectivity {
        autoStart = true
        pollingIntervalMs = 2.seconds
    }
}