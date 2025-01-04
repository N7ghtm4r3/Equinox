package com.tecknobit.equinoxcore.network

import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.network.ResponseStatus.*
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.DATA_KEY
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.IS_LAST_PAGE_KEY
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_KEY
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_SIZE_KEY
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
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
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param connectionErrorMessage The error to send when a connection error occurred
 * @param enableCertificatesValidation: whether enable the **SSL** certificates validation, this for example
 * when the certificate is a self-signed certificate to by-pass
 *
 * @author N7ghtm4r3 - Tecknobit
 */
abstract class Requester(
    protected var host: String,
    protected var userId: String? = null,
    protected var userToken: String? = null,
    protected var debugMode: Boolean = false,
    protected val connectionTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    @JsName("connection_error_message")
    protected val connectionErrorMessage: String,
    protected val enableCertificatesValidation: Boolean = false,
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
         * **RESPONSE_DATA_KEY** the key for the <b>"response"</b> field
         */
        const val RESPONSE_DATA_KEY: String = "response"

        /**
         * **DEFAULT_REQUEST_TIMEOUT** the timeout values in millis used in the requests
         */
        const val DEFAULT_REQUEST_TIMEOUT = 5000L

        /**
         * **DEFAULT_CONNECTION_ERROR_MESSAGE** the message to send when an error during the communication with the
         * backend occurred
         */
        const val DEFAULT_CONNECTION_ERROR_MESSAGE = "connection_error_message_key"

        /**
         * Method to execute and manage the response of a request
         *
         * @param request The request to execute
         * @param onResponse The action to execute when a response is returned from the backend
         * @param onConnectionError The action to execute if the request has been failed for a connection error
         */
        fun <R : Requester> R.sendRequest(
            request: suspend R.() -> JsonObject,
            onResponse: (JsonObject) -> Unit,
            onConnectionError: ((JsonObject) -> Unit)? = null,
        ) {
            return sendRequest(
                request = request,
                onSuccess = onResponse,
                onFailure = onResponse,
                onConnectionError = onConnectionError
            )
        }

        /**
         * Method to execute and manage the response of a request
         *
         * @param request The request to execute
         * @param onSuccess The action to execute if the request has been successful
         * @param onFailure The action to execute if the request has been failed
         * @param onConnectionError The action to execute if the request has been failed for a connection error
         */
        fun <R : Requester> R.sendRequest(
            request: suspend R.() -> JsonObject,
            onSuccess: (JsonObject) -> Unit,
            onFailure: (JsonObject) -> Unit,
            onConnectionError: ((JsonObject) -> Unit)? = null,
        ) {
            MainScope().launch {
                val response = request.invoke(this@sendRequest)
                when (isSuccessfulResponse(response)) {
                    SUCCESSFUL -> onSuccess.invoke(response)
                    GENERIC_RESPONSE -> {
                        if (onConnectionError != null)
                            onConnectionError.invoke(response)
                        else
                            onFailure.invoke(response)
                    }

                    else -> onFailure.invoke(response)
                }
            }
        }

        /**
         * Method to execute and manage the paginated response of a request
         *
         * @param request The request to execute
         * @param onSuccess The action to execute if the request has been successful
         * @param onFailure The action to execute if the request has been failed
         * @param onConnectionError The action to execute if the request has been failed for a connection error
         */
        fun <R : Requester, T> R.sendPaginatedWRequest(
            request: suspend R.() -> JsonObject,
            serializer: KSerializer<T>,
            onSuccess: (PaginatedResponse<T>) -> Unit,
            onFailure: (JsonObject) -> Unit,
            onConnectionError: ((JsonObject) -> Unit)? = null,
        ) {
            sendRequest(
                request = { request.invoke(this) },
                onSuccess = { jPage ->
                    // TODO: USING DIRECTLY THE SERIALIZATION
                    val data = jPage[RESPONSE_DATA_KEY]!!.jsonObject
                    val page = PaginatedResponse(
                        data = data[DATA_KEY]!!.jsonArray.map {
                            Json.decodeFromJsonElement(serializer, it)
                        },
                        page = data[PAGE_KEY]!!.jsonPrimitive.int,
                        pageSize = data[PAGE_SIZE_KEY]!!.jsonPrimitive.int,
                        isLastPage = data[IS_LAST_PAGE_KEY]!!.jsonPrimitive.boolean
                    )
                    onSuccess.invoke(page)
                },
                onFailure = onFailure,
                onConnectionError = onConnectionError
            )
        }

        /**
         * Method to get whether the request has been successful or not
         *
         * @param response The response of the request
         *
         * @return whether the request has been successful or not as [ResponseStatus]
         */
        private fun isSuccessfulResponse(
            response: JsonObject?,
        ): ResponseStatus {
            if (response == null || !response.containsKey(RESPONSE_STATUS_KEY))
                return FAILED
            return when (response.jsonObject[RESPONSE_STATUS_KEY]!!.jsonPrimitive.content) {
                SUCCESSFUL.name -> SUCCESSFUL
                GENERIC_RESPONSE.name -> GENERIC_RESPONSE
                else -> FAILED
            }
        }

        /**
         * Extension Method to get directly the response data from the request response
         *
         * No-any params required
         *
         * @return the [RESPONSE_DATA_KEY] value as [String]
         *
         * ### Example
         * - The complete response
         *
         * ```json
         * {
         *   "response": "Response data", // in the example is String, but with any types is the same workflow
         *   "status": "SUCCESSFUL"
         * }
         * ```
         *
         * - Use the [responseData] Method:
         *
         * ```kotlin
         * requester.sendRequest(
         *     request = {
         *         // make a request
         *     },
         *     onResponse = { response ->
         *        val data: Any = response.responseData()
         *        println(data) // will be printed Response data
         *    }
         * )
         * ```
         *
         */
        // TODO: TO DOCU
        fun JsonObject.toNullResponseData(): JsonObject? {
            val response = this[RESPONSE_DATA_KEY]
            return if (response is JsonNull)
                return null
            else
                response?.jsonObject
        }

        // TODO: TO DOCU
        fun JsonObject.toResponseData(): JsonObject {
            return this[RESPONSE_DATA_KEY]!!.jsonObject
        }

        // TODO: TO DOCU
        fun JsonObject.toResponseArrayData(): JsonArray {
            return this[RESPONSE_DATA_KEY]!!.jsonArray
        }

        // TODO: TO DOCU
        fun JsonObject.toResponseContent(): String {
            return this[RESPONSE_DATA_KEY]!!.jsonPrimitive.content
        }

    }

    /**
     * `timeFormatter` the formatter used to format the timestamp values
     */
    // TODO: TO REPLACE WITH THE REAL ONE
    protected val timeFormatter: TimeFormatter = TimeFormatter.getInstance()

    /**
     * **mustValidateCertificates** flag whether the requests must validate the **SSL** certificates, this for example
     * when the SSL is a self-signed certificate
     */
    protected var mustValidateCertificates: Boolean = false

    /**
     * **interceptorAction** the action of the interceptor to execute when a request has been sent, if not specified is
     * **null** by default and no interceptions will be executed
     */
    protected var interceptorAction: (() -> Unit)? = null

    /**
     * **ktorClient** -> the HTTP client used to send the stats and the performance data
     */
    protected val ktorClient = HttpClient()

    /**
     * **initHost** Method to init correctly the [host] value
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
     * Method to execute a [RequestMethod.GET] request to the backend
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
     * Method to execute a [RequestMethod.POST] request to the backend
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
     * Method to execute a [RequestMethod.PUT] request to the backend
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
     * Method to execute a [RequestMethod.PATCH] request to the backend
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
     * Method to execute a [RequestMethod.DELETE] request to the backend
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
     * Method to create the query with the pagination parameters
     *
     * @param page The number of the page to request to the backend
     * @param pageSize The size of the result for the page
     *
     * @return the paginated query as [JsonObject]
     */
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
     * Method to execute a request to the backend
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
        val response = ktorClient.request(
            urlString = host + endpoint
        ) {
            this.method = HttpMethod(method.name)
            url {
                parameters {
                    query?.entries?.forEach { parameter ->
                        parameter(parameter.key, parameter.value)
                    }
                }
                headers {
                    userToken?.let { token ->
                        append(USER_TOKEN_KEY, token)
                    }
                    headers.forEach { header ->
                        append(header.key, header.value.toString())
                    }
                }
                payload?.let { payload ->
                    setBody(payload.toString())
                }
            }
        }
        val jResponse = Json.encodeToJsonElement(response.bodyAsText()).jsonObject
        logRequestInfo(
            requestUrl = requestUrl,
            requestPayloadInfo = {
                payload?.let {
                    println("\n-PAYLOAD")
                    println(payload)
                }
            },
            response = jResponse
        )
        return jResponse


        /*var response: String? = null
        var jResponse: JsonObject
        if(mustValidateCertificates)
            apiRequest.validateSelfSignedCertificate()
        execRequest(
            response = response
        )
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
                        interceptRequest()
                    } catch (e: IOException) {
                        logError(
                            exception = e
                        )
                        response = connectionErrorMessage().toString()
                    }
                }.await()
                jResponse = JsonObject(response)
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
        return jResponse*/
    }

    /**
     * Method to exec a multipart body  request
     *
     * @param endpoint The endpoint path of the url
     * @param body The body payload of the request
     *
     * @return the result of the request as [JsonObject]
     */
    protected fun execMultipartRequest(
        endpoint: String,
        query: JsonObject? = null,
        body: MultipartBody,
    ): JsonObject {
        val mHeaders = mutableMapOf<String, String>()
        headers.headersKeys.forEach { headerKey ->
            mHeaders[headerKey] = headers.getHeader(headerKey)
        }
        var requestUrl = "$host$endpoint"
        query?.let {
            requestUrl += query.createQueryString()
        }
        val request: Request = Request.Builder()
            .headers(mHeaders.toHeaders())
            .url(requestUrl)
            .post(body)
            .build()
        val client = validateSelfSignedCertificate(OkHttpClient())
        var response: JsonObject? = null
        runBlocking {
            try {
                async {
                    response = try {
                        client.newCall(request).execute().body?.string()?.let { JsonObject(it) }
                    } catch (e: IOException) {
                        logError(
                            exception = e
                        )
                        JsonObject(connectionErrorMessage())
                    }
                    interceptRequest()
                }.await()
            } catch (e: Exception) {
                logError(
                    exception = e
                )
                response = JsonObject(connectionErrorMessage())
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
     * Method to print the details of the request sent if the [debugMode] is enabled
     *
     * @param requestUrl The url of the request
     * @param requestPayloadInfo The payload of the request if sent with the request
     * @param response The response of the request sent
     */
    private fun logRequestInfo(
        requestUrl: String,
        requestPayloadInfo: () -> Unit,
        response: JsonObject?,
    ) {
        if (debugMode) {
            synchronized(this) {
                println("----------- REQUEST ${timeFormatter.formatNowAsString()} -----------")
                logHeaders()
                println("-URL\n$requestUrl")
                requestPayloadInfo.invoke()
                if (response != null)
                    println("\n-RESPONSE\n$response")
                println("---------------------------------------------------")
            }
        }
    }

    /**
     * Method to log the current headers used in the requests
     *
     * No-any params required
     */
    private fun logHeaders() {
        val headerKeys = headers.headersKeys
        if (headerKeys.isNotEmpty()) {
            println("\n-HEADERS")
            val headers = JsonObject()
            headerKeys.forEach { key ->
                headers.put(key, this.headers.getHeader(key))
            }
            println(headers.toString(4) + "\n")
        }
    }

    /**
     * Method to print a log of an exception occurred during a request sent if the [debugMode] is enabled
     *
     * @param exception The exception occurred
     */
    private fun logError(
        exception: Exception
    ) {
        if (debugMode)
            exception.printStackTrace()
    }

    /**
     * Method to set the [RESPONSE_STATUS_KEY] to send when an error during the connection occurred
     *
     * No-any params required
     *
     * @return the error message as [JsonObject]
     */
    protected fun connectionErrorMessage(): JsonObject {
        return buildJsonObject {
            put(RESPONSE_STATUS_KEY, GENERIC_RESPONSE.name)
            put(RESPONSE_DATA_KEY, connectionErrorMessage)
        }
    }

    /**
     * Method to set the user credentials used to make the authenticated requests
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
        if (userToken != null)
            headers.addHeader(USER_TOKEN_KEY, userToken)
    }

    /**
     * Method to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host The new host address to use
     */
    open fun changeHost(
        host: String
    ) {
        this.host = host
        if (enableCertificatesValidation)
            mustValidateCertificates = host.startsWith("https")
    }

    /**
     * Method to set programmatically timeout for the requests
     *
     * @param connectionTimeout Timeout for the requests
     */
    fun setConnectionTimeout(
        connectionTimeout: Long
    ) {
        apiRequest.setConnectionTimeout(connectionTimeout)
    }

    /**
     * Method to attach a new interceptor to the [Requester] to execute it when a request has been sent
     *
     * @param interceptor The interceptor action to attach
     */
    fun attachInterceptorOnRequest(
        interceptor: () -> Unit
    ) {
        this.interceptorAction = interceptor
    }

    /**
     * Method to execute the [interceptorAction] if it is specified by the [attachInterceptorOnRequest] method
     */
    protected fun interceptRequest() {
        interceptorAction?.invoke()
    }

}