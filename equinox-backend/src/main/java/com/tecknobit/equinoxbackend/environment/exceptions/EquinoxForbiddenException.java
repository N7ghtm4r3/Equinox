package com.tecknobit.equinoxbackend.environment.exceptions;

import java.util.Locale;

import static com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages.NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE;

/**
 * The {@code EquinoxForbiddenException} class is useful to represent a forbidden operation with HTTP status 403
 *
 * <p>Constructors without a message key use
 * {@link com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages#NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE}. Constructors
 * without a locale capture the current {@link org.springframework.context.i18n.LocaleContextHolder} locale
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxException
 *
 * @since 2.0.0
 */
public class EquinoxForbiddenException extends EquinoxException {

    /**
     * {@code FORBIDDEN_EXCEPTION_CODE} the HTTP status code associated with this exception
     */
    public static final int FORBIDDEN_EXCEPTION_CODE = 403;

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     */
    public EquinoxForbiddenException() {
        this(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param key The message key to resolve
     */
    public EquinoxForbiddenException(String key) {
        super(key, FORBIDDEN_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param locale The locale used to resolve the message
     */
    public EquinoxForbiddenException(Locale locale) {
        this(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE, locale);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param key    The message key to resolve
     * @param locale The locale used to resolve the message
     */
    public EquinoxForbiddenException(String key, Locale locale) {
        super(key, locale, FORBIDDEN_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param args The message formatting arguments, or {@code null} when none are required
     */
    public EquinoxForbiddenException(String[] args) {
        super(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE, args, FORBIDDEN_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param key  The message key to resolve
     * @param args The message formatting arguments, or {@code null} when none are required
     */
    public EquinoxForbiddenException(String key, String[] args) {
        super(key, args, FORBIDDEN_EXCEPTION_CODE);
    }

    /**
     * Constructor to init the {@link EquinoxForbiddenException} class
     *
     * @param key    The message key to resolve
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     */
    public EquinoxForbiddenException(String key, String[] args, Locale locale) {
        super(key, args, locale, FORBIDDEN_EXCEPTION_CODE);
    }

}
