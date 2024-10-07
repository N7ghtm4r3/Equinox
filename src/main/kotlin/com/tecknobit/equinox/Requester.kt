package com.tecknobit.equinox

import com.tecknobit.apimanager.annotations.Wrapper
import com.tecknobit.apimanager.apis.APIRequest
import com.tecknobit.apimanager.apis.APIRequest.*
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.*
import com.tecknobit.apimanager.formatters.JsonHelper
import com.tecknobit.apimanager.formatters.TimeFormatter
import com.tecknobit.equinox.inputs.InputValidator.DEFAULT_LANGUAGE
import com.tecknobit.mantis.Mantis
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * The **Requester** class is useful to communicate with backend based on the **SpringBoot** framework
 *
 * @param host: the host address where is running the backend
 * @param userId: the user identifier
 * @param userToken: the user token
 * @param debugMode: whether the requester is still in development and who is developing needs the log of the requester's
 * workflow, if it is enabled all the details of the requests sent and the errors occurred will be printed in the console
 * @param connectionTimeout: time to keep alive request then throw the connection refused error
 * @param connectionErrorMessage: the error to send when a connection error occurred
 * @param enableCertificatesValidation: whether enable the **SSL** certificates validation, this for example
 * when the certificate is a self-signed certificate to by-pass
 *
 * @author N7ghtm4r3 - Tecknobit
 */
