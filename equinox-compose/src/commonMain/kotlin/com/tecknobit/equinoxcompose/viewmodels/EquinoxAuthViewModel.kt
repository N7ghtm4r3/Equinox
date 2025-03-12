package com.tecknobit.equinoxcompose.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcompose.utilities.getCurrentLocaleLanguage
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.LANGUAGES_SUPPORTED
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isHostValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isNameValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isServerSecretValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isSurnameValid
import com.tecknobit.equinoxcore.helpers.LANGUAGE_KEY
import com.tecknobit.equinoxcore.helpers.NAME_KEY
import com.tecknobit.equinoxcore.helpers.SURNAME_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_IDENTIFIER_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_TOKEN_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The **EquinoxAuthViewModel** class is the support class used to execute the authentication requests to the backend
 *
 * @param snackbarHostState The host to launch the snackbar messages
 * @param requester The instance to manage the requests with the backend
 * @param localUser The user of the current logged-in session, used to make the requests to the backend
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever.RetrieverWrapper
 * @see EquinoxViewModel
 */
@Structure
abstract class EquinoxAuthViewModel(
    snackbarHostState: SnackbarHostState,
    private val requester: EquinoxRequester,
    private val localUser: EquinoxLocalUser,
) : EquinoxViewModel(
    snackbarHostState = snackbarHostState
) {

    /**
     * `isSignUp` whether the auth request to execute is sign up or sign in
     */
    lateinit var isSignUp: MutableState<Boolean>

    /**
     * `host` the value of the host to reach
     */
    lateinit var host: MutableState<String>

    /**
     * `hostError` whether the [host] field is not valid
     */
    lateinit var hostError: MutableState<Boolean>

    /**
     * `serverSecret` the value of the server secret
     */
    lateinit var serverSecret: MutableState<String>

    /**
     * `serverSecretError` whether the [serverSecret] field is not valid
     */
    lateinit var serverSecretError: MutableState<Boolean>

    /**
     * `name` the name of the user
     */
    lateinit var name: MutableState<String>

    /**
     * `nameError` whether the [name] field is not valid
     */
    lateinit var nameError: MutableState<Boolean>

    /**
     * `surname` the surname of the user
     */
    lateinit var surname: MutableState<String>

    /**
     * `surnameError` whether the [surname] field is not valid
     */
    lateinit var surnameError: MutableState<Boolean>

    /**
     * `email` the email of the user
     */
    lateinit var email: MutableState<String>

    /**
     * `emailError` whether the [email] field is not valid
     */
    lateinit var emailError: MutableState<Boolean>

    /**
     * `password` the password of the user
     */
    lateinit var password: MutableState<String>

    /**
     * `passwordError` whether the [password] field is not valid
     */
    lateinit var passwordError: MutableState<Boolean>

    /**
     * Wrapper function to execute the specific authentication request
     *
     */
    fun auth() {
        if (isSignUp.value) {
            if (signUpFormIsValid())
                signUp()
        } else {
            if (signInFormIsValid())
                signIn()
        }
    }

    /**
     * Method to execute the sign-up authentication request, if successful the [localUser] will
     * be initialized with the data received by the request
     *
     * @param onFailure The action to execute when the request failed
     */
    private fun signUp(
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        if (signUpFormIsValid()) {
            viewModelScope.launch {
                val language = getUserLanguage()
                requester.changeHost(host.value)
                requester.sendRequest(
                    request = {
                        signUp(
                            serverSecret = serverSecret.value,
                            name = name.value,
                            surname = surname.value,
                            email = email.value,
                            password = password.value,
                            language = language,
                            custom = getSignUpCustomParameters()
                        )
                    },
                    onSuccess = { response ->
                        launchApp(
                            name = name.value,
                            surname = surname.value,
                            language = language,
                            response = response.toResponseData(),
                            custom = getSignUpCustomParameters()
                        )
                    },
                    onFailure = onFailure
                )
            }
        }
    }

    /**
     * Method used to get the current user language
     *
     * @return the user language as [String]
     */
    protected fun getUserLanguage(): String {
        val currentLanguageTag = getValidUserLanguage()
        val language = LANGUAGES_SUPPORTED[currentLanguageTag]
        return if (language == null)
            DEFAULT_LANGUAGE
        else
            currentLanguageTag
    }

    /**
     * Method used to get a supported language for the user
     *
     * @return a supported language for the user as [String]
     */
    private fun getValidUserLanguage(): String {
        val currentLanguageTag: String = getCurrentLocaleLanguage().substring(0, 2)
        if (LANGUAGES_SUPPORTED[currentLanguageTag] == null)
            return DEFAULT_LANGUAGE
        return currentLanguageTag
    }

    /**
     * Method used to get the list of the custom parameters to use in the [signUp] request
     *
     * The order of the custom parameters must be the same of that specified in your customization of the
     * [getSignUpValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
     * method
     *
     * 
     */
    protected open fun getSignUpCustomParameters(): Array<out Any?> {
        return emptyArray()
    }

    /**
     * Method to validate the inputs for the [signUp] request
     *
     * @return whether the inputs are valid as [Boolean]
     */
    protected open fun signUpFormIsValid(): Boolean {
        var isValid: Boolean = isHostValid(host.value)
        if (!isValid) {
            hostError.value = true
            return false
        }
        isValid = isServerSecretValid(serverSecret.value)
        if (!isValid) {
            serverSecretError.value = true
            return false
        }
        isValid = isNameValid(name.value)
        if (!isValid) {
            nameError.value = true
            return false
        }
        isValid = isSurnameValid(surname.value)
        if (!isValid) {
            surnameError.value = true
            return false
        }
        isValid = isEmailValid(email.value)
        if (!isValid) {
            emailError.value = true
            return false
        }
        isValid = isPasswordValid(password.value)
        if (!isValid) {
            passwordError.value = true
            return false
        }
        return true
    }

    /**
     * Method to execute the sign in authentication request, if successful the [localUser] will
     * be initialized with the data received by the request
     *
     * @param onFailure The action to execute when the request failed
     *
     */
    private fun signIn(
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        if (signInFormIsValid()) {
            viewModelScope.launch {
                requester.changeHost(host.value)
                requester.sendRequest(
                    request = {
                        requester.signIn(
                            email = email.value,
                            password = password.value,
                            custom = getSignInCustomParameters()
                        )
                    },
                    onSuccess = { response ->
                        val data = response.toResponseData()
                        val jLanguage = data[LANGUAGE_KEY]
                        launchApp(
                            name = data[NAME_KEY]!!.jsonPrimitive.content,
                            surname = data[SURNAME_KEY]!!.jsonPrimitive.content,
                            language = if (jLanguage != null)
                                jLanguage.jsonPrimitive.content
                            else
                                DEFAULT_LANGUAGE,
                            response = data,
                            custom = getSignInCustomParameters()
                        )
                    },
                    onFailure = onFailure
                )
            }
        }
    }

    /**
     * Method used to get the list of the custom parameters to use in the [signIn] request.
     *
     * The order of the custom parameters must be the same of that specified in your customization of the
     * [getSignUpValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
     * method
     *
     **/
    protected open fun getSignInCustomParameters(): Array<out Any?> {
        return emptyArray()
    }

    /**
     * Method to validate the inputs for the [signIn] request
     *
     * @return whether the inputs are valid as [Boolean]
     */
    protected open fun signInFormIsValid(): Boolean {
        var isValid: Boolean = isHostValid(host.value)
        if (!isValid) {
            hostError.value = true
            return false
        }
        isValid = isEmailValid(email.value)
        if (!isValid) {
            emailError.value = true
            return false
        }
        isValid = isPasswordValid(password.value)
        if (!isValid) {
            passwordError.value = true
            return false
        }
        return true
    }

    /**
     * Method to launch the application after the authentication request, will be instantiated with the user details
     * both the [requester] and the [localUser]
     *
     * @param response The response of the authentication request
     * @param name The name of the user
     * @param surname The surname of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user
     */
    @RequiresSuperCall
    protected open fun launchApp(
        response: JsonObject,
        name: String,
        surname: String,
        language: String,
        vararg custom: Any?,
    ) {
        requester.setUserCredentials(
            userId = response[USER_IDENTIFIER_KEY]!!.jsonPrimitive.content,
            userToken = response[USER_TOKEN_KEY]!!.jsonPrimitive.content
        )
        localUser.insertNewUser(
            host.value,
            name,
            surname,
            email.value,
            password.value,
            language,
            response,
            custom
        )
    }

}