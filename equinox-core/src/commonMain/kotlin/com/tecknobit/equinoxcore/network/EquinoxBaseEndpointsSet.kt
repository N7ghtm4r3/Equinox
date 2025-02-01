package com.tecknobit.equinoxcore.network

/**
 * The `EquinoxBaseEndpointsSet` class is a container with all the Equinox's system base endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.6
 */
open class EquinoxBaseEndpointsSet {

    companion object {

        /**
         * `BASE_ENDPOINT` the base endpoint for the backend service
         */
        const val BASE_EQUINOX_ENDPOINT: String = "/api/v1/"

        /**
         * `SIGN_UP_ENDPOINT` the endpoint to execute the sign-up auth action
         */
        const val SIGN_UP_ENDPOINT: String = "users/signUp"

        /**
         * `SIGN_IN_ENDPOINT` the endpoint to execute the sign-in auth action
         */
        const val SIGN_IN_ENDPOINT: String = "users/signIn"

        /**
         * `DYNAMIC_ACCOUNT_DATA_ENDPOINT` the endpoint where the user can retrieve the dynamic data of his/her account,
         * such email, profile picture, etc...
         */
        const val DYNAMIC_ACCOUNT_DATA_ENDPOINT: String = "/dynamicAccountData"

        /**
         * `CHANGE_PROFILE_PIC_ENDPOINT` the endpoint to execute the change of the user profile pic
         */
        const val CHANGE_PROFILE_PIC_ENDPOINT: String = "/changeProfilePic"

        /**
         * `CHANGE_EMAIL_ENDPOINT` the endpoint to execute the change of the user email
         */
        const val CHANGE_EMAIL_ENDPOINT: String = "/changeEmail"

        /**
         * `CHANGE_PASSWORD_ENDPOINT` the endpoint to execute the change of the user password
         */
        const val CHANGE_PASSWORD_ENDPOINT: String = "/changePassword"

        /**
         * `CHANGE_LANGUAGE_ENDPOINT` the endpoint to execute the change of the user language
         */
        const val CHANGE_LANGUAGE_ENDPOINT: String = "/changeLanguage"

    }

}
