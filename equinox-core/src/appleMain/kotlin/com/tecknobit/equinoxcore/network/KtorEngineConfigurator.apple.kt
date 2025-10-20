package com.tecknobit.equinoxcore.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

/**
 * Method used to obtain a platform-based http client engine to execute the HTTP requests
 *
 * @param requestTimeout Maximum time to wait before a timeout exception is thrown
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param socketTimeout Maximum idle time to wait during an I/O operation on a socket
 * @param byPassSSLValidation Whether bypass the `SSL` certificates validation, this for example
 * when is a self-signed the certificate USE WITH CAUTION
 *
 * @return a customized platform-based http client engine as [HttpClient]
 */
internal actual fun obtainHttpEngine(
    requestTimeout: Long,
    connectionTimeout: Long,
    socketTimeout: Long,
    byPassSSLValidation: Boolean,
): HttpClient {
    return HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = requestTimeout
            connectTimeoutMillis = connectionTimeout
            socketTimeoutMillis = socketTimeout
        }
        engine {
            this.requestTimeout = connectionTimeout
            if (byPassSSLValidation) {
                https {
                    serverName = null
                }
            }
        }
    }
}
