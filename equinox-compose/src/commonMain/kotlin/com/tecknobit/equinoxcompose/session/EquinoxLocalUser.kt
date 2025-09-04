@file:OptIn(ExperimentalComposeRuntimeApi::class)

package com.tecknobit.equinoxcompose.session

import androidx.compose.runtime.*
import androidx.compose.ui.text.intl.Locale
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser.ApplicationTheme.Auto
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.SUPPORTED_LANGUAGES
import com.tecknobit.equinoxcore.json.treatsAsString
import com.tecknobit.kmprefs.KMPrefs
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The `EquinoxLocalUser` class is useful to represent a user in the client application
 *
 * @param localStoragePath The path where store the local session details of the user
 * @param observableKeys The keys related to the properties to make observable during the runtime and react to their changes
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
@Structure
open class EquinoxLocalUser(
    localStoragePath: String,
    observableKeys: Set<String> = emptySet()
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
             * Method used to get an instance of the [ApplicationTheme]
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
     * `stateStore` the state store instance used to dynamically keep in memory the observable properties of the local user
     *
     * @since 1.1.6
     */
    protected val stateStore: EquinoxLocalUserStateStore = EquinoxLocalUserStateStore(
        allowedKeys = observableKeys
    )
    
    /**
     * `hostAddress` the host address which the user communicate
     */
    var hostAddress: String = ""
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
                key = IDENTIFIER_KEY,
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
                key = TOKEN_KEY,
                value = value
            )
            field = value
        }

    /**
     * `profilePic` the profile pick of the user
     */
    var profilePic: String = ""
        set(value) {
            val profilePicLocal = if (field != value) {
                if (value.startsWith(hostAddress))
                    value
                else
                    "$hostAddress/$value"
            } else
                value
            setPreference(
                key = PROFILE_PIC_KEY,
                value = profilePicLocal
            )
            field = profilePicLocal
        }

    /**
     * `name` the name of the user
     */
    var name: String = ""
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
    var surname: String = ""
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
    var email: String = ""
        set(value) {
            setPreference(
                key = EMAIL_KEY,
                value = value
            )
            field = value
        }

    /**
     * `language` the language of the user
     */
    var language: String = ""
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
    var theme: ApplicationTheme = Auto
        set(value) {
            setPreference(
                key = THEME_KEY,
                value = value
            )
            field = value
        }

    val isAuthenticated: Boolean
        /**
         * Method used to get whether the user is already authenticated in a session
         *
         * @return whether the user is already authenticated in a session as [Boolean]
         */
        get() = userId != null

    val completeName: String
        /**
         * Method used to get the complete name of the user
         *
         * @return the complete name of the user as [String]
         */
        get() = "$name $surname"

    init {
        @Suppress("ImplicitThis")
        initLocalUser()
    }

    /**
     * Method used to init the local user session
     */
    @RequiresSuperCall
    protected open fun initLocalUser() {
        val currentLocaleLanguage = Locale.current.language
        hostAddress = getNullSafePreference(HOST_ADDRESS_KEY)
        userId = getPreference(IDENTIFIER_KEY)
        userToken = getPreference(TOKEN_KEY)
        profilePic = getNullSafePreference(PROFILE_PIC_KEY)
        name = getNullSafePreference(NAME_KEY)
        surname = getNullSafePreference(SURNAME_KEY)
        email = getNullSafePreference(EMAIL_KEY)
        language = getNullSafePreference(
            key = LANGUAGE_KEY,
            defPrefValue = if (SUPPORTED_LANGUAGES.containsKey(currentLocaleLanguage))
                currentLocaleLanguage
            else
                DEFAULT_LANGUAGE
        )
        theme = ApplicationTheme.getInstance(getPreference(THEME_KEY))
    }

    /**
     * Method used to insert and initialize a new local user.
     *
     * @param hostAddress The host address with which the user communicates
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email address of the user
     * @param language The preferred language of the user
     * @param response The payload response received from an authentication request
     * @param custom Custom parameters added during the customization of the equinox user
     *
     * ## Example workflow
     * TODO: TO REMOVE AND MOVE INTO MARKDOWN DOCU PAGE
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
     *         language: String,
     *         response: JsonHelper,
     *         vararg custom: Any
     *     ) {
     *         super.insertNewUser(hostAddress, name, surname, email, language, response)
     *         currency = custom[0].toString()
     *     }
     *
     * }
     * ```
     */
    @RequiresSuperCall
    open fun insertNewUser(
        hostAddress: String,
        name: String,
        surname: String,
        email: String,
        language: String,
        response: JsonObject,
        vararg custom: Any?,
    ) {
        this.hostAddress = hostAddress
        userId = response[USER_IDENTIFIER_KEY]!!.jsonPrimitive.content
        userToken = response[TOKEN_KEY]!!.jsonPrimitive.content
        profilePic = response[PROFILE_PIC_KEY]!!.jsonPrimitive.content
        this.name = name
        this.surname = surname
        this.email = email
        this.language = language
        this.theme = Auto
    }

    /**
     * Method used to extract a specific value from the custom parameter of the [insertNewUser] method.
     *
     * This method is particularly useful in cases where values are passed as varargs,
     * since Kotlin wraps them inside an array
     *
     * @param indexArray The index of the array from retrieve the custom value
     * @param itemPosition The index of the item inside the created array
     *
     * @return the custom value as [T]
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T> Array<out Any?>.extractsCustomValue(
        indexArray: Int = 0,
        itemPosition: Int,
    ): T {
        return (this[indexArray] as Array<*>)[itemPosition] as T
    }

    /**
     * Method used to store and set a preference
     *
     * @param key:   the key of the preference
     * @param value: the value of the preference
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    protected fun <T> setPreference(
        key: String,
        value: T?,
    ) {
        val preferenceValue = value.toString()
        if(!preferencesManager.valueMatchesTo(key, preferenceValue)) {
            preferencesManager.storeString(
                key = key,
                value = preferenceValue
            )
        }
        stateStore.store(
            key = key,
            property = value
        )
    }

    /**
     * Method used to get a stored preference
     *
     * @param key The key of the preference to get
     * @param defPrefValue Default value of the preference if not stored yet
     *
     * @return the preference stored as nullable [String]
     */
    protected fun getPreference(
        key: String,
        defPrefValue: String? = null,
    ): String? {
        val storedPreference = preferencesManager.retrieveString(
            key = key,
            defValue = defPrefValue
        )
        return storedPreference
    }

    /**
     * Method used to get a stored preference
     *
     * @param key The key of the preference to get
     * @param defPrefValue Default value of the preference if not stored yet
     * @return the preference stored as null-safe [String]
     */
    protected fun getNullSafePreference(
        key: String,
        defPrefValue: String = "",
    ): String {
        return preferencesManager.retrieveString(
            key = key,
            defValue = defPrefValue
        )!!
    }

    /**
     * Method used to clear the current local user session
     */
    fun clear() {
        preferencesManager.clearAll()
        initLocalUser()
    }

    /**
     * Method used to update the dynamic data of the local user
     *
     * @param dynamicData The dynamic data to use to update the current user ones
     */
    fun updateDynamicAccountData(
        dynamicData: JsonObject,
    ) {
        dynamicData.entries.forEach { entry ->
            setPreference(
                entry.key,
                entry.value.treatsAsString()
            )
        }
        initLocalUser()
    }

    /**
     * Method used to observe a property of the local user
     *
     * @param key The associated key to the property to observe
     *
     * @param T The type of the property to observe
     *
     * @return the observable property as [State] of [T]
     *
     * @throws IllegalArgumentException when the requested property is not stored by the [stateStore]
     *
     * @since 1.1.6
     */
    @ExperimentalComposeRuntimeApi
    @Composable
    fun <T> observe(
        key: String
    ) : State<T> {
        @Suppress("UNCHECKED_CAST")
        val observable: State<T>? = remember { stateStore.retrieve(key) as State<T>? }
        require(observable != null) { "Cannot observe a null property" }
        return observable
    }

    /**
     * The `EquinoxLocalUserStateStore` class allows to dynamically store properties that need to be observable in order
     * to properly react to changes
     *
     * @property allowedKeys The keys which are allowed to be stored
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @since 1.1.6
     */
    @ExperimentalComposeRuntimeApi
    protected class EquinoxLocalUserStateStore(
        private val allowedKeys: Set<String> = emptySet()
    ) {

        /**
         * `stateStore` container map of the observable properties
         */
        private val stateStore: MutableMap<String, MutableState<Any?>> = mutableMapOf()

        /**
         * Method used to store an observable property whether its key is present in the [allowedKeys], otherwise the storing
         * will be skipped. When the property has been previously stored will be updated its value
         *
         * @param key The key of the observable property to store
         * @param property The value of the observable property to store
         */
        fun <T> store(
            key: String,
            property: T?
        ) {
            if(!allowedKeys.contains(key))
                return
            val storedProperty = stateStore.getOrPut(
                key = key,
                defaultValue = { mutableStateOf(property) }
            )
            storedProperty.value = property
        }

        /**
         * Method used to retrieve an observable property from the [stateStore] map
         *
         * @param key The key of the observable property to retrieve
         *
         * @return the observable property as nullable [State] of nullable [Any]
         */
        fun retrieve(
            key: String
        ) : State<Any?>? {
            return stateStore[key]
        }

    }

}