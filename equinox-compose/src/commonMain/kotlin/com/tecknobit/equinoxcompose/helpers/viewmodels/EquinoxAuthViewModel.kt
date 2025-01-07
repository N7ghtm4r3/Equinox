// FIXME: ADAPT TO KMP 
//package com.tecknobit.equinoxcompose.helpers.viewmodels
//
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.runtime.MutableState
//import com.tecknobit.equinoxcore.annotations.Structure
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.LANGUAGES_SUPPORTED
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isHostValid
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isNameValid
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isServerSecretValid
//import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isSurnameValid
//
///**
// * The **EquinoxAuthViewModel** class is the support class used to execute the authentication requests to the backend
// *
// * @param snackbarHostState: the host to launch the snackbar messages
// * @param requester: the instance to manage the requests with the backend
// * @param localUser:  the user of the current logged-in session, used to make the requests to the backend
// *
// * @author N7ghtm4r3 - Tecknobit
// * @see EquinoxViewModel
// * @see ViewModel
// * @see RetrieverWrapper
// */
//@Structure
//abstract class EquinoxAuthViewModel(
//    snackbarHostState: SnackbarHostState,
//    private val requester: EquinoxRequester,
//    private val localUser: EquinoxLocalUser,
//) : EquinoxViewModel(
//    snackbarHostState = snackbarHostState
//) {
//
//    /**
//     * **isSignUp** -> whether the auth request to execute is sign up or sign in
//     */
//    lateinit var isSignUp: MutableState<Boolean>
//
//    /**
//     * **host** -> the value of the host to reach
//     */
//    lateinit var host: MutableState<String>
//
//    /**
//     * **hostError** -> whether the [host] field is not valid
//     */
//    lateinit var hostError: MutableState<Boolean>
//
//    /**
//     * **serverSecret** -> the value of the server secret
//     */
//    lateinit var serverSecret: MutableState<String>
//
//    /**
//     * **serverSecretError** -> whether the [serverSecret] field is not valid
//     */
//    lateinit var serverSecretError: MutableState<Boolean>
//
//    /**
//     * **name** -> the name of the user
//     */
//    lateinit var name: MutableState<String>
//
//    /**
//     * **nameError** -> whether the [name] field is not valid
//     */
//    lateinit var nameError: MutableState<Boolean>
//
//    /**
//     * **surname** -> the surname of the user
//     */
//    lateinit var surname: MutableState<String>
//
//    /**
//     * **surnameError** -> whether the [surname] field is not valid
//     */
//    lateinit var surnameError: MutableState<Boolean>
//
//    /**
//     * **email** -> the email of the user
//     */
//    lateinit var email: MutableState<String>
//
//    /**
//     * **emailError** -> whether the [email] field is not valid
//     */
//    lateinit var emailError: MutableState<Boolean>
//
//    /**
//     * **password** -> the password of the user
//     */
//    lateinit var password: MutableState<String>
//
//    /**
//     * **passwordError** -> whether the [password] field is not valid
//     */
//    lateinit var passwordError: MutableState<Boolean>
//
//    /**
//     * Wrapper function to execute the specific authentication request
//     *
//     * No-any params required
//     */
//    fun auth() {
//        if (isSignUp.value)
//            if (signUpFormIsValid())
//                signUp()
//            else
//                if (signInFormIsValid())
//                    signIn()
//    }
//
//    /**
//     * Function to execute the sign-up authentication request, if successful the [localUser] will
//     * be initialized with the data received by the request
//     *
//     * No-any params required
//     */
//    private fun signUp() {
//        if (signUpFormIsValid()) {
//            val language = getUserLanguage()
//            requester.changeHost(host.value)
//            requester.sendRequest(
//                request = {
//                    signUp(
//                        serverSecret = serverSecret.value,
//                        name = name.value,
//                        surname = surname.value,
//                        email = email.value,
//                        password = password.value,
//                        language = language,
//                        custom = getSignUpCustomParameters()
//                    )
//                },
//                onSuccess = { response ->
//                    launchApp(
//                        name = name.value,
//                        surname = surname.value,
//                        language = language,
//                        response = response,
//                        custom = getSignUpCustomParameters()
//                    )
//                },
//                onFailure = { showSnackbarMessage(it) }
//            )
//        }
//    }
//
//    /**
//     * Function to get the current user language
//     *
//     * No-any params required
//     *
//     * @return the user language as [String]
//     */
//    protected fun getUserLanguage(): String {
//        val currentLanguageTag = getValidUserLanguage()
//        val language = LANGUAGES_SUPPORTED[currentLanguageTag]
//        return if (language == null)
//            DEFAULT_LANGUAGE
//        else
//            currentLanguageTag
//    }
//
//    /**
//     * Method to get a supported language for the user
//     *
//     * @return a supported language for the user as [String]
//     */
//    private fun getValidUserLanguage(): String {
//        val currentLanguageTag: String = Locale.getDefault().toLanguageTag().substring(0, 2)
//        if (LANGUAGES_SUPPORTED[currentLanguageTag] == null)
//            return DEFAULT_LANGUAGE
//        return currentLanguageTag
//    }
//
//    /**
//     * Function to get the list of the custom parameters to use in the [signUp] request
//     *
//     * The order of the custom parameters must be the same of that specified in your customization of the
//     * [getQueryValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
//     * method
//     *
//     * No-any params required
//     */
//    protected open fun getSignUpCustomParameters(): Array<out Any?> {
//        return emptyArray()
//    }
//
//    /**
//     * Function to validate the inputs for the [signUp] request
//     *
//     * No-any params required
//     *
//     * @return whether the inputs are valid as [Boolean]
//     */
//    protected open fun signUpFormIsValid(): Boolean {
//        var isValid: Boolean = isHostValid(host.value)
//        if (!isValid) {
//            hostError.value = true
//            return false
//        }
//        isValid = isServerSecretValid(serverSecret.value)
//        if (!isValid) {
//            serverSecretError.value = true
//            return false
//        }
//        isValid = isNameValid(name.value)
//        if (!isValid) {
//            nameError.value = true
//            return false
//        }
//        isValid = isSurnameValid(surname.value)
//        if (!isValid) {
//            surnameError.value = true
//            return false
//        }
//        isValid = isEmailValid(email.value)
//        if (!isValid) {
//            emailError.value = true
//            return false
//        }
//        isValid = isPasswordValid(password.value)
//        if (!isValid) {
//            passwordError.value = true
//            return false
//        }
//        return true
//    }
//
//    /**
//     * Function to execute the sign in authentication request, if successful the [localUser] will
//     * be initialized with the data received by the request
//     *
//     * No-any params required
//     */
//    private fun signIn() {
//        if (signInFormIsValid()) {
//            requester.changeHost(host.value)
//            requester.sendRequest(
//                request = {
//                    requester.signIn(
//                        email = email.value,
//                        password = password.value,
//                        custom = getSignInCustomParameters()
//                    )
//                },
//                onSuccess = { response ->
//                    launchApp(
//                        name = response.getString(NAME_KEY),
//                        surname = response.getString(SURNAME_KEY),
//                        language = response.getString(LANGUAGE_KEY),
//                        response = response,
//                        custom = getSignInCustomParameters()
//                    )
//                },
//                onFailure = { showSnackbarMessage(it) }
//            )
//        }
//    }
//
//    /**
//     * Function to get the list of the custom parameters to use in the [signIn] request.
//     *
//     * The order of the custom parameters must be the same of that specified in your customization of the
//     * [getQueryValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
//     * method
//     *
//     * No-any params required
//     *
//     */
//    protected open fun getSignInCustomParameters(): Array<out Any?> {
//        return emptyArray()
//    }
//
//    /**
//     * Function to validate the inputs for the [signIn] request
//     *
//     * No-any params required
//     *
//     * @return whether the inputs are valid as [Boolean]
//     */
//    protected open fun signInFormIsValid(): Boolean {
//        var isValid: Boolean = isHostValid(host.value)
//        if (!isValid) {
//            hostError.value = true
//            return false
//        }
//        isValid = isEmailValid(email.value)
//        if (!isValid) {
//            emailError.value = true
//            return false
//        }
//        isValid = isPasswordValid(password.value)
//        if (!isValid) {
//            passwordError.value = true
//            return false
//        }
//        return true
//    }
//
//    /**
//     * Function to launch the application after the authentication request, will be instantiated with the user details
//     * both the [requester] and the [localUser]
//     *
//     * @param response: the response of the authentication request
//     * @param name: the name of the user
//     * @param surname: the surname of the user
//     * @param language: the language of the user
//     * @param custom: the custom parameters added in a customization of the [EquinoxUser]
//     */
//    protected open fun launchApp(
//        response: JsonHelper,
//        name: String,
//        surname: String,
//        language: String,
//        vararg custom: Any?,
//    ) {
//        requester.setUserCredentials(
//            userId = response.getString(IDENTIFIER_KEY),
//            userToken = response.getString(TOKEN_KEY)
//        )
//        localUser.insertNewUser(
//            host.value,
//            name,
//            surname,
//            email.value,
//            password.value,
//            language,
//            response,
//            custom
//        )
//    }
//
//}