package com.tecknobit.equinoxcompose.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser.ApplicationTheme
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.helpers.PROFILE_PIC_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.equinoxcore.network.sendRequest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The **EquinoxProfileViewModel** class is the support class used to change the user account settings or preferences
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
open class EquinoxProfileViewModel(
    snackbarHostState: SnackbarHostState,
    private val requester: EquinoxRequester,
    private val localUser: EquinoxLocalUser,
) : EquinoxViewModel(
    snackbarHostState = snackbarHostState
) {

    /**
     * `profilePic` the profile picture of the user
     */
    lateinit var profilePic: MutableState<String>

    /**
     * `email` the email of the user
     */
    lateinit var email: MutableState<String>

    /**
     * `password` the password of the user
     */
    lateinit var password: MutableState<String>

    /**
     * `language` the language of the user
     */
    lateinit var language: MutableState<String>

    /**
     * `theme` the theme of the user
     */
    lateinit var theme: MutableState<ApplicationTheme>

    /**
     * `newEmail` the value of the new email to set
     */
    lateinit var newEmail: MutableState<String>

    /**
     * `newEmailError` whether the [newEmail] field is not valid
     */
    lateinit var newEmailError: MutableState<Boolean>

    /**
     * `newPassword` the value of the new password to set
     */
    lateinit var newPassword: MutableState<String>

    /**
     * `newPasswordError` whether the [newPassword] field is not valid
     */
    lateinit var newPasswordError: MutableState<Boolean>

    /**
     * Method used to execute the profile pic change, look how to integrate
     *
     * @param profilePicName The name of the image to set
     * @param profilePicBytes The bytes of the image selected
     * @param onFailure The action to execute when the request failed
     */
    open fun changeProfilePic(
        profilePicName: String,
        profilePicBytes: ByteArray,
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    changeProfilePic(
                        profilePicName = profilePicName,
                        profilePicBytes = profilePicBytes
                    )
                },
                onSuccess = { response ->
                    localUser.profilePic = response.toResponseData()[PROFILE_PIC_KEY]!!.jsonPrimitive.content
                    profilePic.value = localUser.profilePic
                },
                onFailure = onFailure
            )
        }
    }

    /**
     * Method used to execute the email change
     *
     * @param onSuccess The action to execute if the request has been successful
     * @param onFailure The action to execute when the request failed
     */
    open fun changeEmail(
        onSuccess: (() -> Unit)? = null,
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        if (!isEmailValid(newEmail.value)) {
            newEmailError.value = true
            return
        }
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    changeEmail(
                        newEmail = newEmail.value
                    )
                },
                onSuccess = {
                    localUser.email = newEmail.value
                    email.value = newEmail.value
                    onSuccess?.invoke()
                },
                onFailure = onFailure
            )
        }
    }

    /**
     * Method used to execute the password change
     *
     * @param onSuccess The action to execute if the request has been successful
     * @param onFailure The action to execute when the request failed
     */
    open fun changePassword(
        onSuccess: (() -> Unit)? = null,
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        if (!isPasswordValid(newPassword.value)) {
            newPasswordError.value = true
            return
        }
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    changePassword(
                        newPassword = newPassword.value
                    )
                },
                onSuccess = {
                    localUser.password = newPassword.value
                    password.value = newPassword.value
                    onSuccess?.invoke()
                },
                onFailure = onFailure
            )
        }
    }

    /**
     * Method used to execute the language change
     *
     * @param onSuccess The action to execute if the request has been successful
     * @param onFailure The action to execute when the request failed
     */
    open fun changeLanguage(
        onSuccess: (() -> Unit)? = null,
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    changeLanguage(
                        newLanguage = language.value
                    )
                },
                onSuccess = {
                    localUser.language = language.value
                    onSuccess?.invoke()
                },
                onFailure = onFailure
            )
        }
    }

    /**
     * Method used to execute the theme change
     *
     * @param onChange The action to execute when the theme changed
     */
    open fun changeTheme(
        onChange: (() -> Unit)? = null,
    ) {
        localUser.theme = theme.value
        onChange?.invoke()
    }

    /**
     * Method used to execute the account deletion
     *
     * @param onDelete The action to execute when the account has been deleted
     * @param onFailure The action to execute when the request failed
     */
    open fun deleteAccount(
        onDelete: (() -> Unit)? = null,
        onFailure: (JsonObject) -> Unit = {
            showSnackbarMessage(it)
        },
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = { requester.deleteAccount() },
                onSuccess = {
                    clearSession(
                        onClear = onDelete
                    )
                },
                onFailure = onFailure
            )
        }
    }

    /**
     * Method used to clear the current [localUser] session
     *
     * @param onClear The action to execute when the session has been cleaned
     */
    open fun clearSession(
        onClear: (() -> Unit)? = null,
    ) {
        localUser.clear()
        requester.clearSession()
        onClear?.invoke()
    }

}