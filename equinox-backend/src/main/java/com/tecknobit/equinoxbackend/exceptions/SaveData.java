package com.tecknobit.equinoxbackend.exceptions;

/**
 * The {@code SaveData} class is useful to interrupt backend initialization while exposing data to save
 *
 * <p>The server protector uses this exception to disclose a newly generated secret before the
 * backend continues. The exception message includes a save reminder, while
 * {@link #getSaveableContent()} exposes the supplied content
 *
 * @author Tecknobit N7ghtm4r3
 * @see Exception
 *
 * @since 2.0.0
 */
public class SaveData extends Exception {

    /**
     * {@code savableContent} the original content supplied for the administrator to save
     */
    private final Object savableContent;

    /**
     * Constructor to init the {@link SaveData} class
     *
     * <p>The exception message prefixes the supplied content with a reminder to save it
     *
     * @param message The content to retain and include in the exception message
     * @param <T>     The type of the supplied content
     */
    public <T> SaveData(T message) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message);
        savableContent = message;
    }

    /**
     * Constructor to init the {@link SaveData} class with an originating failure
     *
     * <p>The exception message prefixes the supplied content with a reminder to save it
     *
     * @param message The content to retain and include in the exception message
     * @param cause   The originating failure, or {@code null} when unavailable
     * @param <T>     The type of the supplied content
     */
    public <T> SaveData(T message, Throwable cause) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message, cause);
        savableContent = message;
    }

    /**
     * Constructor to init the {@link SaveData} class with configurable exception diagnostics
     *
     * <p>The exception message prefixes the supplied content with a reminder to save it
     *
     * @param message            The content to retain and include in the exception message
     * @param cause              The originating failure, or {@code null} when unavailable
     * @param enableSuppression  Whether suppressed exceptions can be recorded
     * @param writableStackTrace Whether the exception records a writable stack trace
     * @param <T>                The type of the supplied content
     */
    public <T> SaveData(T message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super("Note: is not an error, but is an alert!\nPlease you should safely save: " + message, cause,
                enableSuppression, writableStackTrace);
        savableContent = message;
    }

    /**
     * Method used to get the original content supplied when creating the exception
     *
     * @param <T> The expected content type, which must match the supplied value
     *
     * @return the original content as {@code T}
     */
    public <T> T getSaveableContent() {
        return (T) savableContent;
    }

}