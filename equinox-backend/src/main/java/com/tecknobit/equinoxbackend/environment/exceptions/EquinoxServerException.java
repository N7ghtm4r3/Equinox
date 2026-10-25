package com.tecknobit.equinoxbackend.environment.exceptions;

import java.util.Locale;

import static com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages.SERVER_ERROR_MESSAGE;

/**
 * The {@code EquinoxServerException} class is useful to represent a server failure with HTTP status 500
 *
 * <p>Constructors without a message key use
 * {@link com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages#SERVER_ERROR_MESSAGE}. Constructors
 * without a locale capture the current {@link org.springframework.context.i18n.LocaleContextHolder} locale
 *
 * <p>The supplied failure is retained as the cause returned by {@link #getCause()}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxException
 *
 * @since 2.0.0
 */
public class EquinoxServerException extends EquinoxException {

    /**
     * {@code SERVER_EXCEPTION_CODE} the HTTP status code associated with this exception
     */
    public static final int SERVER_EXCEPTION_CODE = 500;

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(Throwable throwable) {
        this(SERVER_ERROR_MESSAGE, throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param key The message key to resolve
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String key, Throwable throwable) {
        super(key, SERVER_EXCEPTION_CODE);
        initCause(throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param locale The locale used to resolve the message
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(Locale locale, Throwable throwable) {
        this(SERVER_ERROR_MESSAGE, locale, throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param key    The message key to resolve
     * @param locale The locale used to resolve the message
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String key, Locale locale, Throwable throwable) {
        super(key, locale, SERVER_EXCEPTION_CODE);
        initCause(throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param args The message formatting arguments, or {@code null} when none are required
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String[] args, Throwable throwable) {
        super(SERVER_ERROR_MESSAGE, args, SERVER_EXCEPTION_CODE);
        initCause(throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param key  The message key to resolve
     * @param args The message formatting arguments, or {@code null} when none are required
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String key, String[] args, Throwable throwable) {
        super(key, args, SERVER_EXCEPTION_CODE);
        initCause(throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String[] args, Locale locale, Throwable throwable) {
        this(SERVER_ERROR_MESSAGE, args, locale, throwable);
    }

    /**
     * Constructor to init the {@link EquinoxServerException} class
     *
     * @param key    The message key to resolve
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     * @param throwable The original failure retained as the cause, or {@code null} when unavailable
     */
    public EquinoxServerException(String key, String[] args, Locale locale, Throwable throwable) {
        super(key, args, locale, SERVER_EXCEPTION_CODE);
        initCause(throwable);
    }

}
