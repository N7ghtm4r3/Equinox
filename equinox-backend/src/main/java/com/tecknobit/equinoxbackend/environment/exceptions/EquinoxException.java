package com.tecknobit.equinoxbackend.environment.exceptions;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * The {@code EquinoxException} class is useful to carry a message key, formatting arguments, locale, and HTTP status
 *
 * <p>The message is resolved by {@link EquinoxGlobalExceptionHandler} from the stored key
 * and arguments. Constructors without a locale capture the current
 * {@link LocaleContextHolder} locale. The HTTP status is stored without validation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see RuntimeException
 *
 * @since 2.0.0
 */
public class EquinoxException extends RuntimeException {

    /**
     * {@code key} the message key used to resolve the response text
     */
    protected final String key;

    /**
     * {@code args} the message formatting arguments, or {@code null} when none are supplied
     */
    protected final String[] args;

    /**
     * {@code locale} the locale used to resolve the response text
     */
    protected final Locale locale;

    /**
     * {@code code} the HTTP status code associated with the exception
     */
    protected final int code;

    /**
     * Constructor to init the {@link EquinoxException} class
     *
     * @param key  The message key to resolve
     * @param code The HTTP status code to associate with the exception
     */
    public EquinoxException(String key, int code) {
        this(key, null, LocaleContextHolder.getLocale(), code);
    }

    /**
     * Constructor to init the {@link EquinoxException} class
     *
     * @param key    The message key to resolve
     * @param locale The locale used to resolve the message
     * @param code   The HTTP status code to associate with the exception
     */
    public EquinoxException(String key, Locale locale, int code) {
        this(key, null, locale, code);
    }

    /**
     * Constructor to init the {@link EquinoxException} class
     *
     * @param key  The message key to resolve
     * @param args The message formatting arguments, or {@code null} when none are required
     * @param code The HTTP status code to associate with the exception
     */
    public EquinoxException(String key, String[] args, int code) {
        this(key, args, LocaleContextHolder.getLocale(), code);
    }

    /**
     * Constructor to init the {@link EquinoxException} class
     *
     * @param key    The message key to resolve
     * @param args   The message formatting arguments, or {@code null} when none are required
     * @param locale The locale used to resolve the message
     * @param code   The HTTP status code to associate with the exception
     */
    public EquinoxException(String key, String[] args, Locale locale, int code) {
        this.key = key;
        this.args = args;
        this.locale = locale;
        this.code = code;
    }

    /**
     * Method used to get the {@link #key} instance
     *
     * @return the message key as {@link String}
     */
    public String getKey() {
        return key;
    }

    /**
     * Method used to get the {@link #args} instance
     *
     * @return the message formatting arguments, or {@code null} when absent as {@link String[]}
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Method used to get the {@link #locale} instance
     *
     * @return the message locale as {@link Locale}
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Method used to get the {@link #code} instance
     *
     * @return the HTTP status code as {@code int}
     */
    public int getCode() {
        return code;
    }

}
