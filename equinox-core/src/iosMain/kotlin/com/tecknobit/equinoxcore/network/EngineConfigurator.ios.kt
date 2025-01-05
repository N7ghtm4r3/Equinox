package com.tecknobit.equinoxcore.network

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.*
import platform.Foundation.*
import platform.Security.*
import kotlin.native.concurrent.freeze

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
    return HttpClient(Darwin) {
        engine {
            configureRequest {
                setTimeoutInterval(connectionTimeout / 1000.0)
            }
            if (enableCertificatesValidation) {
                handleChallenge { _, completionHandler ->
                    val trustManagers = validateSelfSignedCertificate()
                    completionHandler(
                        NSURLSessionAuthChallengeDisposition.UseCredential,
                        NSURLCredential.create(trustManagers)
                    )
                }
            }
        }
    }
}

/**
 * Method to validate a self-signed SSL certificate and bypass the checks of its validity<br></br>
 *
 * @return a customized NSURLCredential for bypassing certificate validation.
 *
 * > [!WARNING]
 * > This method disables all checks on the SSL certificate validity, so it is recommended to
 * > use for testing purposes only or in a private distribution on your infrastructure.
 */
private fun validateSelfSignedCertificate(): SecTrust {
    return SecTrustCreateWithCertificates(
        certificates = emptyList<Any>().toCFArray(),
        policies = null
    ).apply {
        this?.freeze()
    }
}

/**
 * Extension function to convert a list of objects (such as certificates) into a CFArrayRef (Core Foundation array).
 * This is used to create a Core Foundation array from a Kotlin List, which is necessary for creating a SecTrust object.
 *
 * @return CFArrayRef created from the list of objects (empty in this case).
 */
private fun List<Any>.toCFArray(): CFArrayRef? {
    return CFArrayCreate(
        null,
        this.toTypedArray().map { it }.toCValues(),
        this.size.toLong(),
        null
    )
}
