package com.tecknobit.equinoxcore.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*

/**
 * Method used to obtain a platform-based HTTP engine to execute HTTP requests.
 *
 * @param connectionTimeout Timeout duration for the connection.
 * @param byPassSSLValidation Whether bypass the **SSL** certificates validation, this for example
 * when is a self-signed the certificate USE WITH CAUTION
 *
 * @return a customized HTTP engine as [HttpClient]
 */
internal actual fun obtainHttpEngine(
    connectionTimeout: Long,
    byPassSSLValidation: Boolean,
): HttpClient {
    return HttpClient(CIO) {
        engine {
            requestTimeout = connectionTimeout
            if (byPassSSLValidation) {
                https {
                    serverName = null
                }
            }
        }
    }
}
