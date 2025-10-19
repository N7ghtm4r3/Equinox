@file:OptIn(ExperimentalComposeRuntimeApi::class)

package com.tecknobit.equinoxcompose.session

import androidx.compose.runtime.*
import androidx.compose.ui.text.intl.Locale
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser.ApplicationTheme.Auto
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Returner
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.helpers.*
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.DEFAULT_LANGUAGE
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.SUPPORTED_LANGUAGES
import com.tecknobit.equinoxcore.json.treatsAsString
import com.tecknobit.kmprefs.KMPrefs
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * The `EquinoxLocalUser` class is useful to represent a user in the client application
 *
 * @param localStoragePath The path where store the local session details of the user
 * @param observableKeys The keys related to the properties to make observable during the runtime and react to their changes
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
// TODO: TO DOCU ABOUT THE localStoragePath MUST BE SAFEGUARDED AND NON INSERT HARDCODED

@Structure
open class EquinoxLocalUser(
    localStoragePath: String,
    protected val observableKeys: Set<String> = emptySet(),
    protected val sensitiveKeys: Set<String> = setOf(HOST_ADDRESS_KEY, IDENTIFIER_KEY, TOKEN_KEY),
) {

    /**
     * `ApplicationTheme` list of the available theming for the client applications
     */
    @Serializable
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

    /**
     * `userId` the identifier of the user
     */
    var userId: String? = null

    /**
     * `userToken` the token of the user
     */
    var userToken: String? = null

    /**
     * `profilePic` the profile pick of the user
     */
    var profilePic: String = ""

    /**
     * `name` the name of the user
     */
    var name: String = ""

    /**
     * `surname` the surname of the user
     */
    var surname: String = ""

    /**
     * `email` the email of the user
     */
    var email: String = ""

    /**
     * `language` the language of the user
     */
    var language: String = ""

    /**
     * `theme` the theme of the user
     */
    var theme: ApplicationTheme = Auto

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
        setNullSafePreference<String>(
            key = HOST_ADDRESS_KEY,
            defPrefValue = "",
            prefInit = { hostAddress ->
                this.hostAddress = hostAddress
            }
        )
        setPreference<String>(
            key = IDENTIFIER_KEY,
            prefInit = { userId ->
                this.userId = userId
            }
        )
        setPreference<String>(
            key = TOKEN_KEY,
            prefInit = { userToken ->
                this.userToken = userToken
            }
        )
        setNullSafePreference<String>(
            key = PROFILE_PIC_KEY,
            defPrefValue = "",
            prefInit = { profilePic ->
                this.profilePic = profilePic
            }
        )
        setNullSafePreference<String>(
            key = NAME_KEY,
            defPrefValue = "",
            prefInit = { name ->
                this.name = name
            }
        )
        setNullSafePreference<String>(
            key = SURNAME_KEY,
            defPrefValue = "",
            prefInit = { surname ->
                this.surname = surname
            }
        )
        setNullSafePreference<String>(
            key = EMAIL_KEY,
            defPrefValue = "",
            prefInit = { email ->
                this.email = email
            }
        )
        val currentLocaleLanguage = Locale.current.language
        setNullSafePreference<String>(
            key = LANGUAGE_KEY,
            defPrefValue = if (SUPPORTED_LANGUAGES.containsKey(currentLocaleLanguage))
                currentLocaleLanguage
            else
                DEFAULT_LANGUAGE,
            prefInit = { language ->
                this.language = language
            }
        )
        setPreference<String>(
            key = THEME_KEY,
            prefInit = { theme ->
                this.theme = ApplicationTheme.getInstance(theme)
            }
        )
    }

    // TODO: TO DOCU SINCE  1.1.7
    @RequiresSuperCall
    open fun insertNewUser(
        hostAddress: String,
        userId: String,
        userToken: String,
        profilePic: String,
        name: String,
        surname: String,
        email: String,
        language: String,
        vararg custom: Any?,
    ) {
        this.hostAddress = hostAddress
        savePreference(
            key = HOST_ADDRESS_KEY,
            value = hostAddress
        )
        this.userId = userId
        savePreference(
            key = IDENTIFIER_KEY,
            value = userId
        )
        this.userToken = userToken
        savePreference(
            key = TOKEN_KEY,
            value = userToken
        )
        this.profilePic = resolveProfilePicValue(
            rawProfilePic = profilePic
        )
        savePreference(
            key = PROFILE_PIC_KEY,
            value = this.profilePic
        )
        this.name = name
        savePreference(
            key = NAME_KEY,
            value = name
        )
        this.surname = surname
        savePreference(
            key = SURNAME_KEY,
            value = surname
        )
        this.email = email
        savePreference(
            key = EMAIL_KEY,
            value = email
        )
        this.language = language
        savePreference(
            key = LANGUAGE_KEY,
            value = language
        )
        this.theme = Auto
        savePreference(
            key = THEME_KEY,
            value = theme
        )
    }

    @Returner
    protected fun resolveProfilePicValue(
        rawProfilePic: String
    ) : String {
        return if (rawProfilePic.startsWith(hostAddress))
            rawProfilePic
        else
            "$hostAddress/$rawProfilePic"
    }

    // TODO: TO DOCU SINCE  1.1.7
    protected inline fun <reified T> savePreference(
        key: String,
        value: T?,
        isSensitive: Boolean = sensitiveKeys.contains(key),
    ) {
        preferencesManager.store(
            key = key,
            value = value,
            isSensitive = isSensitive
        )
        stateStore.store(
            key = key,
            property = value
        )
    }

    // TODO: TO DOCU SINCE  1.1.7
    @Wrapper
    protected inline fun <reified T> setNullSafePreference(
        key: String,
        defPrefValue: T,
        isSensitive: Boolean = sensitiveKeys.contains(key),
        crossinline prefInit: (T) -> Unit,
    ){
        setPreference(
            key = key,
            defPrefValue = defPrefValue,
            isSensitive = isSensitive,
            prefInit = { prefInit(it!!) }
        )
    }

    // TODO: TO DOCU SINCE  1.1.7
    protected inline fun <reified T> setPreference(
        key: String,
        defPrefValue: T? = null,
        isSensitive: Boolean = sensitiveKeys.contains(key),
        crossinline prefInit: (T?) -> Unit,
    ){
        preferencesManager.consumeRetrieval(
            key = key,
            defValue = defPrefValue,
            isSensitive = isSensitive,
            consume = prefInit
        )
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
            savePreference(
                key = entry.key,
                value = entry.value.treatsAsString()
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