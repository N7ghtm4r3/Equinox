package com.tecknobit.equinox

import com.tecknobit.apimanager.annotations.Wrapper
import com.tecknobit.apimanager.apis.APIRequest
import com.tecknobit.apimanager.apis.APIRequest.*
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.*
import com.tecknobit.apimanager.formatters.JsonHelper
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.IOException

/**
 * The **Requester** class is useful to communicate with the Nova's backend
 *
 * @param host: the host where is running the Nova's backend
 * @param userId: the user identifier
 * @param userToken: the user token
 *
 * @author N7ghtm4r3 - Tecknobit
 */
abstract class Requester (
    var host: String,
    var userId: String? = null,
    var userToken: String? = null,
    private val connectionErrorMessage: String,
    private val enableCertificatesValidation: Boolean = false
) {

    companion object {

        const val USER_IDENTIFIER_KEY = "id"

        const val USER_TOKEN_KEY = "token"

        /**
         * **RESPONSE_STATUS_KEY** the key for the <b>"status"</b> field
         */
        const val RESPONSE_STATUS_KEY: String = "status"

        /**
         * **RESPONSE_MESSAGE_KEY** the key for the <b>"response"</b> field
         */
        const val RESPONSE_MESSAGE_KEY: String = "response"

    }

    /**
     * **apiRequest** -> the instance to communicate and make the requests to the backend
     */
    private val apiRequest = APIRequest(5000)

    /**
     * **headers** the headers used in the request
     */
    protected val headers = Headers()

    /**
     * **mustValidateCertificates** flag whether the requests must validate the SSL certificates, this need for example
     * when the SSL is a self-signed certificate
     */
    protected var mustValidateCertificates: Boolean = false

    init {
        changeHost(host)
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
     * Function to change during the runtime, for example when the local session changed, the host address to make the
     * requests
     *
     * @param host: the new host address to use
     */
    fun changeHost(
        host: String
    ) {
        this.host = host
        if(enableCertificatesValidation)
            mustValidateCertificates = host.startsWith("https")
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
        payload: Params
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
        payload: Params
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
        payload: Params
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
        runBlocking {
            try {
                async {
                    val requestUrl = host + endpoint
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
                    } catch (e: IOException) {
                        response = connectionErrorMessage().toString()
                    }
                }.await()
                jResponse = JSONObject(response)
            } catch (e: Exception) {
                jResponse = connectionErrorMessage()
            }
        }
        return jResponse
    }

    /**
     * Function to set the [RESPONSE_STATUS_KEY] to send when an error during the connection occurred
     *
     * No-any params required
     *
     * @return the error message as [JSONObject]
     */
    private fun connectionErrorMessage(): JSONObject {
        return JSONObject()
            .put(RESPONSE_STATUS_KEY, GENERIC_RESPONSE.name)
            .put(RESPONSE_MESSAGE_KEY, connectionErrorMessage)
    }

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