package com.tecknobit.equinoxbackend.environment.exceptions;

import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Returner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_DATA_KEY;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinoxcore.network.ResponseStatus.FAILED;

@RestControllerAdvice
@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - More general purpose behavior if necessary
                - To docu in a dedicated section
                """
)
public class EquinoxGlobalExceptionHandler {

    @Autowired
    protected MessageSource messageSource;

    @ExceptionHandler(EquinoxBadRequestException.class)
    public ResponseEntity<String> handle(EquinoxBadRequestException exception) {
        String message = getInternationalizedMessage(exception);
        String response = assembleResponse(message);

        return ResponseEntity.badRequest().body(response);
    }

    // TODO: 24/04/2026 CHECK TO IMPROVE INTEGRATION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getFieldError();
        String message = messageSource.getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale());
        String response = assembleResponse(message);

        return ResponseEntity.badRequest().body(response);
    }

    @Returner
    protected String getInternationalizedMessage(EquinoxBadRequestException exception) {
        String key = exception.getKey();
        String[] args = exception.getArgs();
        Locale locale = exception.getLocale();

        return messageSource.getMessage(key, args, locale);
    }

    @Assembler
    // TODO: 23/04/2026 TO USE EquinoxControllerCore.notAuthorizedOrWrongDetailsResponse() METHOD LIKE INSTEAD TO KEEP
    //  RESPONSE IN STANDARD EQUINOX FORMAT AND COMMONIZE COMMON CODE
    private String assembleResponse(String message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, FAILED)
                .put(RESPONSE_DATA_KEY, message).toString();
    }

    @FutureEquinoxApi(
            releaseVersion = "2.0.0",
            additionalNotes = """
                    - More general purpose behavior if necessary
                    - To docu in a dedicated section
                    - Think about a hierarchy like BadRequest Forbidden, ServerError etc
                    """
    )
    public static class EquinoxBadRequestException extends RuntimeException {

        private final String key;

        private final String[] args;

        private final Locale locale;

        public EquinoxBadRequestException(String key) {
            this(key, null, LocaleContextHolder.getLocale());
        }

        public EquinoxBadRequestException(String key, Locale locale) {
            this(key, null, locale);
        }

        public EquinoxBadRequestException(String key, String[] args) {
            this(key, args, LocaleContextHolder.getLocale());
        }

        public EquinoxBadRequestException(String key, String[] args, Locale locale) {
            this.key = key;
            this.args = args;
            this.locale = locale;
        }

        public String getKey() {
            return key;
        }

        public String[] getArgs() {
            return args;
        }

        public Locale getLocale() {
            return locale;
        }

    }

}