abstract class Requester (
    protected var host: String,
    protected var userId: String? = null,
    protected var userToken: String? = null,
    protected var debugMode: Boolean = false,
    protected val connectionTimeout: Long = DEFAULT_REQUEST_TIMEOUT.toLong(),
    protected val connectionErrorMessage: String = DEFAULT_CONNECTION_ERROR_MESSAGE,
    protected val enableCertificatesValidation: Boolean = false
) {

    companion object {

        /**
         * **USER_IDENTIFIER_KEY** the key for the user <b>"id"</b> field
         */
        const val USER_IDENTIFIER_KEY = "id"

        /**
         * **USER_TOKEN_KEY** the key for the user <b>"token"</b> field
         */
        const val USER_TOKEN_KEY = "token"

        /**
         * **RESPONSE_STATUS_KEY** the key for the <b>"status"</b> field
         */
        const val RESPONSE_STATUS_KEY: String = "status"

        /**
         * **RESPONSE_MESSAGE_KEY** the key for the <b>"response"</b> field
         */
        const val RESPONSE_MESSAGE_KEY: String = "response"

        /**
         * **DEFAULT_CONNECTION_ERROR_MESSAGE** the message to send when an error during the communication with the
         * backend occurred
         */
        const val DEFAULT_CONNECTION_ERROR_MESSAGE = "connection_error_message_key"

    }

    /**
     * `mantis` the translations manager
     */
    protected val mantis = Mantis(DEFAULT_LANGUAGE)

    /**
     * `timeFormatter` the formatter used to format the timestamp values
     */
    protected val timeFormatter: TimeFormatter = TimeFormatter.getInstance()

    /**
     * **apiRequest** -> the instance to communicate and make the requests to the backend
     */
    protected val apiRequest = APIRequest(connectionTimeout)

    /**
     * **headers** the headers used in the request
     */
    protected val headers = Headers()

    /**
     * **mustValidateCertificates** flag whether the requests must validate the **SSL** certificates, this for example
     * when the SSL is a self-signed certificate
     */
    protected var mustValidateCertificates: Boolean = false

    /**
     * **initHost** function to init correctly the [host] value
     */
    private val initHost by lazy {
        {
            changeHost(host)
        }
    }

    init {
        initHost.invoke()
        setUserCredentials(userId, userToken)
    }

    /**
     * Function to set the user credentials used to make the authenticated requests
     *
     * @param userId: the user identifier to use
     * @param userToken: the user token to use
     */
    fun setUserCredentials(
        userId: String?,
        userToken: String?
    ) {
        this.userId = userId
        this.userToken = userToken
        if(userToken != null)
            headers.addHeader(USER_TOKEN_KEY, userToken)
    }

    /**
     * Function to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host: the new host address to use
     */
    open fun changeHost(
        host: String
    ) {
        this.host = host
        if(enableCertificatesValidation)
            mustValidateCertificates = host.startsWith("https")
    }

    /**
     * Function to set programmatically timeout for the requests
     *
     * @param connectionTimeout: timeout for the requests
     */
    fun setConnectionTimeout(
        connectionTimeout: Long
    ) {
        apiRequest.setConnectionTimeout(connectionTimeout)
    }

    /**
     * Function to execute a [RequestMethod.GET] request to the backend
     *
     * @param endpoint: the endpoint path of the request url
     *
     * @return the result of the request as [JSONObject]
     */
    @Wrapper
    protected fun execGet(
        endpoint: String
    ) : JSONObject {
        return execRequest(
            method = RequestMethod.GET,
            endpoint = endpoint
        )
    }

    /**
     * Function to execute a [RequestMethod.POST] request to the backend
     *
     * @param endpoint: the endpoint path of the request url
     * @param payload: the payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    @Wrapper
    protected fun execPost(
        endpoint: String,
        payload: Params = Params()
    ) : JSONObject {
        return execRequest(
            method = RequestMethod.POST,
            endpoint = endpoint,
            payload = payload
        )
    }

    /**
     * Function to execute a [RequestMethod.PUT] request to the backend
     *
     * @param endpoint: the endpoint path of the request url
     * @param payload: the payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    @Wrapper
    protected fun execPut(
        endpoint: String,
        payload: Params = Params()
    ) : JSONObject {
        return execRequest(
            method = RequestMethod.PUT,
            endpoint = endpoint,
            payload = payload
        )
    }

    /**
     * Function to execute a [RequestMethod.PATCH] request to the backend
     *
     * @param endpoint: the endpoint path of the request url
     * @param payload: the payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    @Wrapper
    protected fun execPatch(
        endpoint: String,
        payload: Params = Params()
    ) : JSONObject {
        return execRequest(
            method = RequestMethod.PATCH,
            endpoint = endpoint,
            payload = payload
        )
    }

    /**
     * Function to execute a [RequestMethod.DELETE] request to the backend
     *
     * @param endpoint: the endpoint path of the request url
     * @param payload: the payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    @Wrapper
    protected fun execDelete(
        endpoint: String,
        payload: Params? = null
    ) : JSONObject {
        return execRequest(
            method = RequestMethod.DELETE,
            endpoint = endpoint,
            payload = payload
        )
    }

    /**
     * Function to execute a request to the backend
     *
     * @param method: the method of the request
     * @param endpoint: the endpoint path of the request url
     * @param payload: the payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    private fun execRequest(
        method: RequestMethod,
        endpoint: String,
        payload: Params? = null
    ) : JSONObject {
        var response: String? = null
        var jResponse: JSONObject
        if(mustValidateCertificates)
            apiRequest.validateSelfSignedCertificate()
        val requestUrl = host + endpoint
        runBlocking {
            try {
                async {
                    try {
                        if(payload != null) {
                            apiRequest.sendJSONPayloadedAPIRequest(
                                requestUrl,
                                method,
                                headers,
                                payload
                            )
                        } else {
                            apiRequest.sendAPIRequest(
                                requestUrl,
                                method,
                                headers
                            )
                        }
                        response = apiRequest.response
                        if (response == null)
                            response = apiRequest.errorResponse
                    } catch (e: IOException) {
                        logError(
                            exception = e
                        )
                        response = connectionErrorMessage().toString()
                    }
                }.await()
                jResponse = JSONObject(response)
            } catch (e: Exception) {
                logError(
                    exception = e
                )
                jResponse = connectionErrorMessage()
            }
        }
        logRequestInfo(
            requestUrl = requestUrl,
            requestPayloadInfo = {
                if (payload != null) {
                    println("\n-PAYLOAD")
                    println(payload.createJSONPayload().toString(4))
                }
            },
            response = jResponse
        )
        return jResponse
    }

    /**
     * Function to exec a multipart body  request
     *
     * @param endpoint: the endpoint path of the url
     * @param body: the body payload of the request
     *
     * @return the result of the request as [JSONObject]
     */
    protected fun execMultipartRequest(
        endpoint: String,
        body: MultipartBody
    ): JSONObject {
        val mHeaders = mutableMapOf<String, String>()
        headers.headersKeys.forEach { headerKey ->
            mHeaders[headerKey] = headers.getHeader(headerKey)
        }
        val requestUrl = "$host$endpoint"
        val request: Request = Request.Builder()
            .headers(mHeaders.toHeaders())
            .url(requestUrl)
            .post(body)
            .build()
        val client = validateSelfSignedCertificate(OkHttpClient())
        var response: JSONObject? = null
        runBlocking {
            try {
                async {
                    response = try {
                        client.newCall(request).execute().body?.string()?.let { JSONObject(it) }
                    } catch (e: IOException) {
                        logError(
                            exception = e
                        )
                        JSONObject(connectionErrorMessage())
                    }
                }.await()
            } catch (e: Exception) {
                logError(
                    exception = e
                )
                response = JSONObject(connectionErrorMessage())
            }
        }
        logRequestInfo(
            requestUrl = requestUrl,
            requestPayloadInfo = {
                println("\n-PAYLOAD")
                body.parts.forEachIndexed { index, part ->
                    println("---------------------- $index ----------------------------")
                    val bodyPart = part.body
                    print("| " + part.headers)
                    println("| Content-Type: ${bodyPart.contentType()}")
                    println("| Content-Length: ${bodyPart.contentLength()}")
                    if (index == (body.size - 1))
                        println("-----------------------------------------------------")
                }
            },
            response = response
        )
        return response!!
    }

    /**
     * Method to validate a self-signed SLL certificate and bypass the checks of its validity<br></br>
     * No-any params required
     *
     * @apiNote this method disable all checks on the SLL certificate validity, so is recommended to use for test only or
     * in a private distribution on own infrastructure
     */
    private fun validateSelfSignedCertificate(
        okHttpClient: OkHttpClient
    ): OkHttpClient {
        if (mustValidateCertificates) {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }


                    override fun checkClientTrusted(
                        certs: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun checkServerTrusted(
                        certs: Array<X509Certificate>,
                        authType: String
                    ) {
                    }
                })
            val builder = okHttpClient.newBuilder()
            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, SecureRandom())
                builder.sslSocketFactory(
                    sslContext.socketFactory,
                    trustAllCerts[0] as X509TrustManager
                )
                builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
                return builder.build()
            } catch (ignored: java.lang.Exception) {
            }
        }
        return OkHttpClient()
    }

    /**
     * Function to print the details of the request sent if the [debugMode] is enabled
     *
     * @param requestUrl: the url of the request
     * @param requestPayloadInfo: the payload of the request if sent with the request
     * @param response: the response of the request sent
     */
    private fun logRequestInfo(
        requestUrl: String,
        requestPayloadInfo: () -> Unit,
        response: JSONObject?
    ) {
        if (debugMode) {
            synchronized(this) {
                println("----------- REQUEST ${timeFormatter.formatNowAsString()} -----------")
                println("-URL\n$requestUrl")
                requestPayloadInfo.invoke()
                if (response != null)
                    println("\n-RESPONSE\n${response.toString(4)}")
                println("---------------------------------------------------")
            }
        }
    }

    /**
     * Function to print a log of an exception occurred during a request sent if the [debugMode] is enabled
     *
     * @param exception: the exception occurred
     */
    private fun logError(
        exception: Exception
    ) {
        if (debugMode)
            exception.printStackTrace()
    }

    /**
     * Function to set the [RESPONSE_STATUS_KEY] to send when an error during the connection occurred
     *
     * No-any params required
     *
     * @return the error message as [JSONObject]
     */
    protected fun connectionErrorMessage(): JSONObject {
        return JSONObject()
            .put(RESPONSE_STATUS_KEY, GENERIC_RESPONSE.name)
            .put(RESPONSE_MESSAGE_KEY, mantis.getResource(connectionErrorMessage))
    }

    /**
     * Function to execute and manage the response of a request
     *
     * @param request: the request to execute
     * @param onResponse: the action to execute when a response is returned from the backend
     * @param onConnectionError: the action to execute if the request has been failed for a connection error
     */
    @Wrapper
    fun sendRequest(
        request: () -> JSONObject,
        onResponse: (JsonHelper) -> Unit,
        onConnectionError: ((JsonHelper) -> Unit)? = null
    ) {
        return sendRequest(
            request = request,
            onSuccess = onResponse,
            onFailure = onResponse,
            onConnectionError = onConnectionError
        )
    }

    /**
     * Function to execute and manage the response of a request
     *
     * @param request: the request to execute
     * @param onSuccess: the action to execute if the request has been successful
     * @param onFailure: the action to execute if the request has been failed
     * @param onConnectionError: the action to execute if the request has been failed for a connection error
     */
    fun sendRequest(
        request: () -> JSONObject,
        onSuccess: (JsonHelper) -> Unit,
        onFailure: (JsonHelper) -> Unit,
        onConnectionError: ((JsonHelper) -> Unit)? = null
    ) {
        val response = request.invoke()
        val hResponse = JsonHelper(response)
        when(isSuccessfulResponse(response)) {
            SUCCESSFUL -> onSuccess.invoke(hResponse)
            GENERIC_RESPONSE -> {
                if(onConnectionError != null)
                    onConnectionError.invoke(hResponse)
                else
                    onFailure.invoke(hResponse)
            }
            else -> onFailure.invoke(hResponse)
        }
    }

    /**
     * Function to get whether the request has been successful or not
     *
     * @param response: the response of the request
     *
     * @return whether the request has been successful or not as [StandardResponseCode]
     */
    private fun isSuccessfulResponse(
        response: JSONObject?
    ): StandardResponseCode {
        if(response == null || !response.has(RESPONSE_STATUS_KEY))
            return FAILED
        return when(response.getString(RESPONSE_STATUS_KEY)) {
            SUCCESSFUL.name -> SUCCESSFUL
            GENERIC_RESPONSE.name -> GENERIC_RESPONSE
            else -> FAILED
        }
    }

}