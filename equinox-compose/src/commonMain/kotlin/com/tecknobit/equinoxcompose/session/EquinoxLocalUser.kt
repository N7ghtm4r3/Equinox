package com.tecknobit.equinoxcompose.session

import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.HOST_ADDRESS_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_IDENTIFIER_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_TOKEN_KEY
import com.tecknobit.kmprefs.KMPrefs
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive


/**
 * The `EquinoxLocalUser` class is useful to represent a user in the client application
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
@Structure
open class EquinoxLocalUser(
    localStoragePath: String,
) {

    /**
     * `ApplicationTheme` list of the available theming for the client applications
     */
    enum class ApplicationTheme {

        /**
         * `Dark` the dark theme to use as theme
         */
        Dark,

        /**
         * `Light` the light theme to use as theme
         */
        Light,

        /**
         * `Auto` the theme to use based on the user current theme set
         */
        Auto;

        companion object {

            /**
             * Method to get an instance of the [ApplicationTheme]
             *
             * @param theme The name of the theme to get
             * @return the theme instance as [ApplicationTheme]
             */
            fun getInstance(
                theme: String?,
            ): ApplicationTheme {
                if (theme == null) return Auto
                return when (theme) {
                    "Dark" -> Dark
                    "Light" -> Light
                    else -> Auto
                }
            }

        }

    }

    /**
     * `preferencesManager` the local preferences manager
     */
    protected val preferencesManager = KMPrefs(
        path = localStoragePath
    )

    /**
     * `hostAddress` the host address which the user communicate
     */
    var hostAddress: String? = null
        set(value) {
            setPreference(
                key = HOST_ADDRESS_KEY,
                value = value
            )
            field = value
        }

    /**
     * `userId` the identifier of the user
     */
    var userId: String? = null
        set(value) {
            setPreference(
                key = USER_IDENTIFIER_KEY,
                value = value
            )
            field = value
        }

    /**
     * `userToken` the token of the user
     */
    var userToken: String? = null
        set(value) {
            setPreference(
                key = USER_TOKEN_KEY,
                value = value
            )
            field = value
        }

    /**
     * `profilePic` the profile pick of the user
     */
    var profilePic: String? = null
        set(value) {
            if (field != value) {
                val profilePicLocal = if (value == null || value.startsWith(hostAddress!!))
                    value
                else
                    "$hostAddress/$value"
                setPreference(
                    key = PROFILE_PIC_KEY,
                    value = profilePicLocal
                )
                field = profilePicLocal
            }
        }

    /**
     * `name` the name of the user
     */
    var name: String? = null
        set(value) {
            setPreference(
                key = NAME_KEY,
                value = value
            )
            field = value
        }

    /**
     * `surname` the surname of the user
     */
    var surname: String? = null
        set(value) {
            setPreference(
                key = SURNAME_KEY,
                value = value
            )
            field = value
        }

    /**
     * `email` the email of the user
     */
    var email: String? = null
        set(value) {
            setPreference(
                key = EMAIL_KEY,
                value = value
            )
            field = value
        }

    /**
     * `password` the password of the user
     */
    var password: String? = null
        set(value) {
            setPreference(
                key = PASSWORD_KEY,
                value = value
            )
            field = value
        }

    /**
     * `language` the language of the user
     */
    var language: String? = null
        set(value) {
            setPreference(
                key = LANGUAGE_KEY,
                value = value
            )
            field = value
        }

    /**
     * `theme` the theme of the user
     */
    var theme: ApplicationTheme? = null
        set(value) {
            setPreference(
                key = THEME_KEY,
                value = value?.name
            )
            field = value
        }

    val isAuthenticated: Boolean
        /**
         * Method to get whether the user is already authenticated in a session
         *
         * @return whether the user is already authenticated in a session as [Boolean]
         */
        get() = userId != null

    val completeName: String
        /**
         * Method to get the complete name of the user
         *
         * @return the complete name of the user as [String]
         */
        get() = "$name $surname"

    init {
        @Suppress("LeakingThis")
        initLocalUser()
    }

    /**
     * Method to init the local user session
     */
    @RequiresSuperCall
    protected open fun initLocalUser() {
        hostAddress = getPreference(HOST_ADDRESS_KEY)
        userId = getPreference(USER_IDENTIFIER_KEY)
        userToken = getPreference(USER_TOKEN_KEY)
        profilePic = getPreference(PROFILE_PIC_KEY)
        name = getPreference(NAME_KEY)
        surname = getPreference(SURNAME_KEY)
        email = getPreference(EMAIL_KEY)
        password = getPreference(PASSWORD_KEY)
        language = getPreference(LANGUAGE_KEY)
        theme = ApplicationTheme.getInstance(getPreference(THEME_KEY))
    }

    /**
     * Method to insert and initialize a new local user.
     *
     * @param hostAddress The host address with which the user communicates.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param language The preferred language of the user.
     * @param response The payload response received from an authentication request.
     * @param custom Custom parameters added during the customization of the equinox user.
     *
     * ## Example workflow:
     *
     * ```kotlin
     * class CustomLocalUser : EquinoxLocalUser() {
     *
     *     var currency: String? = null
     *          set(value) {
     *             setPreference(
     *                 key = "currency",
     *                 value = value
     *             )
     *             field = value
     *         }
     *
     *     fun insertNewUser(
     *         hostAddress: String,
     *         name: String,
     *         surname: String,
     *         email: String,
     *         password: String,
     *         language: String,
     *         response: JsonHelper,
     *         vararg custom: Any
     *     ) {
     *         super.insertNewUser(hostAddress, name, surname, email, password, language, response)
     *         currency = custom[0].toString()
     *     }
     *
     * }
     * ```
     */
    open fun insertNewUser(
        hostAddress: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        response: JsonObject,
        vararg custom: Any?,
    ) {
        this.hostAddress = hostAddress
        userId = response[USER_IDENTIFIER_KEY]!!.jsonPrimitive.content
        userToken = response[USER_TOKEN_KEY]!!.jsonPrimitive.content
        profilePic = response[PROFILE_PIC_KEY]!!.jsonPrimitive.content
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.language = language
        this.theme = ApplicationTheme.Auto
    }

    /**
     * Method to store and set a preference
     *
     * @param key:   the key of the preference
     * @param value: the value of the preference
     */
    protected fun setPreference(
        key: String,
        value: String?,
    ) {
        preferencesManager.storeString(
            key = key,
            value = value
        )
    }

    /**
     * Method to get a stored preference
     *
     * @param key: the key of the preference to get
     * @return the preference stored as nullable [String]
     */
    protected fun getPreference(
        key: String,
    ): String? {
        return preferencesManager.retrieveString(
            key = key
        )
    }

    /**
     * Method to clear the current local user session
     */
    fun clear() {
        preferencesManager.clearAll()
        initLocalUser()
    }

}