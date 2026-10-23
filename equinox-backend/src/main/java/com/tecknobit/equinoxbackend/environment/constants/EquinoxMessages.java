package com.tecknobit.equinoxbackend.environment.constants;

/**
 * The {@code EquinoxMessages} class is a container for the message keys used
 * for backend validation and response messages
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public abstract class EquinoxMessages {
    
    /**
     * {@code WRONG_PROCEDURE_MESSAGE} the message key used when the procedure is wrong
     */
    public static final String WRONG_PROCEDURE_MESSAGE = "wrong_procedure";

    /**
     * {@code NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE} the message key used when the user is not authorized
     * or the requested details are incorrect
     */
    public static final String NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE = "not_authorized";

    /**
     * {@code SERVER_ERROR_MESSAGE} the message key used for an unexpected server error
     */
    public static final String SERVER_ERROR_MESSAGE = "server_error";

    /**
     * {@code UNKNOWN_SERVER_ERROR_MESSAGE} the fallback message key used when an exception message cannot be resolved
     */
    public static final String UNKNOWN_SERVER_ERROR_MESSAGE = "unknown_server_error";

    /**
     * {@code RESPONSE_SUCCESSFUL_MESSAGE} the message key used when the request has been successful
     */
    public static final String RESPONSE_SUCCESSFUL_MESSAGE = "operation_executed_successfully";

    /**
     * {@code WRONG_NAME_MESSAGE} the message key used when the name is not valid
     */
    public static final String WRONG_NAME_MESSAGE = "wrong_name";

    /**
     * {@code WRONG_SURNAME_MESSAGE} the message key used when the surname is not valid
     */
    public static final String WRONG_SURNAME_MESSAGE = "wrong_surname";

    /**
     * {@code WRONG_EMAIL_MESSAGE} the message key used when the email is not valid
     */
    public static final String WRONG_EMAIL_MESSAGE = "wrong_email";

    /**
     * {@code WRONG_PASSWORD_MESSAGE} the message key used when the password is not valid
     */
    public static final String WRONG_PASSWORD_MESSAGE = "wrong_password";

    /**
     * {@code WRONG_LANGUAGE_MESSAGE} the message key used when the language is not supported
     */
    public static final String WRONG_LANGUAGE_MESSAGE = "wrong_language";

}