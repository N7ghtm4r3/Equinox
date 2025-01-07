package com.tecknobit.equinoxcompose.network

import com.tecknobit.equinoxcore.annotations.RequestPath
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isLanguageValid
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.BASE_EQUINOX_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_EMAIL_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_LANGUAGE_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_PASSWORD_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.CHANGE_PROFILE_PIC_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.SIGN_IN_ENDPOINT
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.SIGN_UP_ENDPOINT
import com.tecknobit.equinoxcore.network.RequestMethod.*
import com.tecknobit.equinoxcore.network.Requester
import io.ktor.client.request.forms.*
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
 * @param connectionTimeout Time to keep alive request then throw the connection refused error
 * @param connectionErrorMessage The error to send when a connection error occurred
 * @param enableCertificatesValidation Whether enable the **SSL** certificates validation, this for example
 * when the certificate is a self-signed certificate to by-pass
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
    connectionTimeout: Long = DEFAULT_REQUEST_TIMEOUT,
    connectionErrorMessage: String,
    enableCertificatesValidation: Boolean = false,
) : Requester(
    host = host,
    userId = userId,
    userToken = userToken,
    debugMode = debugMode,
    connectionTimeout = connectionTimeout,
    connectionErrorMessage = connectionErrorMessage,
    byPassSSLValidation = enableCertificatesValidation
) {

    /**
     * Method to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host The new host address to use
     */
    override fun changeHost(host: String) {
        super.changeHost("$host$BASE_EQUINOX_ENDPOINT")
    }

    /**
     * Method to execute the request to sign up in the Equinox's system
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
     * Method to create the payload for the [signUp] request.
     *
     * #### Usage example:
     *
     * ```
     * @CustomParametersOrder(order = ["currency"]) // optional
     * override fun getSignUpPayload(
     *      serverSecret: String,
     *      name: String,
     *      surname: String,
     *      email: String,
     *      password: String,
     *      language: String,
     *      vararg custom: Any
     * ): JsonObject {
     *      val payload = super.getSignUpPayload(serverSecret, name, surname, email, password, language)
     *      payload.addParam("currency", custom[0])
     *      return payload
     * }
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
     * Method to execute the request to sign in the Equinox's system
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-in
     *
     * @return the result of the request as [JsonObject]
     *
     */
    @RequestPath(path = "/api/v1/users/signIn", method = POST)
    open suspend fun signIn(
        email: String,
        password: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = getSignInPayload(
            email = email,
            password = password,
            custom = custom
        )
        return execPost(
            endpoint = SIGN_IN_ENDPOINT,
            payload = payload
        )
    }

    /**
     * Method to create the payload for the [signIn] request.
     *
     * #### Usage example:
     *
     * ```
     * @CustomParametersOrder(order = ["currency"])
     * override fun getSignInPayload(email: String, password: String, vararg custom: Any?): JsonObject {
     *   val payload = super.getSignInPayload(email, password)
     *   payload.addParam("currency", custom[0])
     *   return payload
     * }
     * ```
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-in
     *
     * @return the payload for the request as [JsonObject]
     *
     */
    protected open fun getSignInPayload(
        email: String,
        password: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = buildJsonObject {
            put(EMAIL_KEY, email)
            put(PASSWORD_KEY, password)
        }
        return payload
    }

    /**
     * Method to execute the request to change the profile pic of the user
     *
     * @param profilePic The profile pic chosen by the user to set as the new profile pic
     *
     * @return the result of the request as [JsonObject]
     */
    // TODO: CHANGE BYTE ARRAY TO REAL FILE FROM LIBRARY 
    @RequestPath(path = "/api/v1/users/{id}/changeProfilePic", method = POST)
    open suspend fun changeProfilePic(
        profilePic: ByteArray,
    ): JsonObject {
        val payload = formData {
            append(PROFILE_PIC_KEY, profilePic)
        }
        return execMultipartRequest(
            endpoint = assembleUsersEndpointPath(CHANGE_PROFILE_PIC_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Method to execute the request to change the email of the user
     *
     * @param newEmail The new email of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changeEmail", method = PATCH)
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
     * Method to execute the request to change the password of the user
     *
     * @param newPassword The new password of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changePassword", method = PATCH)
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
     * Method to execute the request to change the language of the user
     *
     * @param newLanguage The new language of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changeLanguage", method = PATCH)
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
     * Method to execute the request to delete the account of the user
     *
     * No-any params required
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}", method = DELETE)
    open suspend fun deleteAccount(): JsonObject {
        return execDelete(
            endpoint = assembleUsersEndpointPath()
        )
    }

    /**
     * Method to assemble the endpoint to make the request to the custom controllers
     *
     * @param customEndpoint The custom endpoint of the request, the main part of the complete url
     * @param subEndpoint The sub-endpoint path of the url
     *
     * @return an endpoint to make the request as [String]
     */
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
     * Method to assemble the endpoint to make the request to the users controller
     *
     * @param endpoint The endpoint path of the url
     *
     * @return an endpoint to make the request as [String]
     */
    protected fun assembleUsersEndpointPath(
        endpoint: String = "",
    ): String {
        return "$USERS_KEY/$userId$endpoint"
    }

}