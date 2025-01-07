package com.tecknobit.equinoxcompose.session

import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.HOST_ADDRESS_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_IDENTIFIER_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.USER_TOKEN_KEY
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The `EquinoxLocalUser` class is useful to represent a user in the client application
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
@Structure
abstract class EquinoxLocalUser {

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
            fun getInstance(theme: String?): ApplicationTheme {
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
     * `hostAddress` the host address which the user communicate
     */
    protected var hostAddress: String? = null

    /**
     * `userId` the identifier of the user
     */
    protected var userId: String? = null

    /**
     * `userToken` the token of the user
     */
    protected var userToken: String? = null

    /**
     * `profilePic` the profile pick of the user
     */
    protected var profilePic: String? = null

    /**
     * `name` the name of the user
     */
    protected var name: String? = null

    /**
     * `surname` the surname of the user
     */
    protected var surname: String? = null

    /**
     * `email` the email of the user
     */
    protected var email: String? = null

    /**
     * `password` the password of the user
     */
    protected var password: String? = null

    /**
     * `language` the language of the user
     */
    protected var language: String? = null

    /**
     * `theme` the theme of the user
     */
    protected var theme: ApplicationTheme? = null

    val isAuthenticated: Boolean
        /**
         * Method to get whether the user is already authenticated in a session
         *
         * @return whether the user is already authenticated in a session as [Boolean]
         */
        get() = userId != null

    /**
     * Method to init the local user session
     */
    @RequiresSuperCall
    protected fun initLocalUser() {
        hostAddress = getHostAddress()
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
     *         // First, set your custom parameters
     *         setCurrency(custom[0].toString())
     *         // Then, invoke the super method
     *         super.insertNewUser(hostAddress, name, surname, email, password, language, response)
     *     }
     *
     *     private fun setCurrency(currency: String) {
     *         this.setPreference("currency", currency)
     *         this.currency = currency
     *     }
     *
     *     fun getCurrency(): String? {
     *         return this.currency
     *     }
     * }
     * ```
     */
    @RequiresSuperCall
    fun insertNewUser(
        hostAddress: String?,
        name: String?,
        surname: String?,
        email: String?,
        password: String?,
        language: String?,
        response: JsonObject,
        vararg custom: Any?,
    ) {
        setHostAddress(hostAddress)
        setUserId(response[USER_IDENTIFIER_KEY]!!.jsonPrimitive.content)
        setUserToken(response[USER_TOKEN_KEY]!!.jsonPrimitive.content)
        setProfilePic(response[PROFILE_PIC_KEY]!!.jsonPrimitive.content)
        setName(name)
        setSurname(surname)
        setEmail(email)
        setPassword(password)
        setLanguage(language)
        setTheme(ApplicationTheme.Auto)
        initLocalUser()
    }

    /**
     * Method to set the [hostAddress] instance <br></br>
     *
     * @param hostAddress: the host address which the user communicate
     */
    fun setHostAddress(
        hostAddress: String?,
    ) {
        setPreference(HOST_ADDRESS_KEY, hostAddress)
        this.hostAddress = hostAddress
    }

    /**
     * Method to get [hostAddress] instance <br></br>
     * No-any params required
     *
     * @return [hostAddress] instance as [String]
     */
    fun getHostAddress(): String {
        val hostAddress = getPreference(HOST_ADDRESS_KEY)
        return hostAddress
    }

    /**
     * Method to set the [userId] instance <br></br>
     *
     * @param userId: the identifier of the user
     */
    fun setUserId(
        userId: String?,
    ) {
        setPreference(USER_IDENTIFIER_KEY, userId)
        this.userId = userId
    }

    /**
     * Method to get [userId] instance <br></br>
     * No-any params required
     *
     * @return [userId] instance as [String]
     */
    fun getUserId(): String? {
        return userId
    }

    /**
     * Method to set the [userToken] instance <br></br>
     *
     * @param userToken: the token of the user
     */
    fun setUserToken(
        userToken: String?,
    ) {
        setPreference(USER_TOKEN_KEY, userToken)
        this.userToken = userToken
    }

    /**
     * Method to get [userToken] instance <br></br>
     * No-any params required
     *
     * @return [userToken] instance as [String]
     */
    fun getUserToken(): String? {
        return userToken
    }

    /**
     * Method to set the [profilePic] instance <br></br>
     *
     * @param profilePic: the profile pic of the user
     */
    fun setProfilePic(
        profilePic: String,
    ) {
        var profilePicLocal = profilePic
        if (this.profilePic == null || this.profilePic != profilePicLocal) {
            profilePicLocal = "$hostAddress/$profilePicLocal"
            setPreference(PROFILE_PIC_KEY, profilePicLocal)
            this.profilePic = profilePicLocal
        }
    }

    /**
     * Method to get [profilePic] instance <br></br>
     * No-any params required
     *
     * @return [profilePic] instance as [String]
     */
    fun getProfilePic(): String? {
        return profilePic
    }

    /**
     * Method to set the [name] instance <br></br>
     *
     * @param name: the name of the user
     */
    fun setName(
        name: String?,
    ) {
        setPreference(NAME_KEY, name)
        this.name = name
    }

    /**
     * Method to get [name] instance <br></br>
     * No-any params required
     *
     * @return [name] instance as [String]
     */
    fun getName(): String? {
        return name
    }

    /**
     * Method to set the [surname] instance <br></br>
     *
     * @param surname: the surname of the user
     */
    fun setSurname(
        surname: String?,
    ) {
        setPreference(SURNAME_KEY, surname)
        this.surname = surname
    }

    /**
     * Method to get [surname] instance <br></br>
     * No-any params required
     *
     * @return [surname] instance as [String]
     */
    fun getSurname(): String? {
        return surname
    }

    val completeName: String
        /**
         * Method to get the complete name of the user <br></br>
         * No-any params required
         *
         * @return the complete name of the user as [String]
         */
        get() = "$name $surname"

    /**
     * Method to set the [email] instance <br></br>
     *
     * @param email: the email of the user
     */
    fun setEmail(
        email: String?,
    ) {
        setPreference(EMAIL_KEY, email)
        this.email = email
    }

    /**
     * Method to get [email] instance <br></br>
     * No-any params required
     *
     * @return [email] instance as [String]
     */
    fun getEmail(): String? {
        return email
    }

    /**
     * Method to set the [password] instance <br></br>
     *
     * @param password: the password of the user
     */
    fun setPassword(
        password: String?,
    ) {
        setPreference(PASSWORD_KEY, password)
        this.password = password
    }

    /**
     * Method to get [password] instance <br></br>
     * No-any params required
     *
     * @return [password] instance as [String]
     */
    fun getPassword(): String? {
        return password
    }

    /**
     * Method to set the [language] instance <br></br>
     *
     * @param language: the language of the user
     */
    fun setLanguage(
        language: String?,
    ) {
        setPreference(LANGUAGE_KEY, language)
        this.language = language
    }

    /**
     * Method to get [language] instance <br></br>
     * No-any params required
     *
     * @return [language] instance as [String]
     */
    fun getLanguage(): String? {
        return language
    }

    /**
     * Method to set the [theme] instance <br></br>
     *
     * @param theme: the theme of the user
     */
    fun setTheme(
        theme: ApplicationTheme,
    ) {
        setPreference(THEME_KEY, theme.name)
        this.theme = theme
    }

    /**
     * Method to get [theme] instance <br></br>
     * No-any params required
     *
     * @return [theme] instance as [ApplicationTheme]
     */
    fun getTheme(): ApplicationTheme? {
        return theme
    }

    /**
     * Method to store and set a preference
     *
     * @param key:   the key of the preference
     * @param value: the value of the preference
     */
    protected abstract fun setPreference(key: String?, value: String?)

    /**
     * Method to get a stored preference
     *
     * @param key: the key of the preference to get
     * @return the preference stored as [String]
     */
    protected abstract fun getPreference(key: String?): String

    /**
     * Method to clear the current local user session <br></br>
     * No-any params required
     */
    abstract fun clear()
}