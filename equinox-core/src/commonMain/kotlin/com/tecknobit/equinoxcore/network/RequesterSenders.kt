package com.tecknobit.equinoxcore.network

import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.network.Requester.Companion.RESPONSE_STATUS_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.equinoxcore.network.ResponseStatus.*
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Method used to execute and handle the response of the request
 *
 * @param request The request to execute
 * @param onResponse The callback to execute when a response is returned from the backend
 * @param onConnectionError The callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester> R.sendRequest(
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
 * Method used to execute and handle the response of the request
 *
 * @param request The request to execute
 * @param onSuccess The callback to execute if the request has been successful
 * @param onFailure The callback to execute if the request has been failed
 * @param onConnectionError The callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester> R.sendRequest(
    request: suspend R.() -> JsonObject,
    onSuccess: (JsonObject) -> Unit,
    onFailure: (JsonObject) -> Unit,
    onConnectionError: ((JsonObject) -> Unit)? = null,
) {
    val response = request.invoke(this)
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

/**
 * Method used to execute and handle the response of the request
 *
 * @param request The request to execute
 * @param onResponse The asynchronous callback to execute when a response is returned from the backend
 * @param onConnectionError The asynchronous callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester> R.sendRequestAsyncHandlers(
    request: suspend R.() -> JsonObject,
    onResponse: suspend (JsonObject) -> Unit,
    onConnectionError: (suspend (JsonObject) -> Unit)? = null,
) {
    return sendRequestAsyncHandlers(
        request = request,
        onSuccess = onResponse,
        onFailure = onResponse,
        onConnectionError = onConnectionError
    )
}

/**
 * Method used to execute and handle the response of the request
 *
 * @param request The request to execute
 * @param onSuccess The asynchronous callback to execute if the request has been successful
 * @param onFailure The asynchronous callback to execute if the request has been failed
 * @param onConnectionError The asynchronous callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester> R.sendRequestAsyncHandlers(
    request: suspend R.() -> JsonObject,
    onSuccess: suspend (JsonObject) -> Unit,
    onFailure: suspend (JsonObject) -> Unit,
    onConnectionError: (suspend (JsonObject) -> Unit)? = null,
) {
    val response = request.invoke(this)
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

/**
 * Method used to get whether the request has been successful or not
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
 * Method used to execute and handle the paginated response of the request
 *
 * @param request The request to execute
 * @param serializer The serializer of the item in the page
 * @param onSuccess The callback to execute if the request has been successful
 * @param onFailure The callback to execute if the request has been failed
 * @param onConnectionError The callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester, T> R.sendPaginatedRequest(
    request: suspend R.() -> JsonObject,
    serializer: KSerializer<T>,
    onSuccess: (PaginatedResponse<T>) -> Unit,
    onFailure: (JsonObject) -> Unit,
    onConnectionError: ((JsonObject) -> Unit)? = null,
) {
    sendRequest(
        request = { request.invoke(this) },
        onSuccess = { jPage ->
            val page = preparePage(
                jPage = jPage,
                serializer = serializer
            )
            onSuccess.invoke(page)
        },
        onFailure = onFailure,
        onConnectionError = onConnectionError
    )
}

/**
 * Method used to execute and handle the paginated response of the request
 *
 * @param request The request to execute
 * @param serializer The serializer of the item in the page
 * @param onSuccess The asynchronous callback to execute if the request has been successful
 * @param onFailure The asynchronous callback to execute if the request has been failed
 * @param onConnectionError The asynchronous callback to execute if the request has been failed for a connection error
 */
suspend fun <R : Requester, T> R.sendPaginatedRequestAsyncHandlers(
    request: suspend R.() -> JsonObject,
    serializer: KSerializer<T>,
    onSuccess: suspend (PaginatedResponse<T>) -> Unit,
    onFailure: suspend (JsonObject) -> Unit,
    onConnectionError: (suspend (JsonObject) -> Unit)? = null,
) {
    sendRequestAsyncHandlers(
        request = { request.invoke(this) },
        onSuccess = { jPage ->
            val page = preparePage(
                jPage = jPage,
                serializer = serializer
            )
            onSuccess.invoke(page)
        },
        onFailure = onFailure,
        onConnectionError = onConnectionError
    )
}

/**
 * Method used to prepare and to format the raw [JsonObject] obtained with the [sendPaginatedRequest] or
 * [sendPaginatedRequestAsyncHandlers] methods in a [PaginatedResponse]
 *
 * @param jPage The response from the request to format
 * @param serializer The serializer of the item in the page
 *
 * @return the prepared page as [PaginatedResponse] of [T]
 *
 * @param T The type of the items in the [PaginatedResponse]
 */
@Assembler
private fun <T> preparePage(
    jPage: JsonObject,
    serializer: KSerializer<T>,
): PaginatedResponse<T> {
    val pageSerializer = PaginatedResponse.serializer(serializer)
    val json = Json {
        ignoreUnknownKeys = true
    }
    return json.decodeFromJsonElement(
        deserializer = pageSerializer,
        element = jPage.toResponseData()
    )
}