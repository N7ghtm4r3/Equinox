package com.tecknobit.equinoxcore.network

import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.helpers.TOKEN_KEY
import com.tecknobit.equinoxcore.json.treatsAsString
import com.tecknobit.equinoxcore.network.Requester.Companion.RESPONSE_DATA_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.RESPONSE_STATUS_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.toNullableResponseData
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseArrayData
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseContent
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.equinoxcore.network.ResponseStatus.GENERIC_RESPONSE
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_KEY
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_SIZE_KEY
import com.tecknobit.equinoxcore.time.TimeFormatter
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.*
import kotlin.js.JsName

/**
 * The **Requester** class is useful to communicate with backend based on the **SpringBoot** framework
 *
 * @param host The host address where is running the backend
 * @param userId The user identifier
 * @param userToken The user token
 * @param debugMode Whether the requester is still in development and who is developing needs the log of the requester's
 * workflow, if it is enabled all the details of the requests sent and the errors occurred will be printed in the console
 * @param requestTimeout Maximum time to wait before a timeout exception is thrown
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param socketTimeout Maximum idle time to wait during an I/O operation on a socket
 * @param connectionErrorMessage The error to send when a connection error occurred
 * @param byPassSSLValidation Whether bypass the **SSL** certificates validation, this for example
 * when is a self-signed the certificate USE WITH CAUTION
 *
 * @author N7ghtm4r3 - Tecknobit
 */
