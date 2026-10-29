package com.tecknobit.equinoxbackend.environment.services.builtin.controllers;

import com.tecknobit.equinoxbackend.apis.json.JsonHelper;
import com.tecknobit.equinoxbackend.apis.resources.ResourcesProvider;
import com.tecknobit.equinoxbackend.configuration.ServerProtector;
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration;
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxcore.annotations.Structure;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import com.tecknobit.equinoxcore.network.ResponseStatus;
import jakarta.annotation.Nullable;
import jdk.jfr.Experimental;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_DATA_KEY;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinoxcore.network.ResponseStatus.FAILED;
import static com.tecknobit.equinoxcore.network.ResponseStatus.SUCCESSFUL;

/**
 * The {@code EquinoxControllerCore} class provides logic for features such as {@code i18n},
 * default response messages, and utilities to build responses, enabling controllers
 * to implement APIs that are ready to use and easy to adopt
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.1.7
 */
@Structure
@Experimental
abstract public class EquinoxControllerCore {

    /**
     * {@code resourcesProvider} the resources provider and manager
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static ResourcesProvider resourcesProvider;

    /**
     * {@code WRONG_PROCEDURE_MESSAGE} message to use when the procedure is wrong
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_PROCEDURE_MESSAGE = "wrong_procedure";

    /**
     * {@code NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE} message to use when the request is by a not authorized user or
     * tried to fetch wrong details
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE = "not_authorized";

    /**
     * {@code RESPONSE_SUCCESSFUL_MESSAGE} message to use when the request has been successful
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String RESPONSE_SUCCESSFUL_MESSAGE = "operation_executed_successfully";

    /**
     * {@code WRONG_NAME_MESSAGE} error message used when the name inserted is not valid
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_NAME_MESSAGE = "wrong_name";

    /**
     * {@code WRONG_SURNAME_MESSAGE} error message used when the surname inserted is not valid
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_SURNAME_MESSAGE = "wrong_surname";

    /**
     * {@code WRONG_EMAIL_MESSAGE} error message used when the email inserted is not valid
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_EMAIL_MESSAGE = "wrong_email";

    /**
     * {@code WRONG_PASSWORD_MESSAGE} error message used when the password inserted is not valid
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_PASSWORD_MESSAGE = "wrong_password";

    /**
     * {@code WRONG_LANGUAGE_MESSAGE} error message used when the language inserted is not valid
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static final String WRONG_LANGUAGE_MESSAGE = "wrong_language";

    /**
     * {@code protector} the instance to launch the server protector to manage the server accesses
     *
     * @apiNote the commands scheme:
     * <ul>
     *     <li>
     *         <b>rss</b> -> launch your java application with "rss" to recreate the server secret <br>
     *                       e.g java -jar your_backend.jar rss
     *     </li>
     *     <li>
     *         <b>dss</b> -> launch your java application with "dss" to delete the current server secret <br>
     *                       e.g java -jar your_backend.jar dss
     *     </li>
     *     <li>
     *         <b>dssi</b> -> launch your java application with "dssi" to delete the current server secret and interrupt
     *                        the current workflow of the server <br>
     *                        e.g java -jar your_backend.jar dssi
     *     </li>
     * </ul>
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static ServerProtector serverProtector;

    /**
     * {@code configuration} the current configuration of the Equinox's backend instance
     */
    protected final EquinoxBackendConfiguration configuration;

    /**
     * {@code jsonHelper} helper to work with JSON values
     */
    protected JsonHelper jsonHelper = new JsonHelper("{}");

    /**
     * {@code messageSource} the message source used to manage the resources messages bundle
     */
    @Autowired(required = false)
    protected MessageSource messageSource;

    /**
     * Constructor to instantiate the {@link EquinoxController}
     *
     * @apiNote will be instantiated also the {@link #configuration}
     */
    public EquinoxControllerCore() {
        EquinoxEnvironmentService equinoxEnvironmentService = EquinoxEnvironmentService.getInstance();
        configuration = equinoxEnvironmentService.getBackendConfiguration();
    }

    /**
     * Method used to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     */
    protected void loadJsonHelper(JSONObject payload) {
        jsonHelper.setJSONObjectSource(payload);
    }

    /**
     * Method used to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     */
    protected void loadJsonHelper(JSONArray payload) {
        jsonHelper.setJSONArraySource(payload);
    }

