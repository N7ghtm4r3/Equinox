package com.tecknobit.equinoxcore.network

import io.ktor.client.*

/**
 * Method used to obtain a platform-based http client engine to execute the HTTP requests
 *
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param byPassSSLValidation Whether bypass the **SSL** certificates validation, this for example
 * when is a self-signed the certificate USE WITH CAUTION
 *
 * @return a customized platform-based http client engine as [HttpClient]
 */
internal expect fun obtainHttpEngine(
    connectionTimeout: Long,
    byPassSSLValidation: Boolean,
): HttpClient