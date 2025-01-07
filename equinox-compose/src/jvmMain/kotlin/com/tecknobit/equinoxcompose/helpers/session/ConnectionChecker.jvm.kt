package com.tecknobit.equinoxcompose.helpers.session

import dev.jordond.connectivity.Connectivity

actual fun createConnectivity(): Connectivity {
    return Connectivity {
        autoStart = true
        pollingIntervalMs = 2.seconds
    }
}