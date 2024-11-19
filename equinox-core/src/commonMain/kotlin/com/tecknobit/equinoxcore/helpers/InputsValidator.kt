package com.tecknobit.equinoxcore.helpers

/**
 * The `InputsValidator` class is useful to validate the inputs
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.1
 */
open class InputsValidator {

    companion object {

        /**
         * `HOST_ADDRESS_KEY` the key for the **"host_address"** field
         */
        const val HOST_ADDRESS_KEY: String = "host_address"

        /**
         * `WRONG_NAME_MESSAGE` error message used when the name inserted is not valid
         */
        const val WRONG_NAME_MESSAGE: String = "wrong_name_key"

        /**
         * `NAME_MAX_LENGTH` the max valid length for the username
         */
        const val NAME_MAX_LENGTH: Int = 20

        /**
         * `WRONG_SURNAME_MESSAGE` error message used when the surname inserted is not valid
         */
        const val WRONG_SURNAME_MESSAGE: String = "wrong_surname_key"

        /**
         * `SURNAME_MAX_LENGTH` the max valid length for the surname
         */
        const val SURNAME_MAX_LENGTH: Int = 30

        /**
         * `WRONG_EMAIL_MESSAGE` error message used when the email inserted is not valid
         */
        const val WRONG_EMAIL_MESSAGE: String = "wrong_email_key"

        /**
         * `EMAIL_MAX_LENGTH` the max valid length for the email
         */
        const val EMAIL_MAX_LENGTH: Int = 75

        /**
         * `WRONG_PASSWORD_MESSAGE` error message used when the password inserted is not valid
         */
        const val WRONG_PASSWORD_MESSAGE: String = "wrong_password_key"

        /**
         * `PASSWORD_MIN_LENGTH` the min valid length for the password
         */
        const val PASSWORD_MIN_LENGTH: Int = 8

        /**
         * `PASSWORD_MAX_LENGTH` the max valid length for the password
         */
        const val PASSWORD_MAX_LENGTH: Int = 32

        /**
         * `WRONG_LANGUAGE_MESSAGE` error message used when the language inserted is not valid
         */
        const val WRONG_LANGUAGE_MESSAGE: String = "wrong_language_key"

        /**
         * `WRONG_CURRENCY_MESSAGE` error message used when the currency inserted is not valid
         */
        const val WRONG_CURRENCY_MESSAGE: String = "wrong_currency_key"

        /**
         * `REVENUE_TITLE_MAX_LENGTH` the max valid length for the revenue title
         */
        const val REVENUE_TITLE_MAX_LENGTH: Int = 30

        /**
         * `REVENUE_DESCRIPTION_MAX_LENGTH` the max valid length for the revenue description
         */
        const val REVENUE_DESCRIPTION_MAX_LENGTH: Int = 250

        /**
         * `MAX_REVENUE_LABELS_NUMBER_LENGTH` the max valid number of labels for revenue
         */
        const val MAX_REVENUE_LABELS_NUMBER: Int = 5

        /**
         * `DEFAULT_LANGUAGE` default language used
         */
        const val DEFAULT_LANGUAGE: String = "en"

        /**
         * `EMAIL_REGEX` regular expression to validate the emails value
         */
        private const val EMAIL_REGEX =
            "^(?![.])(?!.*\\.\\.{2})[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"

        /**
         * `URL_REGEX` regular expression to validate the urls value
         */
        private const val URL_REGEX =
            "^(https?|ftp|file|mailto|data|ws|wss)://(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}(?::\\d{2,5})?(?:/[A-Za-z0-9%&=?./_-]*)?(?:#[A-Za-z0-9_-]*)?$"

        /**
         * `emailValidator` helper to validate the emails values
         */
        private val emailValidator = Regex(EMAIL_REGEX)

        /**
         * `urlValidator` helper to validate the urls values
         */
        private val urlValidator = Regex(URL_REGEX)

        /**
         * `LANGUAGES_SUPPORTED` list of the supported languages
         */
        val LANGUAGES_SUPPORTED: HashMap<String, String> = HashMap()

        init {
            LANGUAGES_SUPPORTED["it"] = "Italiano"
            LANGUAGES_SUPPORTED["en"] = "English"
            LANGUAGES_SUPPORTED["fr"] = "Francais"
            LANGUAGES_SUPPORTED["es"] = "Espanol"
        }

        /**
         * Method to validate a host
         *
         * @param host: host value to check the validity
         * @return whether the host is valid or not as `boolean`
         */
        fun isHostValid(
            host: String?,
        ): Boolean {
            return host != null && urlValidator.matches(host)
        }

        /**
         * Method to validate a server secret
         *
         * @param serverSecret: name value to check the validity
         * @return whether the server secret is valid or not as `boolean`
         */
        fun isServerSecretValid(
            serverSecret: String?,
        ): Boolean {
            return isInputValid(serverSecret)
        }

        /**
         * Method to validate a name
         *
         * @param name: name value to check the validity
         * @return whether the name is valid or not as `boolean`
         */
        fun isNameValid(
            name: String?,
        ): Boolean {
            return isInputValid(name) && name!!.length <= NAME_MAX_LENGTH
        }

        /**
         * Method to validate a surname
         *
         * @param surname: surname value to check the validity
         * @return whether the surname is valid or not as `boolean`
         */
        fun isSurnameValid(
            surname: String?,
        ): Boolean {
            return isInputValid(surname) && surname!!.length <= SURNAME_MAX_LENGTH
        }

        /**
         * Method to validate an email
         *
         * @param email: password value to check the validity
         * @return whether the email is valid or not as `boolean`
         */
        fun isEmailValid(
            email: String?,
        ): Boolean {
            return email != null && emailValidator.matches(email) && email.length <= EMAIL_MAX_LENGTH
        }

        /**
         * Method to validate a password
         *
         * @param password: password value to check the validity
         * @return whether the password is valid or not as `boolean`
         */
        fun isPasswordValid(
            password: String?,
        ): Boolean {
            if (password == null)
                return false
            val passwordLength = password.length
            return isInputValid(password) && passwordLength >= PASSWORD_MIN_LENGTH && passwordLength <= PASSWORD_MAX_LENGTH
        }

        /**
         * Method to validate a language
         *
         * @param language: language value to check the validity
         * @return whether the language is valid or not as `boolean`
         */
        fun isLanguageValid(
            language: String?,
        ): Boolean {
            return language != null && (LANGUAGES_SUPPORTED.containsKey(language) ||
                    LANGUAGES_SUPPORTED.containsValue(language))
        }

        /**
         * Method to validate an input
         *
         * @param field: field value to check the validity
         * @return whether the field is valid or not as `boolean`
         */
        private fun isInputValid(field: String?): Boolean {
            return !field.isNullOrEmpty()
        }

    }

}