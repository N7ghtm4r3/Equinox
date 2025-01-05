package com.tecknobit.equinoxcore.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
    return HttpClient(OkHttp) {
        engine {
            config {
                connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                if (enableCertificatesValidation) {
                    val sslContext = SSLContext.getInstance("TLS")
                    val certificates = validateSelfSignedCertificate()
                    sslContext.init(null, certificates, SecureRandom())
                    sslSocketFactory(sslContext.socketFactory, certificates[0] as X509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }
            }
        }
    }
}

/**
 * Method to validate a self-signed SLL certificate and bypass the checks of its validity<br></br>
 *
 * @return list of trust managers as [Array] of [TrustManager]
 *
 * > [!WARNING]
 * > this method disable all checks on the SLL certificate validity, so is recommended to
 * > use for test only or in a private distribution on own infrastructure
 */
private fun validateSelfSignedCertificate(): Array<TrustManager> {
    return arrayOf(
        @Suppress("CustomX509TrustManager")
        object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Suppress("TrustAllX509TrustManager")
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
            }

            @Suppress("TrustAllX509TrustManager")
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
            }
        })
}