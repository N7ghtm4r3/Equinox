package com.tecknobit.equinoxcompose.helpers.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxbackend.Requester.Companion.sendRequest
import com.tecknobit.equinoxbackend.environment.helpers.EquinoxRequester
import com.tecknobit.equinoxbackend.environment.models.EquinoxLocalUser
import com.tecknobit.equinoxbackend.environment.models.EquinoxUser.ApplicationTheme
import com.tecknobit.equinoxbackend.environment.models.EquinoxUser.PROFILE_PIC_KEY
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import java.io.File

/**
 * The **EquinoxProfileViewModel** class is the support class used to change the user account settings or preferences
 *
 * @param snackbarHostState The host to launch the snackbar messages
 * @param requester The instance to manage the requests with the backend
 * @param localUser:  the user of the current logged-in session, used to make the requests to the backend
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxViewModel
 * @see ViewModel
 * @see FetcherManagerWrapper
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
     * Function to execute the profile pic change
     *
     * @param imagePath The path of the image to set
     * @param profilePic The state used to display the current profile pic
     */
    fun changeProfilePic(
        imagePath: String,
        profilePic: MutableState<String>,
    ) {
        requester.sendRequest(
            request = {
                changeProfilePic(
                    profilePic = File(imagePath)
                )
            },
            onSuccess = {
                profilePic.value = imagePath
                localUser.profilePic = it.getString(PROFILE_PIC_KEY)
            },
            onFailure = { showSnackbarMessage(it) }
        )
    }

    /**
     * Function to execute the email change
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
                    localUser.email = newEmail.value
                    onSuccess.invoke()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        } else
            newEmailError.value = true
    }

    /**
     * Function to execute the password change
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
     * Function to execute the language change
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
                localUser.language = newLanguage
                onSuccess.invoke()
            },
            onFailure = { showSnackbarMessage(it) }
        )
    }

    /**
     * Function to execute the theme change
     *
     * @param newTheme The new theme of the user
     * @param onChange The action to execute when the theme changed
     */
    fun changeTheme(
        newTheme: ApplicationTheme,
        onChange: () -> Unit,
    ) {
        localUser.theme = newTheme
        onChange.invoke()
    }

    /**
     * Function to execute the account deletion
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