    /**
     * Method used to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     * @param <V>     generic type for the values in the payload
     */
    protected <V> void loadJsonHelper(Map<String, V> payload) {
        jsonHelper.setJSONObjectSource(new JSONObject(payload));
    }

    /**
     * Method used to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     */
    protected void loadJsonHelper(String payload) {
        jsonHelper.setJSONObjectSource(payload);
    }

    /**
     * Method used to change the current locale of the session
     *
     * @param locale The locale of the session to set
     */
    @Wrapper
    protected void setSessionLocale(String locale) {
        setSessionLocale(Locale.forLanguageTag(locale));
    }

    /**
     * Method used to change the current locale of the session
     *
     * @param locale The locale of the session to set
     */
    protected void setSessionLocale(Locale locale) {
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * Method used to assemble a successful response with HTTP status {@code 200} and the localized
     * {@link #RESPONSE_SUCCESSFUL_MESSAGE} message
     *
     * @return the successful response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> successResponse() {
        return successResponse(RESPONSE_SUCCESSFUL_MESSAGE);
    }

    /**
     * Method used to assemble a successful response with HTTP status {@code 200}
     *
     * @param message The message key to resolve using the current locale
     *
     * @return the successful response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> successResponse(String message) {
        return successResponse(message, (Object) null);
    }

    /**
     * Method used to assemble a successful response with HTTP status {@code 200}
     *
     * @param message The message key to resolve using the current locale
     * @param args    The arguments used to format the localized message
     *
     * @return the successful response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> successResponse(String message, Object... args) {
        return plainResponse(SUCCESSFUL, getInternationalizedMessage(message, args));
    }

    /**
     * Method used to assemble a successful response with HTTP status {@code 200}
     *
     * @param value The value to send as response
     * @param <V>   The type of the response data
     *
     * @return the successful response as {@link ResponseEntity} containing a {@link HashMap} with {@link String} keys and {@code V} values
     */
    @Assembler
    protected <V> ResponseEntity<HashMap<String, V>> successResponse(V value) {
        HashMap<String, V> response = new HashMap<>();
        response.put(RESPONSE_DATA_KEY, value);
        response.put(RESPONSE_STATUS_KEY, (V) SUCCESSFUL);
        return ResponseEntity.ok(response);
    }

    /**
     * Method used to assemble a successful response with HTTP status {@code 200}
     *
     * @param message The JSON object to include as the response data
     *
     * @return the successful response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Assembler
    protected ResponseEntity<JSONObject> successResponse(JSONObject message) {
        return plainResponse(SUCCESSFUL, message);
    }

    /**
     * Wrapper method used to assemble a {@link #failedResponse(String)} with the {@link #NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE}
     * message
     *
     * @return the error response as {@link ResponseEntity} of {@link JSONObject}
     *
     * @since 1.1.3
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> notAuthorizedOrWrongDetailsResponse() {
        return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Wrapper method used to assemble a {@link #failedResponse(String)} with the {@link #WRONG_PROCEDURE_MESSAGE} message
     *
     * @return the error response as {@link ResponseEntity} of {@link JSONObject}
     *
     * @since 1.1.3
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> wrongProcedureResponse() {
        return failedResponse(WRONG_PROCEDURE_MESSAGE);
    }

    /**
     * Method used to assemble a failed response with the HTTP status defined by {@link ResponseStatus#FAILED}
     *
     * @param errorKey The key of the error
     *
     * @return the failed response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> failedResponse(String errorKey) {
        return failedResponse(errorKey, null);
    }

    /**
     * Method used to assemble a failed response with the HTTP status defined by {@link ResponseStatus#FAILED}
     *
     * @param errorKey The key of the error
     * @param args     The arguments used to format the localized message, or {@code null} when none are needed
     *
     * @return the failed response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Wrapper
    @Assembler
    protected ResponseEntity<JSONObject> failedResponse(String errorKey, @Nullable Object[] args) {
        return plainResponse(FAILED, getInternationalizedMessage(errorKey, args));
    }

    /**
     * Method used to assemble a response containing the status and the supplied data
     *
     * <p>Successful responses use HTTP status {@code 200}. Other responses use the code provided by
     * {@link ResponseStatus#getCode()}
     *
     * @param status  The response status used in the body and to select the HTTP status
     * @param message The value to include as the response data, preserving JSON objects as nested objects
     *
     * @return the response as {@link ResponseEntity} of {@link JSONObject}
     */
    @Assembler
    protected ResponseEntity<JSONObject> plainResponse(ResponseStatus status, Object message) {
        JSONObject response = new JSONObject();
        response.put(RESPONSE_STATUS_KEY, status);
        response.put(RESPONSE_DATA_KEY, message);

        return resolveResponseFormat(status, response);
    }

    /**
     * Method used to wrap the supplied response body with the HTTP status matching the response status
     *
     * <p>Successful responses use HTTP status {@code 200}. Other responses use the code provided by
     * {@link ResponseStatus#getCode()}
     *
     * @param status   The response status used to select the HTTP status
     * @param response The JSON response body to return
     *
     * @return the response with the selected HTTP status as {@link ResponseEntity} of {@link JSONObject}
     *
     * @since 2.0.0
     */
    @Returner
    protected ResponseEntity<JSONObject> resolveResponseFormat(ResponseStatus status, JSONObject response) {
        if(status == SUCCESSFUL)
            return ResponseEntity.ok(response);
        else {
            ResponseEntity.BodyBuilder responseEntity =  ResponseEntity.status(status.getCode());
            return responseEntity.body(response);
        }
    }

    /**
     * Method used to get the international message
     *
     * @param messageKey The message of the international message
     * @return the internationalized message as {@link String}
     */
    @Wrapper
    @Returner
    protected String getInternationalizedMessage(String messageKey) {
        return getInternationalizedMessage(messageKey, null);
    }

    /**
     * Method used to get the international message
     *
     * @param messageKey The message of the international message
     * @param args       The arguments used to format the international message
     * @return the internationalized message as {@link String}
     */
    @Returner
    protected String getInternationalizedMessage(String messageKey, @Nullable Object[] args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

    /**
     * Method used to generate an identifier of an item
     *
     * @return the identifier as {@link String}
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static String generateIdentifier() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Method used to init the {@link #serverProtector} and create the resources directories correctly
     *
     * @param context The launcher {@link Class} where this method has been invoked
     * @param args    Custom arguments to share with {@link SpringApplication} and with the {@link #serverProtector}
     * @apiNote the arguments scheme:
     * <ul>
     *     <li>
     *         {@link #serverProtector} ->
     *         <ul>
     *          <li>
     *             <b>rss</b> -> launch your java application with "rss" to recreate the server secret <br>
     *                       e.g java -jar your_backend.jar rss
     *             </li>
     *              <li>
     *                  <b>dss</b> -> launch your java application with "dss" to delete the current server secret <br>
     *                       e.g java -jar your_backend.jar dss
     *              </li>
     *              <li>
     *                  <b>dssi</b> -> launch your java application with "dssi" to delete the current server secret and interrupt
     *                        the current workflow of the server <br>
     *                        e.g java -jar your_backend.jar dssi
     *              </li>
     *          </ul>
     *     </li>
     *     <li>
     *         {@link SpringApplication} -> see the allowed arguments <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html">here</a>
     *     </li>
     * </ul>
     * @since 1.1.0
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    @Wrapper
    public static void initEquinoxEnvironment(Class<?> context, String[] args) {
        initEquinoxEnvironment(context, null, args);
    }

    /**
     * Method used to init the {@link #serverProtector} and create the resources directories correctly
     *
     * @param context          The launcher {@link Class} where this method has been invoked
     * @param saveMessageExtra Extra arguments used to format the save message displayed by the {@link #serverProtector}
     * @param args             Custom arguments to share with {@link SpringApplication} and with the {@link #serverProtector}
     * @apiNote the arguments scheme:
     * <ul>
     *     <li>
     *         {@link #serverProtector} ->
     *         <ul>
     *          <li>
     *             <b>rss</b> -> launch your java application with "rss" to recreate the server secret <br>
     *                       e.g java -jar your_backend.jar rss
     *             </li>
     *              <li>
     *                  <b>dss</b> -> launch your java application with "dss" to delete the current server secret <br>
     *                       e.g java -jar your_backend.jar dss
     *              </li>
     *              <li>
     *                  <b>dssi</b> -> launch your java application with "dssi" to delete the current server secret and interrupt
     *                        the current workflow of the server <br>
     *                        e.g java -jar your_backend.jar dssi
     *              </li>
     *          </ul>
     *     </li>
     *     <li>
     *         {@link SpringApplication} -> see the allowed arguments <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html">here</a>
     *     </li>
     * </ul>
     * @since 1.1.2
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public static void initEquinoxEnvironment(Class<?> context, Object[] saveMessageExtra, String[] args) {
        throw new RuntimeException("To use EquinoxEnvironmentService instead");
    }

}
