package com.tecknobit.equinoxcore.network

/**
 * `ResponseStatus` list of available response status code
 *
 * @property code The code of the response
 */
enum class ResponseStatus(
    val code: Int,
) {

    /**
     * `SUCCESSFUL` when the communication ended successfully
     */
    SUCCESSFUL(200),

    /**
     * `GENERIC_RESPONSE` when the communication ended with a generic error
     */
    GENERIC_RESPONSE(300),

    /**
     * `NOT_FOUND` when the communication ended with a not found resource
     */
    NOT_FOUND(404),

    /**
     * `FAILED` when the communication failed
     */
    FAILED(500)

}