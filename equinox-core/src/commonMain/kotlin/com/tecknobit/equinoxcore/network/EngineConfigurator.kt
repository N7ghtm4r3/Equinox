package com.tecknobit.equinoxcore.network

import io.ktor.client.*

/**
 * Method to obtain a platform-based http client engine to execute the HTTP requests
 *
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param enableCertificatesValidation: whether enable the **SSL** certificates validation, this for example
 * when the certificate is a self-signed certificate to by-pass
 *
 * @return a customized platform-based http client engine as [HttpClient]
 */
internal expect fun obtainHttpEngine(
    connectionTimeout: Long,
    enableCertificatesValidation: Boolean,
): HttpClient