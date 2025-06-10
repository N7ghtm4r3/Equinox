package com.tecknobit.equinoxcore.helpers

import com.tecknobit.equinoxcore.annotations.Validator
import kotlin.jvm.JvmStatic

/**
 * The `InputsValidator` class is useful to validate the inputs
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.1
 */
open class InputsValidator {

    companion object {

        /**
         * `NAME_MAX_LENGTH` the max valid length for the username
         */
        const val NAME_MAX_LENGTH: Int = 20

        /**
         * `SURNAME_MAX_LENGTH` the max valid length for the surname
         */
        const val SURNAME_MAX_LENGTH: Int = 30

        /**
         * `EMAIL_MAX_LENGTH` the max valid length for the email
         */
        const val EMAIL_MAX_LENGTH: Int = 75

        /**
         * `PASSWORD_MIN_LENGTH` the min valid length for the password
         */
        const val PASSWORD_MIN_LENGTH: Int = 8

        /**
         * `PASSWORD_MAX_LENGTH` the max valid length for the password
         */
        const val PASSWORD_MAX_LENGTH: Int = 32

        /**
         * `DEFAULT_LANGUAGE` default language used
         */
        const val DEFAULT_LANGUAGE: String = "en"

        /**
         * `EMAIL_REGEX` regular expression to validate the emails value
         */
        @JvmStatic
        protected val EMAIL_REGEX =
            "^(?![.])(?!.*\\.\\.{2})[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"

        /**
         * `URL_REGEX` regular expression to validate the urls value
         */
        @JvmStatic
        protected val URL_REGEX =
            "^[a-zA-Z][a-zA-Z0-9+.-]*://(([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,6}|\\d{1,3}(?:\\.\\d{1,3}){3})(?::\\d{1,5})?(/\\S*)?(\\?(\\S*))?(#(\\S*))?$"

        /**
         * `emailValidator` helper to validate the emails values
         */
        @JvmStatic
        protected val emailValidator = Regex(EMAIL_REGEX)

        /**
         * `urlValidator` helper to validate the urls values
         */
        @JvmStatic
        protected val urlValidator = Regex(URL_REGEX)

        /**
         * `SUPPORTED_LANGUAGES` list of the supported languages
         */
        val SUPPORTED_LANGUAGES: HashMap<String, String> = HashMap()

        init {
            SUPPORTED_LANGUAGES["it"] = "Italiano"
            SUPPORTED_LANGUAGES["en"] = "English"
            SUPPORTED_LANGUAGES["fr"] = "Francais"
            SUPPORTED_LANGUAGES["es"] = "Espanol"
        }

        /**
         * Method used to validate a host
         *
         * @param host The host value to check the validity
         * @return whether the host is valid or not as `boolean`
         */
        @Validator(
            validWhen = "The host is considered valid when is not null and is a valid host address"
        )
        fun isHostValid(
            host: String?,
        ): Boolean {
            return host != null && urlValidator.matches(host)
        }

        /**
         * Method used to validate a server secret
         *
         * @param serverSecret The name value to check the validity
         * @return whether the server secret is valid or not as `boolean`
         */
        @Validator(validWhen = "The server secret is considered valid when is not null and is not empty")
        fun isServerSecretValid(
            serverSecret: String?,
        ): Boolean {
            return isInputValid(serverSecret)
        }

        /**
         * Method used to validate a name
         *
         * @param name The name value to check the validity
         * @return whether the name is valid or not as `boolean`
         */
        @Validator(
            validWhen = """
                The name is considered valid when is not null and is not empty and its length in the validity range
                (max 20 characters)
            """
        )
        fun isNameValid(
            name: String?,
        ): Boolean {
            return isInputValid(name) && name!!.length <= NAME_MAX_LENGTH
        }

        /**
         * Method used to validate a surname
         *
         * @param surname The surname value to check the validity
         * @return whether the surname is valid or not as `boolean`
         */
        @Validator(
            validWhen = """
                The surname is considered valid when is not null and is not empty and its length in the validity range
                (max 30 characters)
            """
        )
        fun isSurnameValid(
            surname: String?,
        ): Boolean {
            return isInputValid(surname) && surname!!.length <= SURNAME_MAX_LENGTH
        }

        /**
         * Method used to validate an email
         *
         * @param email The password value to check the validity
         * @return whether the email is valid or not as `boolean`
         */
        @Validator(
            validWhen = """
                The email is considered valid when is not null and is not empty and its length in the validity range
                (max 75 characters)
            """
        )
        fun isEmailValid(
            email: String?,
        ): Boolean {
            return email != null && emailValidator.matches(email) && email.length <= EMAIL_MAX_LENGTH
        }

        /**
         * Method used to validate a password
         *
         * @param password The password value to check the validity
         * @return whether the password is valid or not as `boolean`
         */
        @Validator(
            validWhen = """
                The password is considered valid when is not null and is not empty and its length in the validity range
                (8-32 characters)
            """
        )
        fun isPasswordValid(
            password: String?,
        ): Boolean {
            if (password == null)
                return false
            val passwordLength = password.length
            return isInputValid(password) && passwordLength >= PASSWORD_MIN_LENGTH && passwordLength <= PASSWORD_MAX_LENGTH
        }

        /**
         * Method used to validate a language
         *
         * @param language The language value to check the validity
         * @return whether the language is valid or not as `boolean`
         */
        @Validator(validWhen = "The language is considered valid when is not null and is not empty and is a supported language")
        fun isLanguageValid(
            language: String?,
        ): Boolean {
            return language != null && (SUPPORTED_LANGUAGES.containsKey(language) ||
                    SUPPORTED_LANGUAGES.containsValue(language))
        }

        /**
         * Method used to validate an input
         *
         * @param input The input value to check the validity
         * @return whether the input is valid or not as `boolean`
         */
        @JvmStatic
        @Validator(validWhen = "The input is considered valid when is not null and is not empty")
        protected fun isInputValid(
            input: String?,
        ): Boolean {
            return !input.isNullOrEmpty()
        }

    }

}