package com.tecknobit.equinoxbackend.environment.exceptions;

import java.util.Locale;

/**
 * The {@code EquinoxFormException} class is useful to report invalid form input with HTTP status 427
 *
 * <p>The message key, arguments, and locale are resolved by {@link EquinoxGlobalExceptionHandler}
 * when converting the exception into a failure response
 *
 * @see EquinoxException
 *
 * @since 2.0.0
 */
public class EquinoxFormException extends EquinoxException {
    
    /**
     * {@code FORM_EXCEPTION_CODE} the HTTP status code associated with invalid form input
     */
    public static final int FORM_EXCEPTION_CODE = 427;

    /**
     * Constructor to init the {@link EquinoxFormException} class
     *
     * @param key The message key to resolve
     */
    public EquinoxFormException(String key) {
        super(key, FORM_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxFormException} class
     *
     * @param key    The message key to resolve
     * @param locale The locale used to resolve the message
     */
    public EquinoxFormException(String key, Locale locale) {
        super(key, locale, FORM_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxFormException} class
     *
     * @param key  The message key to resolve
     * @param args The message formatting arguments, or {@code null} when none are required
     */
    public EquinoxFormException(String key, String[] args) {
        super(key, args, FORM_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxFormException} class
     *
     * @param key    The message key to resolve
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     */
    public EquinoxFormException(String key, String[] args, Locale locale) {
        super(key, args, locale, FORM_EXCEPTION_CODE);
    }
    
}
