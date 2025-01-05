package com.tecknobit.equinoxcore.network

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*

/**
 * Method to obtain a platform-based http client engine to execute the HTTP requests
 *
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param enableCertificatesValidation: whether enable the **SSL** certificates validation, this for example
 * when the certificate is a self-signed certificate to by-pass
 *
 * @return a customized platform-based http client engine as [HttpClient]
 */
internal actual fun obtainHttpEngine(
    connectionTimeout: Long,
    enableCertificatesValidation: Boolean,
): HttpClient {
    return HttpClient(Js) {
        install(HttpTimeout) {
            connectTimeoutMillis = connectionTimeout
        }
    }
}