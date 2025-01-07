package com.tecknobit.equinoxcompose.helpers.session

import dev.jordond.connectivity.Connectivity
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.http.*

// FIXME: TO FIX CORS ISSUES 
actual fun createConnectivity(): Connectivity {
    return Connectivity(
        httpClient = HttpClient(Js) {
            headers {
                append("Access-Control-Allow-Origin", "*")
            }
        }
    ) {
        //urls("8.8.8.8")
        autoStart = true
        pollingIntervalMs = 2.seconds
    }
}