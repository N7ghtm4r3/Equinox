package com.tecknobit.equinoxcompose.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser.ApplicationTheme
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest

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
     * **newEmail** -> the value of the new email to set
     */
    lateinit var newEmail: MutableState<String>

    /**
     * **newEmailError** -> whether the [newEmail] field is not valid
     */
    lateinit var newEmailError: MutableState<Boolean>

    /**
     * **newPassword** -> the value of the new password to set
     */
    lateinit var newPassword: MutableState<String>

    /**
     * **newPasswordError** -> whether the [newPassword] field is not valid
     */
    lateinit var newPasswordError: MutableState<Boolean>

    /**
     * Method to execute the profile pic change
     *
     * @param imagePath The path of the image to set
     * @param profilePic The state used to display the current profile pic
     */
    fun changeProfilePic(
        imagePath: String,
        profilePic: MutableState<String>,
    ) {
        // TODO: TO SET 
        /*requester.sendRequest(
            request = {
                changeProfilePic(
                    profilePic = File(imagePath)
                )
            },
            onSuccess = { response ->
                profilePic.value = imagePath
                localUser.setProfilePic(
                    profilePic = response.toResponseData().jsonPrimitive.content
                )
            },
            onFailure = { showSnackbarMessage(it) }
        )*/
    }

    /**
     * Method to execute the email change
     *
     * @param onSuccess The action to execute if the request has been successful
     */
    fun changeEmail(
        onSuccess: () -> Unit,
    ) {
        if (isEmailValid(newEmail.value)) {
            requester.sendRequest(
                request = {
                    changeEmail(
                        newEmail = newEmail.value
                    )
                },
                onSuccess = {
                    localUser.setEmail(
                        email = newEmail.value
                    )
                    onSuccess.invoke()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        } else
            newEmailError.value = true
    }

    /**
     * Method to execute the password change
     *
     * @param onSuccess The action to execute if the request has been successful
     */
    fun changePassword(
        onSuccess: () -> Unit,
    ) {
        if (isPasswordValid(newPassword.value)) {
            requester.sendRequest(
                request = {
                    changePassword(
                        newPassword = newPassword.value
                    )
                },
                onSuccess = { onSuccess.invoke() },
                onFailure = { showSnackbarMessage(it) }
            )
        } else
            newPasswordError.value = true
    }

    /**
     * Method to execute the language change
     *
     * @param newLanguage The new language of the user
     * @param onSuccess The action to execute if the request has been successful
     */
    fun changeLanguage(
        newLanguage: String,
        onSuccess: () -> Unit,
    ) {
        requester.sendRequest(
            request = {
                changeLanguage(
                    newLanguage = newLanguage
                )
            },
            onSuccess = {
                localUser.setLanguage(
                    language = newLanguage
                )
                onSuccess.invoke()
            },
            onFailure = { showSnackbarMessage(it) }
        )
    }

    /**
     * Method to execute the theme change
     *
     * @param newTheme The new theme of the user
     * @param onChange The action to execute when the theme changed
     */
    fun changeTheme(
        newTheme: ApplicationTheme,
        onChange: () -> Unit,
    ) {
        localUser.setTheme(
            theme = newTheme
        )
        onChange.invoke()
    }

    /**
     * Method to execute the account deletion
     *
     * @param onDelete The action to execute when the account has been deleted
     */
    fun deleteAccount(
        onDelete: () -> Unit,
    ) {
        requester.sendRequest(
            request = { requester.deleteAccount() },
            onSuccess = {
                clearSession(
                    onClear = onDelete
                )
            },
            onFailure = { showSnackbarMessage(it) }
        )
    }

    /**
     * Method to clear the current [localUser] session
     *
     * @param onClear The action to execute when the session has been cleaned
     */
    fun clearSession(
        onClear: () -> Unit,
    ) {
        localUser.clear()
        requester.setUserCredentials(
            userId = null,
            userToken = null
        )
        onClear.invoke()
    }

}