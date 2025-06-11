package com.tecknobit.equinoxcompose.network

import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.RequestPath
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isLanguageValid
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.BASE_EQUINOX_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_EMAIL_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_LANGUAGE_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_PASSWORD_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_PROFILE_PIC_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.DYNAMIC_ACCOUNT_DATA_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.SIGN_IN_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.SIGN_UP_ENDPOINT
import com.tecknobit.equinoxcore.network.RequestMethod.*
import com.tecknobit.equinoxcore.network.Requester
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * The **EquinoxRequester** class is useful to communicate with backend based on the **SpringBoot** framework with the
 * requests pre-implemented
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
 *
 * @since 1.0.6
 */
abstract class EquinoxRequester(
    host: String,
    userId: String? = null,
    userToken: String? = null,
    debugMode: Boolean = false,
    requestTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    connectionTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    socketTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    connectionErrorMessage: String,
    byPassSSLValidation: Boolean = false,
) : Requester(
    host = host,
    userId = userId,
    userToken = userToken,
    debugMode = debugMode,
    requestTimeout = requestTimeout,
    connectionTimeout = connectionTimeout,
    socketTimeout = socketTimeout,
    connectionErrorMessage = connectionErrorMessage,
    byPassSSLValidation = byPassSSLValidation
) {

    /**
     * Method used to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host The new host address to use
     */
    override fun changeHost(host: String) {
        super.changeHost("$host$BASE_EQUINOX_ENDPOINT")
    }

    /**
     * Method used to request the to sign up in the Equinox's system
     *
     * @param serverSecret The secret of the personal Equinox's backend
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-up
     *
     * @return the result of the request as [JsonObject]
     *
     */
    @RequestPath(path = "/api/v1/users/signUp", method = POST)
    suspend fun signUp(
        serverSecret: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = getSignUpPayload(
            serverSecret = serverSecret,
            name = name,
            surname = surname,
            email = email,
            password = password,
            language = language,
            custom = custom
        )
        return execPost(
            endpoint = SIGN_UP_ENDPOINT,
            payload = payload
        )
    }

    /**
     * Method used to create the payload for the [signUp] request.
     *
     * #### Usage example:
     *
     * ```kotlin
     * @CustomParametersOrder(order = ["currency"]) // optional
     * override fun getSignUpPayload(
     *         serverSecret: String,
     *         name: String,
     *         surname: String,
     *         email: String,
     *         password: String,
     *         language: String,
     *         vararg custom: Any?
     *     ): JsonObject {
     *         val payload = super.getSignUpPayload(serverSecret, name, surname, email, password, language, *custom).toMutableMap()
     *         payload["currency"] = Json.encodeToJsonElement(custom[0]!!.toString())
     *         return Json.encodeToJsonElement(payload).jsonObject
     *     }
     * ```
     *
     * @param serverSecret The secret of the personal Equinox's backend
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-up
     *
     * @return the payload for the request as [JsonObject]
     *
     */
    protected open fun getSignUpPayload(
        serverSecret: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        vararg custom: Any?,
    ): JsonObject {
        return buildJsonObject {
            put(SERVER_SECRET_KEY, serverSecret)
            put(NAME_KEY, name)
            put(SURNAME_KEY, surname)
            put(EMAIL_KEY, email)
            put(PASSWORD_KEY, password)
            put(
                key = LANGUAGE_KEY,
                value = if (!isLanguageValid(language))
                    DEFAULT_LANGUAGE
                else
                    language
            )
        }
    }

    /**
     * Method used to request the to sign in the Equinox's system
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-in
     *
     * @return the result of the request as [JsonObject]
     *
     */
    @RequestPath(path = "/api/v1/users/signIn", method = POST)
    open suspend fun signIn(
        email: String,
        password: String,
        language: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = getSignInPayload(
            email = email,
            password = password,
            language = language,
            custom = custom
        )
        return execPost(
            endpoint = SIGN_IN_ENDPOINT,
            payload = payload
        )
    }

    /**
     * Method used to create the payload for the [signIn] request.
     *
     * #### Usage example:
     *
     * ```kotlin
     * @CustomParametersOrder(order = ["currency"])
     * override fun getSignInPayload(
     *      email: String,
     *      password: String,
     *      vararg custom: Any?
     * ): JsonObject {
     *    val payload = super.getSignInPayload(email, password, custom).toMutableMap()
     *    payload["currency"] = Json.encodeToJsonElement(custom[0]!!.toString())
     *    return Json.encodeToJsonElement(payload).jsonObject
     * }
     * ```
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-in
     *
     * @return the payload for the request as [JsonObject]
     *
     */
    @Assembler
    protected open fun getSignInPayload(
        email: String,
        password: String,
        language: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = buildJsonObject {
            put(EMAIL_KEY, email)
            put(PASSWORD_KEY, password)
            put(
                key = LANGUAGE_KEY,
                value = if (!isLanguageValid(language))
                    DEFAULT_LANGUAGE
                else
                    language
            )
        }
        return payload
    }

    /**
     * Method used to request the dynamic data of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}/dynamicAccountData", method = GET)
    suspend fun getDynamicAccountData(): JsonObject {
        return execGet(
            endpoint = assembleUsersEndpointPath(DYNAMIC_ACCOUNT_DATA_ENDPOINT)
        )
    }

    /**
     * Method used to request the to change the profile pic of the user
     *
     * @param profilePicName The name of the profile pic
     * @param profilePicBytes The profile pic chosen by the user to set as the new profile pic
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}/changeProfilePic", method = POST)
    open suspend fun changeProfilePic(
        profilePicName: String,
        profilePicBytes: ByteArray,
    ): JsonObject {
        val payload = formData {
            append(PROFILE_PIC_KEY, profilePicBytes, Headers.build {
                append(HttpHeaders.ContentType, "image/*")
                append(HttpHeaders.ContentDisposition, "filename=\"$profilePicName\"")
            })
        }
        return execMultipartRequest(
            endpoint = assembleUsersEndpointPath(CHANGE_PROFILE_PIC_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Method used to request the to change the email of the user
     *
     * @param newEmail The new email of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}/changeEmail", method = PATCH)
    open suspend fun changeEmail(
        newEmail: String,
    ): JsonObject {
        val payload = buildJsonObject {
            put(EMAIL_KEY, newEmail)
        }
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_EMAIL_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Method used to request the to change the password of the user
     *
     * @param newPassword The new password of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}/changePassword", method = PATCH)
    open suspend fun changePassword(
        newPassword: String,
    ): JsonObject {
        val payload = buildJsonObject {
            put(PASSWORD_KEY, newPassword)
        }
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_PASSWORD_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Method used to request the to change the language of the user
     *
     * @param newLanguage The new language of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}/changeLanguage", method = PATCH)
    open suspend fun changeLanguage(
        newLanguage: String,
    ): JsonObject {
        val payload = buildJsonObject {
            put(LANGUAGE_KEY, newLanguage)
        }
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_LANGUAGE_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Method used to request the to delete the account of the user
     *
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{user_id}", method = DELETE)
    open suspend fun deleteAccount(): JsonObject {
        return execDelete(
            endpoint = assembleUsersEndpointPath()
        )
    }

    /**
     * Method used to assemble the endpoint to make the request to the custom controllers
     *
     * @param customEndpoint The custom endpoint of the request, the main part of the complete url
     * @param subEndpoint The sub-endpoint path of the url
     *
     * @return an endpoint to make the request as [String]
     */
    @Assembler
    protected fun assembleCustomEndpointPath(
        customEndpoint: String,
        subEndpoint: String = "",
    ): String {
        val subPath = if (subEndpoint.isNotBlank())
            "/$subEndpoint"
        else
            subEndpoint
        val requestUrl = "$customEndpoint$subPath"
        return assembleUsersEndpointPath(
            endpoint = if (customEndpoint.startsWith("/"))
                requestUrl
            else
                "/$requestUrl"
        )
    }

    /**
     * Method used to assemble the endpoint to make the request to the users controller
     *
     * @param endpoint The endpoint path of the url
     *
     * @return an endpoint to make the request as [String]
     */
    @Assembler
    protected fun assembleUsersEndpointPath(
        endpoint: String = "",
    ): String {
        return "$USERS_KEY/$userId$endpoint"
    }

}