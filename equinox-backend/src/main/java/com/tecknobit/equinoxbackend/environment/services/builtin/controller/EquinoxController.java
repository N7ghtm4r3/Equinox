package com.tecknobit.equinoxbackend.environment.services.builtin.controller;

import com.tecknobit.apimanager.apis.ServerProtector;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinoxbackend.apis.resources.ResourcesProvider;
import com.tecknobit.equinoxbackend.configuration.EquinoxBackendConfiguration;
import com.tecknobit.equinoxbackend.configuration.EquinoxBackendConfiguration.ResourcesConfig;
import com.tecknobit.equinoxbackend.configuration.EquinoxBackendConfiguration.ServerProtectorConfig;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxcore.annotations.Validator;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import com.tecknobit.equinoxcore.network.ResponseStatus;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.tecknobit.equinoxbackend.apis.resources.ResourcesManager.RESOURCES_KEY;
import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_DATA_KEY;
import static com.tecknobit.equinoxcore.network.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinoxcore.network.ResponseStatus.FAILED;
import static com.tecknobit.equinoxcore.network.ResponseStatus.SUCCESSFUL;

/**
 * The {@code EquinoxController} class is useful to give the base behavior of the <b>Equinox's controllers</b>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param <T> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersService} used in the system, is generic to avoid manual casts if it has been customized
 * @since 1.0.1
 */
