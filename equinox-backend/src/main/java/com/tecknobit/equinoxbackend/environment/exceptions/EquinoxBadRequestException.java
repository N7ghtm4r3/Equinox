package com.tecknobit.equinoxbackend.environment.exceptions;

import java.util.Locale;

import static com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages.WRONG_PROCEDURE_MESSAGE;

/**
 * The {@code EquinoxBadRequestException} class is useful to represent a rejected request with HTTP status 400
 *
 * <p>Constructors without a message key use
 * {@link com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages#WRONG_PROCEDURE_MESSAGE}. Constructors
 * without a locale capture the current {@link org.springframework.context.i18n.LocaleContextHolder} locale
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxException
 *
 * @since 2.0.0
 */
public class EquinoxBadRequestException extends EquinoxException {

    /**
     * {@code BAD_REQUEST_EXCEPTION_CODE} the HTTP status code associated with this exception
     */
    public static final int BAD_REQUEST_EXCEPTION_CODE = 400;

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     */
    public EquinoxBadRequestException() {
        this(WRONG_PROCEDURE_MESSAGE);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param key The message key to resolve
     */
    public EquinoxBadRequestException(String key) {
        super(key, BAD_REQUEST_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param locale The locale used to resolve the message
     */
    public EquinoxBadRequestException(Locale locale) {
        this(WRONG_PROCEDURE_MESSAGE, locale);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param key    The message key to resolve
     * @param locale The locale used to resolve the message
     */
    public EquinoxBadRequestException(String key, Locale locale) {
        super(key, locale, BAD_REQUEST_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param args The message formatting arguments, or {@code null} when none are required
     */
    public EquinoxBadRequestException(String[] args) {
        super(WRONG_PROCEDURE_MESSAGE, args, BAD_REQUEST_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param key  The message key to resolve
     * @param args The message formatting arguments, or {@code null} when none are required
     */
    public EquinoxBadRequestException(String key, String[] args) {
        super(key, args, BAD_REQUEST_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxBadRequestException} class
     *
     * @param key    The message key to resolve
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     */
    public EquinoxBadRequestException(String key, String[] args, Locale locale) {
        super(key, args, locale, BAD_REQUEST_EXCEPTION_CODE);
    }

}
