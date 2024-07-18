package com.tecknobit.equinox.environment.helpers

import com.tecknobit.apimanager.annotations.RequestPath
import com.tecknobit.apimanager.apis.APIRequest.Params
import com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*
import com.tecknobit.apimanager.apis.ServerProtector.SERVER_SECRET_KEY
import com.tecknobit.equinox.Requester
import com.tecknobit.equinox.environment.helpers.EquinoxBaseEndpointsSet.*
import com.tecknobit.equinox.environment.records.EquinoxUser.*
import com.tecknobit.equinox.inputs.InputValidator.DEFAULT_LANGUAGE
import com.tecknobit.equinox.inputs.InputValidator.isLanguageValid
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

/**
 * The **EquinoxRequester** class is useful to communicate with backend based on the **SpringBoot** framework with the
 * [EquinoxUser] requests pre-implemented
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
 *
 * @since 1.0.1
 */
abstract class EquinoxRequester(
    host: String,
    userId: String? = null,
    userToken: String? = null,
    debugMode: Boolean = false,
    connectionTimeout: Int,
    connectionErrorMessage: String,
    enableCertificatesValidation: Boolean = false
) : Requester(
    host = host,
    userId = userId,
    userToken = userToken,
    debugMode = debugMode,
    connectionTimeout = connectionTimeout,
    connectionErrorMessage = connectionErrorMessage,
    enableCertificatesValidation = enableCertificatesValidation
) {

    /**
     * Function to change, during the runtime for example when the session changed, the host address to make the
     * requests
     *
     * @param host: the new host address to use
     */
    override fun changeHost(host: String) {
        super.changeHost("$host$BASE_EQUINOX_ENDPOINT")
    }

    /**
     * Function to execute the request to sign up in the Neutron's system
     *
     * @param serverSecret: the secret of the personal Neutron's backend
     * @param name: the name of the user
     * @param surname: the surname of the user
     * @param email: the email of the user
     * @param password: the password of the user
     * @param language: the language of the user
     *
     * @return the result of the request as [JSONObject]
     *
     */
    @RequestPath(path = "/api/v1/users/signUp", method = POST)
    open fun signUp(
        serverSecret: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String
    ): JSONObject {
        val payload = Params()
        payload.addParam(SERVER_SECRET_KEY, serverSecret)
        payload.addParam(NAME_KEY, name)
        payload.addParam(SURNAME_KEY, surname)
        payload.addParam(EMAIL_KEY, email)
        payload.addParam(PASSWORD_KEY, password)
        payload.addParam(
            LANGUAGE_KEY,
            if (!isLanguageValid(language))
                DEFAULT_LANGUAGE
            else
                language
        )
        return execPost(
            endpoint = SIGN_UP_ENDPOINT,
            payload = payload
        )
    }

    /**
     * Function to execute the request to sign in the Neutron's system
     *
     * @param email: the email of the user
     * @param password: the password of the user
     *
     * @return the result of the request as [JSONObject]
     *
     */
    @RequestPath(path = "/api/v1/users/signIn", method = POST)
    open fun signIn(
        email: String,
        password: String
    ): JSONObject {
        val payload = Params()
        payload.addParam(EMAIL_KEY, email)
        payload.addParam(PASSWORD_KEY, password)
        return execPost(
            endpoint = SIGN_IN_ENDPOINT,
            payload = payload
        )
    }

    /**
     * Function to execute the request to change the profile pic of the user
     *
     * @param profilePic: the profile pic chosen by the user to set as the new profile pic
     *
     * @return the result of the request as [JSONObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changeProfilePic", method = POST)
    open fun changeProfilePic(profilePic: File): JSONObject {
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                PROFILE_PIC_KEY,
                profilePic.name,
                profilePic.readBytes().toRequestBody("*/*".toMediaType())
            )
            .build()
        return execMultipartRequest(
            endpoint = assembleUsersEndpointPath(CHANGE_PROFILE_PIC_ENDPOINT),
            body = body
        )
    }

    /**
     * Function to execute the request to change the email of the user
     *
     * @param newEmail: the new email of the user
     *
     * @return the result of the request as [JSONObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changeEmail", method = PATCH)
    open fun changeEmail(
        newEmail: String
    ): JSONObject {
        val payload = Params()
        payload.addParam(EMAIL_KEY, newEmail)
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_EMAIL_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Function to execute the request to change the password of the user
     *
     * @param newPassword: the new password of the user
     *
     * @return the result of the request as [JSONObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changePassword", method = PATCH)
    open fun changePassword(
        newPassword: String
    ): JSONObject {
        val payload = Params()
        payload.addParam(PASSWORD_KEY, newPassword)
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_PASSWORD_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Function to execute the request to change the language of the user
     *
     * @param newLanguage: the new language of the user
     *
     * @return the result of the request as [JSONObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/changeLanguage", method = PATCH)
    open fun changeLanguage(
        newLanguage: String
    ): JSONObject {
        val payload = Params()
        payload.addParam(LANGUAGE_KEY, newLanguage)
        return execPatch(
            endpoint = assembleUsersEndpointPath(CHANGE_LANGUAGE_ENDPOINT),
            payload = payload
        )
    }

    /**
     * Function to execute the request to delete the account of the user
     *
     * No-any params required
     *
     * @return the result of the request as [JSONObject]
     */
    @RequestPath(path = "/api/v1/users/{id}", method = DELETE)
    open fun deleteAccount(): JSONObject {
        return execDelete(
            endpoint = assembleUsersEndpointPath()
        )
    }

    /**
     * Function to assemble the endpoint to make the request to the users controller
     *
     * @param endpoint: the endpoint path of the url
     *
     * @return an endpoint to make the request as [String]
     */
    protected fun assembleUsersEndpointPath(
        endpoint: String = ""
    ): String {
        return "$USERS_KEY/$userId$endpoint"
    }

}