@RestController
@RequestMapping(BASE_EQUINOX_ENDPOINT)
abstract public class EquinoxController<T extends EquinoxUser, R extends EquinoxUsersRepository<T>,
        H extends EquinoxUsersService<T, R>> {

    /**
     * {@code resourcesProvider} the resources provider and manager
     */
    public static ResourcesProvider resourcesProvider;

    /**
     * {@code WRONG_PROCEDURE_MESSAGE} message to use when the procedure is wrong
     */
    public static final String WRONG_PROCEDURE_MESSAGE = "wrong_procedure";

    /**
     * {@code NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE} message to use when the request is by a not authorized user or
     * tried to fetch wrong details
     */
    public static final String NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE = "not_authorized";

    /**
     * {@code RESPONSE_SUCCESSFUL_MESSAGE} message to use when the request has been successful
     */
    public static final String RESPONSE_SUCCESSFUL_MESSAGE = "operation_executed_successfully";

    /**
     * {@code WRONG_NAME_MESSAGE} error message used when the name inserted is not valid
     */
    public static final String WRONG_NAME_MESSAGE = "wrong_name";

    /**
     * {@code WRONG_SURNAME_MESSAGE} error message used when the surname inserted is not valid
     */
    public static final String WRONG_SURNAME_MESSAGE = "wrong_surname";

    /**
     * {@code WRONG_EMAIL_MESSAGE} error message used when the email inserted is not valid
     */
    public static final String WRONG_EMAIL_MESSAGE = "wrong_email";

    /**
     * {@code WRONG_PASSWORD_MESSAGE} error message used when the password inserted is not valid
     */
    public static final String WRONG_PASSWORD_MESSAGE = "wrong_password";

    /**
     * {@code WRONG_LANGUAGE_MESSAGE} error message used when the language inserted is not valid
     */
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
     * {@code usersRepository} instance for the user repository
     */
    @Autowired(required = false)
    protected R usersRepository;

    /**
     * {@code me} user representing the user who made a request on the server
     */
    protected T me;

    /**
     * Constructor to instantiate the {@link EquinoxController}
     *
     * @apiNote will be instantiated also the {@link #configuration}
     */
    public EquinoxController() {
        configuration = EquinoxBackendConfiguration.getInstance();
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
     * @param <V>      generic type for the values in the payload
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
     * Method used to check whether the user who made a request is an authorized user <br>
     * If the user is authorized the {@link #me} instance is loaded
     *
     * @param id The identifier of the user
     * @param token The token of the user
     * @return whether the user is an authorized user as boolean
     */
    @Validator
    protected boolean isMe(String id, String token) {
        Optional<T> query = usersRepository.findById(id);
        me = query.orElse(null);
        boolean isMe = me != null && me.getToken().equals(token);
        if (!isMe) {
            me = null;
            setSessionLocale(DEFAULT_LANGUAGE);
        } else
            setSessionLocale(me.getLanguage());
        return isMe;
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
     * Method used to get the payload for a successful response 
     *
     * @return the payload for a successful response as {@link String}
     */
    @Wrapper
    @Assembler
    protected String successResponse() {
        return successResponse(RESPONSE_SUCCESSFUL_MESSAGE);
    }

    /**
     * Method used to get the payload for a successful response
     *
     * @param message The message to send as response
     * @return the payload for a successful response as {@link String}
     */
    @Wrapper
    @Assembler
    protected String successResponse(String message) {
        return successResponse(message, (Object) null);
    }

    /**
     * Method used to get the payload for a successful response
     *
     * @param message The message to send as response
     * @return the payload for a successful response as {@link String}
     */
    @Wrapper
    @Assembler
    protected String successResponse(String message, Object... args) {
        return plainResponse(SUCCESSFUL, getInternationalizedMessage(message, args));
    }

    /**
     * Method used to get the payload for a successful response
     *
     * @param value The value to send as response
     * @param <V>    generic type for the values in the payload
     * @return the payload for a successful response as {@link HashMap} of {@link V}
     */
    @Assembler
    protected <V> HashMap<String, V> successResponse(V value) {
        HashMap<String, V> response = new HashMap<>();
        response.put(RESPONSE_DATA_KEY, value);
        response.put(RESPONSE_STATUS_KEY, (V) SUCCESSFUL);
        return response;
    }

    /**
     * Method used to get the payload for a successful response
     *
     * @param message The message to send as response
     * @return the payload for a successful response as {@link String}
     */
    @Assembler
    protected String successResponse(JSONObject message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, SUCCESSFUL)
                .put(RESPONSE_DATA_KEY, message).toString();
    }

    /**
     * Wrapper method used to assemble a {@link #failedResponse(String)} with the {@link #NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE}
     * message
     *
     * @return the error response as {@link String}
     * @since 1.1.3
     */
    @Wrapper
    @Assembler
    protected String notAuthorizedOrWrongDetailsResponse() {
        return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Wrapper method used to assemble a {@link #failedResponse(String)} with the {@link #WRONG_PROCEDURE_MESSAGE} message
     *
     * @return the error response as {@link String}
     * @since 1.1.3
     */
    @Wrapper
    @Assembler
    protected String wrongProcedureResponse() {
        return failedResponse(WRONG_PROCEDURE_MESSAGE);
    }

    /**
     * Method used to get the payload for a failed response
     *
     * @param errorKey The key of the error
     * @return the payload for a failed response as {@link String}
     */
    @Wrapper
    @Assembler
    protected String failedResponse(String errorKey) {
        return failedResponse(errorKey, null);
    }

    /**
     * Method used to get the payload for a failed response
     *
     * @param errorKey The key of the error
     * @param args     The arguments used to format the response
     * @return the payload for a failed response as {@link String}
     */
    @Wrapper
    @Assembler
    protected String failedResponse(String errorKey, @Nullable Object[] args) {
        return plainResponse(FAILED, getInternationalizedMessage(errorKey, args));
    }

    /**
     * Method used to assemble the payload for a response
     *
     * @param status  The response code value
     * @param message The message to send as response
     * @return the payload for a response as {@link String}
     */
    protected String plainResponse(ResponseStatus status, String message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, status)
                .put(RESPONSE_DATA_KEY, message).toString();
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
     * @param args The arguments used to format the international message
     *
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
    @Wrapper
    public static void initEquinoxEnvironment(Class<?> context, String[] args) {
        initEquinoxEnvironment(context, null, args);
    }

    /**
     * Method used to init the {@link #serverProtector} and create the resources directories correctly
     *
     * @param context The launcher {@link Class} where this method has been invoked
     * @param saveMessageExtra Extra arguments used to format the save message displayed by the {@link #serverProtector}
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
     * @since 1.1.2
     */
    public static void initEquinoxEnvironment(Class<?> context, Object[] saveMessageExtra, String[] args) {
        try {
            EquinoxBackendConfiguration backendConfiguration = EquinoxBackendConfiguration.getInstance();
            ResourcesConfig resourcesConfig = backendConfiguration.getResourcesConfig();
            if (resourcesConfig.createResourcesFolder()) {
                resourcesProvider = new ResourcesProvider(RESOURCES_KEY, resourcesConfig.getSubdirectories());
                resourcesProvider.createContainerDirectory();
                resourcesProvider.createSubDirectories();
            }
            if (backendConfiguration.serverProtectorEnabled()) {
                if (serverProtector != null)
                    throw new IllegalAccessException("The protector has been already instantiated");
                ServerProtectorConfig serverProtectorConfig = backendConfiguration.getServerProtectorConfig();
                serverProtector = new ServerProtector(
                        serverProtectorConfig.getStoragePath(),
                        serverProtectorConfig.getSaveMessage(saveMessageExtra)
                );
                serverProtector.launch(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
