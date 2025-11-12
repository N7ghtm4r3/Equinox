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
 * @param localStoragePath The path where store the local session details of the user.
 * It is suggested to not hardcode it directly, but for example create a property in `gradle.properties` file or
 * similar and `do not share` in public repos
 * @param observableKeys The keys related to the properties to make observable during the runtime and react to their changes
 * @param sensitiveKeys The keys related to the properties considered sensitive that require to be safeguarded
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
@Structure
open class EquinoxLocalUser(
    localStoragePath: String,
    protected val observableKeys: Set<String> = emptySet(),
    protected val sensitiveKeys: Set<String> = DEFAULT_SENSITIVE_KEYS,
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

    }

    companion object {

        /**
         * `DEFAULT_SENSITIVE_KEYS` set of keys related to the properties considered sensitive that require to be
         * safeguarded.
         *
         * To add custom keys or change the default properties you can do as follows:
         *
         * ```kotlin
         * val mySensitiveKeys = buildSet {
         *      addAll(DEFAULT_SENSITIVE_KEYS) // omit if is not required to include default set
         *      ...
         *      add(your_key)
         * }
         * ```
         *
         * @since 1.1.7
         */
        val DEFAULT_SENSITIVE_KEYS: Set<String> = setOf(HOST_ADDRESS_KEY, IDENTIFIER_KEY, TOKEN_KEY)

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
        protected set

    /**
     * `userId` the identifier of the user
     */
    var userId: String? = null
        protected set

    /**
     * `userToken` the token of the user
     */
    var userToken: String? = null
        protected set

    /**
     * `profilePic` the profile pick of the user
     */
    var profilePic: String = ""
        protected set

    /**
     * `name` the name of the user
     */
    var name: String = ""
        protected set

    /**
     * `surname` the surname of the user
     */
    var surname: String = ""
        protected set

    /**
     * `email` the email of the user
     */
    var email: String = ""
        protected set

    /**
     * `language` the language of the user
     */
    var language: String = ""
        protected set

    /**
     * `theme` the theme of the user
     */
    var theme: ApplicationTheme = Auto
        protected set

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
        // TODO: TO REMOVE THIS TRY-CATCH IN FUTURE RELEASES
        try {
            initLocalUser()
        } catch (e: Exception) {
            preferencesManager.clearAll()
            throw e
        }
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
        setNullSafePreference(
            key = THEME_KEY,
            defPrefValue = Auto,
            prefInit = { theme ->
                this.theme = theme
            }
        )
    }

    /**
     * Method used to insert a new user and save locally his/her properties
     *
     * @param hostAddress The host address with which the user communicates
     * @param userId The identifier of the user
     * @param userToken The token of the user
     * @param profilePic The profile picture of the user
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param language The language of the user
     * @param custom The custom parameters added during the customization of the [EquinoxLocalUser]
     */
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
        initHostAddress(
            hostAddress = hostAddress
        )
        initUserId(
            userId = userId
        )
        initUserToken(
            userToken = userToken
        )
        initProfilePic(
            profilePic = profilePic
        )
        initName(
            name = name
        )
        initSurname(
            surname = surname
        )
        initEmail(
            email = email
        )
        initLanguage(
            language = language
        )
        initTheme(
            theme = Auto
        )
    }

    /**
     * Method to initialize the [hostAddress] property and locally save its value with the [savePreference] method
     *
     * @param hostAddress The host address with which the user communicates
     * 
     * @since 1.1.7
     */
    fun initHostAddress(
        hostAddress: String
    ) {
        this.hostAddress = hostAddress
        savePreference(
            key = HOST_ADDRESS_KEY,
            value = hostAddress
        )
    }

    /**
     * Method to initialize the [userId] property and locally save its value with the [savePreference] method
     * 
     * @param userId The identifier of the user
     *
     * @since 1.1.7
     */
    fun initUserId(
        userId: String
    ) {
        this.userId = userId
        savePreference(
            key = IDENTIFIER_KEY,
            value = userId
        )
    }

    /**
     * Method to initialize the [userToken] property and locally save its value with the [savePreference] method
     *
     * @param userToken The token of the user
     *
     * @since 1.1.7
     */
    fun initUserToken(
        userToken: String
    ) {
        this.userToken = userToken
        savePreference(
            key = TOKEN_KEY,
            value = userToken
        )
    }

    /**
     * Method to initialize the [profilePic] property and locally save its value with the [savePreference] method
     *
     * @param profilePic The profile picture of the user
     *
     * @since 1.1.7
     */
    fun initProfilePic(
        profilePic: String
    ) {
        this.profilePic = resolveProfilePicValue(
            rawProfilePic = profilePic
        )
        savePreference(
            key = PROFILE_PIC_KEY,
            value = this.profilePic
        )
    }

    /**
     * Method used to resolve the [profilePic] value to save whether is needed to append the [hostAddress] value
     * 
     * @param rawProfilePic The value of the profile pic to resolve
     * 
     * @return the value of the profile pic to save as [String]
     * 
     * @since 1.1.7
     */
    @Returner
    protected fun resolveProfilePicValue(
        rawProfilePic: String
    ) : String {
        return if (rawProfilePic.startsWith(hostAddress))
            rawProfilePic
        else
            "$hostAddress/$rawProfilePic"
    }

    /**
     * Method to initialize the [name] property and locally save its value with the [savePreference] method
     *
     * @param name The name of the user
     *
     * @since 1.1.7
     */
    fun initName(
        name: String
    ) {
        this.name = name
        savePreference(
            key = NAME_KEY,
            value = name
        )
    }

    /**
     * Method to initialize the [surname] property and locally save its value with the [savePreference] method
     *
     * @param surname The surname of the user
     *
     * @since 1.1.7
     */
    fun initSurname(
        surname: String
    ) {
        this.surname = surname
        savePreference(
            key = SURNAME_KEY,
            value = surname
        )
    }

    /**
     * Method to initialize the [email] property and locally save its value with the [savePreference] method
     *
     * @param hostAddress The host address with which the user communicates
     *
     * @since 1.1.7
     */
    fun initEmail(
        email: String
    ) {
        this.email = email
        savePreference(
            key = EMAIL_KEY,
            value = email
        )
    }

    /**
     * Method to initialize the [language] property and locally save its value with the [savePreference] method
     *
     * @param language The language of the user
     *
     * @since 1.1.7
     */
    fun initLanguage(
        language: String
    ) {
        this.language = language
        savePreference(
            key = LANGUAGE_KEY,
            value = language
        )
    }

    /**
     * Method to initialize the [theme] property and locally save its value with the [savePreference] method
     *
     * @param theme The theme chosen by the user 
     *
     * @since 1.1.7
     */
    fun initTheme(
        theme: ApplicationTheme
    ) {
        this.theme = Auto
        savePreference(
            key = THEME_KEY,
            value = theme
        )
    }

    /**
     * Method to locally save a preference. 
     * 
     * If the [key] is present in the [sensitiveKeys] set, before saving it, will be encrypted.
     * Similar to the [sensitiveKeys], if the [key] is present in the [observableKeys] set will be handled by the 
     * [EquinoxLocalUserStateStore]
     * 
     * @param key The representative key of a preference
     * @param value The value of the preference to save
     * 
     * @param T The type of the preference to save
     * 
     * @since 1.1.7
     */
    protected inline fun <reified T> savePreference(
        key: String,
        value: T?,
    ) {
        preferencesManager.store(
            key = key,
            value = value,
            isSensitive = sensitiveKeys.contains(key)
        )
        stateStore.store(
            key = key,
            property = value
        )
    }

    /**
     * Method to locally retrieve a `non-nullable` preference and to initialize the related property
     *
     * If the [key] is present in the [sensitiveKeys] set, before initialize the property, will be decrypted
     *
     * @param key The representative key of a preference
     * @param defPrefValue The default value to use if the real one is not saved yet
     * @param prefInit The initialization routine to perform to use the retrieved value and to use it to initialize a property
     *
     * @param T The type of the preference to retrieve
     * 
     * @since 1.1.7
     */
    @Wrapper
    protected inline fun <reified T> setNullSafePreference(
        key: String,
        defPrefValue: T,
        crossinline prefInit: (T) -> Unit,
    ) {
        setPreference(
            key = key,
            defPrefValue = defPrefValue,
            prefInit = { prefInit(it!!) }
        )
    }

    /**
     * Method to locally retrieve a preference and to initialize the related property
     *
     * If the [key] is present in the [sensitiveKeys] set, before initialize the property, will be decrypted
     *
     * @param key The representative key of a preference
     * @param defPrefValue The default value to use if the real one is not saved yet
     * @param prefInit The initialization routine to perform to use the retrieved value and to use it to initialize a property
     *
     * @param T The type of the preference to retrieve
     *
     * @since 1.1.7
     */
    protected inline fun <reified T> setPreference(
        key: String,
        defPrefValue: T? = null,
        crossinline prefInit: (T?) -> Unit,
    ) {
        preferencesManager.consumeRetrieval(
            key = key,
            defValue = defPrefValue,
            isSensitive = sensitiveKeys.contains(key),
            consume = { retrieval ->
                prefInit(retrieval)
                stateStore.store(
                    key = key,
                    property = retrieval
                )
            }
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