abstract class Requester(
    protected var host: String,
    protected var userId: String? = null,
    protected var userToken: String? = null,
    protected var debugMode: Boolean = false,
    protected val requestTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    protected val connectionTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    protected val socketTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    @JsName("connection_error_message")
    protected val connectionErrorMessage: String,
    protected val byPassSSLValidation: Boolean = false,
) {

    companion object {

        /**
         * `USER_IDENTIFIER_KEY` The key for the user <b>"id"</b> field
         */
        @Deprecated(
            message = "Moved into CommonKeys",
            level = DeprecationLevel.ERROR
        )
        const val USER_IDENTIFIER_KEY = "id"

        /**
         * `USER_TOKEN_KEY` The key for the user <b>"token"</b> field
         */
        @Deprecated(
            message = "Will be removed in next release",
            level = DeprecationLevel.ERROR
        )
        const val USER_TOKEN_KEY = "token"

        /**
         * `RESPONSE_STATUS_KEY` The key for the <b>"status"</b> field
         */
        const val RESPONSE_STATUS_KEY: String = "status"

        /**
         * `RESPONSE_DATA_KEY` The key for the <b>"response"</b> field
         */
        const val RESPONSE_DATA_KEY: String = "response"

        /**
         * `DEFAULT_REQUEST_TIMEOUT` The timeout values in millis used in the requests
         */
        const val DEFAULT_REQUEST_TIMEOUT = 5000L

        /**
         * `DEFAULT_CONNECTION_ERROR_MESSAGE` The message to send when an error during the communication with the
         * backend occurred
         */
        const val DEFAULT_CONNECTION_ERROR_MESSAGE = "connection_error_message"

        /**
         * Method used to get directly the response data from the request response
         *
         * @return the [RESPONSE_DATA_KEY] value as nullable [JsonObject]
         *
         * ### Example
         * - The complete response
         *
         * ```json
         * {
         *   "response": null, 
         *   "status": "SUCCESSFUL"
         * }
         * ```
         *
         * - Use the [toNullableResponseData] Method:
         *
         * ```kotlin
         * requester.sendRequest(
         *     request = {
         *         // make a request
         *     },
         *     onResponse = { response ->
         *        val data: JsonObject? = response.toNullableResponseData()
         *        println(data) // will be printed null
         *    }
         * )
         * ```
         *
         */
        fun JsonObject.toNullableResponseData(): JsonObject? {
            val response = this[RESPONSE_DATA_KEY]
            return if (response is JsonNull)
                return null
            else
                response?.jsonObject
        }

        /**
         * Method used to get directly the response data from the request response
         *
         * @return the [RESPONSE_DATA_KEY] value as [JsonObject]
         *
         * ### Example
         * - The complete response
         *
         * ```json
         * {
         *   "response": {
         *      "data" : "value"
         *   },
         *   "status": "SUCCESSFUL"
         * }
         * ```
         *
         * - Use the [toResponseData] Method:
         *
         * ```kotlin
         * requester.sendRequest(
         *     request = {
         *         // make a request
         *     },
         *     onResponse = { response ->
         *        val data: JsonObject = response.toResponseData()
         *        println(data) // will be printed { "data" : "value" }
         *    }
         * )
         * ```
         *
         */
        fun JsonObject.toResponseData(): JsonObject {
            return this[RESPONSE_DATA_KEY]!!.jsonObject
        }

        /**
         * Method used to get directly the response data from the request response and format as [JsonArray]
         *
         * @return the [RESPONSE_DATA_KEY] value as [JsonArray]
         *
         * ### Example
         * - The complete response
         *
         * ```json
         * {
         *   "response": [
         *      "#1",
         *      "#2",
         *   ]
         *   "status": "SUCCESSFUL"
         * }
         * ```
         *
         * - Use the [toResponseArrayData] Method:
         *
         * ```kotlin
         * requester.sendRequest(
         *     request = {
         *         // make a request
         *     },
         *     onResponse = { response ->
         *        val data: JsonArray = response.toResponseArrayData()
         *        println(data) // will be printed ["#1", "#2"]
         *    }
         * )
         * ```
         *
         */
        fun JsonObject.toResponseArrayData(): JsonArray {
            return this[RESPONSE_DATA_KEY]!!.jsonArray
        }

        /**
         * Method used to get directly the response data from the request response and format as [String]
         *
         * @return the [RESPONSE_DATA_KEY] value as [String]
         *
         * ### Example
         * - The complete response
         *
         * ```json
         * {
         *   "response": "Hello World!"
         *   "status": "SUCCESSFUL"
         * }
         * ```
         *
         * - Use the [toResponseContent] Method:
         *
         * ```kotlin
         * requester.sendRequest(
         *     request = {
         *         // make a request
         *     },
         *     onResponse = { response ->
         *        val data: JsonArray = response.toResponseContent()
         *        println(data) // will be printed Hello World!
         *    }
         * )
         * ```
         *
         */
        fun JsonObject.toResponseContent(): String {
            return this[RESPONSE_DATA_KEY]!!.jsonPrimitive.content
        }

    }

    /**
     * `mustValidateCertificates** flag whether the requests must validate the **SSL** certificates, this for example
     * when the SSL is a self-signed certificate
     */
    protected var mustValidateCertificates: Boolean = false

    /**
     * `interceptorAction` The callback of the interceptor to execute when a request has been sent, if not specified is
     * `null** by default and no interceptions will be executed
     */
    protected var interceptorAction: (() -> Unit)? = null

    /**
     * `ktorClient` the HTTP client used to make the requests.
     *
     * To customize you can do as follows
     *
     * ```kotlin
     * init { // the init block of your own Requester
     *     ktorClient = HttpClient() // customize as you need
     * }
     * ```
     */
    protected var ktorClient = obtainHttpEngine(
        requestTimeout = requestTimeout,
        connectionTimeout = connectionTimeout,
        socketTimeout = socketTimeout,
        byPassSSLValidation = byPassSSLValidation
    )

    /**
     * `loggerMutex` the mutex used to log atomically the log messages if [debugMode] is `true`
     */
    private val loggerMutex = Mutex(
        locked = false
    )

    /**
     * `initHost** Method used to init correctly the [host] value
     */
    private val initHost by lazy {
        {
            changeHost(host)
        }
    }

    init {
        initHost()
        setUserCredentials(userId, userToken)
    }

    /**
     * Method used to execute a [RequestMethod.GET] request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param query The query parameters of the request
     *
     * @return the result of the request as [JsonObject]
     */
    @Wrapper
    protected suspend fun execGet(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
    ): JsonObject {
        return execRequest(
            method = RequestMethod.GET,
            endpoint = endpoint,
            headers = headers,
            query = query
        )
    }

    /**
     * Method used to execute a [RequestMethod.POST] request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    @Wrapper
    protected suspend fun execPost(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: JsonObject = JsonObject(emptyMap()),
    ): JsonObject {
        return execRequest(
            method = RequestMethod.POST,
            endpoint = endpoint,
            headers = headers,
            query = query,
            payload = payload
        )
    }

    /**
     * Method used to execute a [RequestMethod.PUT] request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    @Wrapper
    protected suspend fun execPut(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: JsonObject = JsonObject(emptyMap()),
    ): JsonObject {
        return execRequest(
            method = RequestMethod.PUT,
            endpoint = endpoint,
            headers = headers,
            query = query,
            payload = payload
        )
    }

    /**
     * Method used to execute a [RequestMethod.PATCH] request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    @Wrapper
    protected suspend fun execPatch(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: JsonObject = JsonObject(emptyMap()),
    ): JsonObject {
        return execRequest(
            method = RequestMethod.PATCH,
            endpoint = endpoint,
            headers = headers,
            query = query,
            payload = payload
        )
    }

    /**
     * Method used to execute a [RequestMethod.DELETE] request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    @Wrapper
    protected suspend fun execDelete(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: JsonObject? = null,
    ): JsonObject {
        return execRequest(
            method = RequestMethod.DELETE,
            endpoint = endpoint,
            headers = headers,
            query = query,
            payload = payload
        )
    }

    /**
     * Method used to create the query with the pagination parameters
     *
     * @param page The number of the page to request to the backend
     * @param pageSize The size of the result for the page
     *
     * @return the paginated query as [JsonObject]
     */
    @Assembler
    protected fun createPaginationQuery(
        page: Int,
        pageSize: Int,
    ): JsonObject {
        return buildJsonObject {
            put(PAGE_KEY, page)
            put(PAGE_SIZE_KEY, pageSize)
        }
    }

    /**
     * Method used to execute a request to the backend
     *
     * @param method The method of the request
     * @param endpoint The endpoint path of the request url
     * @param headers Custom headers of the request
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    private suspend fun execRequest(
        method: RequestMethod,
        endpoint: String,
        headers: Map<String, Any>,
        query: JsonObject? = null,
        payload: JsonObject? = null,
    ): JsonObject {
        val requestUrl = host + endpoint
        val jResponse: JsonObject = try {
            val response = ktorClient.request(
                urlString = host + endpoint
            ) {
                this.method = HttpMethod(method.name)
                prepareRequest(
                    headers = headers,
                    contentType = ContentType.parse("application/json"),
                    query = query,
                    payload = if (payload != null) {
                        {
                            setBody(payload.toString())
                        }
                    } else
                        null
                )
            }
            interceptRequest()
            Json.decodeFromString<JsonObject>(response.bodyAsText())
        } catch (exception: Exception) {
            if (debugMode) {
                logError(
                    exception = exception
                )
            }
            connectionErrorMessage()
        }
        if (debugMode) {
            logRequestInfo(
                requestUrl = requestUrl,
                headers = headers,
                query = query,
                requestPayloadInfo = {
                    payload?.let {
                        println("\n-PAYLOAD")
                        payload.prettyPrint()
                    }
                },
                response = jResponse
            )
        }
        return jResponse
    }

    /**
     * Method used to execute a multipart request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param headers Custom headers of the request
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    protected suspend fun execMultipartRequest(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: PartData,
    ): JsonObject {
        return execMultipartRequest(
            endpoint = endpoint,
            headers = headers,
            query = query,
            payload = listOf(payload)
        )
    }

    /**
     * Method used to execute a multipart request to the backend
     *
     * @param endpoint The endpoint path of the request url
     * @param headers Custom headers of the request
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    protected suspend fun execMultipartRequest(
        endpoint: String,
        headers: Map<String, Any> = emptyMap(),
        query: JsonObject? = null,
        payload: List<PartData>,
    ): JsonObject {
        val requestUrl = host + endpoint
        val jResponse = try {
            val response: HttpResponse = ktorClient.submitFormWithBinaryData(
                url = requestUrl,
                formData = payload
            ) {
                prepareRequest(
                    headers = headers,
                    query = query
                )
            }
            interceptRequest()
            Json.decodeFromString<JsonObject>(response.bodyAsText())
        } catch (e: Exception) {
            if (debugMode) {
                logError(
                    exception = e
                )
            }
            connectionErrorMessage()
        }
        if (debugMode) {
            logRequestInfo(
                requestUrl = requestUrl,
                headers = headers,
                query = query,
                requestPayloadInfo = {
                    println("\n-PAYLOAD")
                    payload.forEachIndexed { index, part ->
                        println("---------------------- $index ----------------------------")
                        print("| " + part.headers)
                        println("| Content-Type: ${part.contentType}")
                        println("| Name: ${part.name}")
                        if (index == payload.lastIndex)
                            println("-----------------------------------------------------")
                    }
                },
                response = jResponse
            )
        }
        return jResponse
    }

    /**
     * Method used to prepare the details of the request to execute
     *
     * @param headers Custom headers of the request
     * @param contentType The content type of the request
     * @param query The query parameters of the request
     * @param payload The payload of the request
     *
     */
    private fun HttpRequestBuilder.prepareRequest(
        headers: Map<String, Any>,
        contentType: ContentType? = null,
        query: JsonObject? = null,
        payload: (() -> Unit)? = null,
    ) {
        url {
            headers {
                userToken?.let { token ->
                    append(TOKEN_KEY, token)
                }
                headers.forEach { header ->
                    append(header.key, header.value.toString())
                }
                contentType?.let {
                    contentType(contentType)
                }
            }
            parameters {
                query?.entries?.forEach { parameter ->
                    val value = parameter.value
                    when (value) {
                        is JsonArray -> {
                            val parametersList = StringBuilder()
                            val rawParameters = value.jsonArray
                            rawParameters.forEachIndexed { index, element ->
                                parametersList.append(element.treatsAsString())
                                if (index != rawParameters.lastIndex)
                                    parametersList.append(",")
                            }
                            parameter(parameter.key, parametersList)
                        }

                        else -> {
                            parameter(parameter.key, value.treatsAsString())
                        }
                    }
                }
            }
            payload?.invoke()
        }
    }

    /**
     * Method used to print the details of the request sent if the [debugMode] is enabled
     *
     * @param requestUrl The url of the request
     * @param headers Custom headers of the request
     * @param query The query parameters of the request
     * @param requestPayloadInfo The payload of the request if sent with the request
     * @param response The response of the request sent
     */
    private suspend fun logRequestInfo(
        requestUrl: String,
        headers: Map<String, Any>,
        query: JsonObject?,
        requestPayloadInfo: () -> Unit,
        response: JsonObject?,
    ) {
        loggerMutex.withLock {
            println("----------- REQUEST ${TimeFormatter.formatNowAsString()} -----------")
            logHeaders(
                headers = headers
            )
            println("-URL\n$requestUrl")
            logQuery(
                query = query
            )
            requestPayloadInfo()
            response?.let {
                println("\n-RESPONSE")
                response.prettyPrint()
            }
            println("---------------------------------------------------")
        }
    }

    /**
     * Method used to log the current headers used in the requests
     */
    private fun logHeaders(
        headers: Map<String, Any>,
    ) {
        if (headers.isNotEmpty()) {
            println("\n-HEADERS")
            userToken?.let {
                println("$TOKEN_KEY: $userToken")
            }
            headers.forEach { header ->
                println(header.key + ": " + header.value)
            }
        }
    }

    /**
     * Method used to log the current query used in the request
     */
    private fun logQuery(
        query: JsonObject?,
    ) {
        query?.let {
            println("\n-QUERY")
            query.prettyPrint()
        }
    }

    /**
     * Method used to print a [JsonObject] request part in pretty format
     */
    private fun JsonObject.prettyPrint() {
        val json = Json {
            prettyPrint = true
        }
        println(json.encodeToString(JsonObject.serializer(), this))
    }

    /**
     * Method used to print a log of an exception occurred during a request sent if the [debugMode] is enabled
     *
     * @param exception The exception occurred
     */
    private fun logError(
        exception: Exception
    ) {
        exception.printStackTrace()
    }

    /**
     * Method used to set the [RESPONSE_STATUS_KEY] to send when an error during the connection occurred
     *
     * @return the error message as [JsonObject]
     */
    @Assembler
    protected fun connectionErrorMessage(): JsonObject {
        return buildJsonObject {
            put(RESPONSE_STATUS_KEY, GENERIC_RESPONSE.name)
            put(RESPONSE_DATA_KEY, connectionErrorMessage)
        }
    }

    /**
     * Method used to set the user credentials used to make the authenticated requests
     *
     * @param userId The user identifier to use
     * @param userToken The user token to use
     */
    fun setUserCredentials(
        userId: String?,
        userToken: String?
    ) {
        this.userId = userId
        this.userToken = userToken
    }

    /**
     * Method used to clear the current session of the requester, for example, after user logged out or
     * changed the auth credentials
     */
    @RequiresSuperCall
    open fun clearSession() {
        setUserCredentials(
            userId = null,
            userToken = null
        )
    }

    /**
     * Method used to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host The new host address to use
     */
    open fun changeHost(
        host: String
    ) {
        this.host = host
        if (byPassSSLValidation)
            mustValidateCertificates = host.startsWith("https")
    }

    /**
     * Method used to attach a new interceptor to the [Requester] to execute it when a request has been sent
     *
     * @param interceptor The interceptor action to attach
     */
    fun attachInterceptorOnRequest(
        interceptor: () -> Unit
    ) {
        this.interceptorAction = interceptor
    }

    /**
     * Method used to execute the [interceptorAction] if it is specified by the [attachInterceptorOnRequest] method
     */
    protected fun interceptRequest() {
        interceptorAction?.invoke()
    }

}