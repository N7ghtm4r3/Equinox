package com.tecknobit.equinoxbackend.environment.exceptions;

import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.Returner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages.UNKNOWN_SERVER_ERROR_MESSAGE;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_DATA_KEY;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinoxcore.network.ResponseStatus.FAILED;

/**
 * The {@code EquinoxGlobalExceptionHandler} class is useful to convert validation failures
 * and Equinox exceptions into HTTP responses
 *
 * <p>Responses with a message use the Equinox failure envelope. A validation message
 * that cannot be resolved may produce a response without a body
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@RestControllerAdvice
public class EquinoxGlobalExceptionHandler {

    /**
     * {@code messageSource} the message source used to resolve localized response text
     */
    @Autowired
    protected MessageSource messageSource;

    /**
     * Method used to convert the first field validation error into an HTTP 400 response
     *
     * <p>The field default message is resolved as a message key using the current locale
     * and returned in the failure envelope. A missing field message uses the default bad-request
     * exception. An unresolved key produces an empty body when the message source throws
     * {@link NoSuchMessageException}
     *
     * @param exception The validation failure to handle
     *
     * @return the bad-request response as {@link ResponseEntity} of {@link String}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getFieldError();
        String defaultMessage = (fieldError != null) ? fieldError.getDefaultMessage() : null;
        if(defaultMessage == null)
            return handle(new EquinoxBadRequestException());

        try {
            String message = messageSource.getMessage(defaultMessage, null, LocaleContextHolder.getLocale());
            String response = assembleResponse(message);
            return ResponseEntity.badRequest().body(response);
        } catch (NoSuchMessageException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method used to convert an Equinox exception into a localized failure response
     *
     * @param exception The exception supplying the message data and HTTP status
     *
     * @return the failure response as {@link ResponseEntity} of {@link String}
     * @throws NoSuchMessageException if neither the message nor its fallback can be resolved
     * @throws IllegalArgumentException if the selected HTTP status is invalid
     */
    @ExceptionHandler(EquinoxException.class)
    public ResponseEntity<String> handle(EquinoxException exception) {
        String message = getInternationalizedMessage(exception);
        String response = assembleResponse(message);

        ResponseEntity.BodyBuilder exceptionError = resolveExceptionError(exception);
        return exceptionError.body(response);
    }

    /**
     * Method used to select the HTTP response status for an Equinox exception
     *
     * <p>Bad-request and server exceptions use HTTP 400 and 500 respectively.
     * Other exceptions use their stored status code
     *
     * @param exception The exception whose HTTP status is selected
     *
     * @return the response builder as {@link ResponseEntity.BodyBuilder}
     * @throws IllegalArgumentException if the stored HTTP status is invalid
     */
    @Returner
    protected ResponseEntity.BodyBuilder resolveExceptionError(EquinoxException exception) {
        return switch (exception) {
            case EquinoxBadRequestException ignored -> ResponseEntity.badRequest();

            case EquinoxServerException ignored -> ResponseEntity.internalServerError();

            default -> ResponseEntity.status(exception.getCode());
        };
    }

    /**
     * Method used to resolve the exception message with its formatting arguments and locale
     *
     * <p>If the message source throws {@link NoSuchMessageException}, the unknown-server-error
     * key is resolved with the same arguments and locale. A message source configured to return
     * unresolved keys may return the key directly without using this fallback
     *
     * @param exception The exception supplying the message key, arguments, and locale
     *
     * @return the resolved message as {@link String}
     * @throws NoSuchMessageException if the fallback message cannot be resolved
     */
    @Returner
    protected String getInternationalizedMessage(EquinoxException exception) {
        String key = exception.getKey();
        String[] args = exception.getArgs();
        Locale locale = exception.getLocale();

        try {
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException suchMessageException) {
            return messageSource.getMessage(UNKNOWN_SERVER_ERROR_MESSAGE, args, locale);
        }
    }

    /**
     * Method used to assemble the Equinox failure envelope containing the response message
     *
     * @param message The message to include in the failure response
     *
     * @return the serialized failure envelope as {@link String}
     */
    @Assembler
    protected String assembleResponse(String message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, FAILED)
                .put(RESPONSE_DATA_KEY, message)
                .toString();
    }

}