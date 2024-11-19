package com.tecknobit.equinoxbackend.environment.services.builtin.controller;

import com.tecknobit.apimanager.apis.ServerProtector;
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinoxbackend.configurationsutils.ConfigsGenerator;
import com.tecknobit.equinoxbackend.environment.records.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersHelper;
import com.tecknobit.equinoxbackend.resourcesutils.ResourcesProvider;
import com.tecknobit.mantis.Mantis;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.FAILED;
import static com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.SUCCESSFUL;
import static com.tecknobit.equinoxbackend.Requester.RESPONSE_DATA_KEY;
import static com.tecknobit.equinoxbackend.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinoxbackend.environment.helpers.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;
import static com.tecknobit.equinoxbackend.resourcesutils.ResourcesManager.PROFILES_DIRECTORY;
import static com.tecknobit.equinoxbackend.resourcesutils.ResourcesManager.RESOURCES_KEY;
import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

/**
 * The {@code EquinoxController} class is useful to give the base behavior of the <b>Equinox's controllers</b>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param <T> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersHelper} used in the system, is generic to avoid manual casts if it has been customized
 * @since 1.0.1
 */
@RestController
@RequestMapping(BASE_EQUINOX_ENDPOINT)
abstract public class EquinoxController<T extends EquinoxUser, R extends EquinoxUsersRepository<T>,
        H extends EquinoxUsersHelper<T, R>> {

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
     * {@code resourcesProvider} the resources provider and manager
     */
    public static ResourcesProvider resourcesProvider;

    /**
     * {@code mantis} the translations manager
     */
    protected static final Mantis mantis;

    static {
        try {
            mantis = new Mantis(DEFAULT_LANGUAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code WRONG_PROCEDURE_MESSAGE} message to use when the procedure is wrong
     */
    public static final String WRONG_PROCEDURE_MESSAGE = "wrong_procedure_key";

    /**
     * {@code NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE} message to use when the request is by a not authorized user or
     * tried to fetch wrong details
     */
    public static final String NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE = "not_authorized_key";

    /**
     * {@code RESPONSE_SUCCESSFUL_MESSAGE} message to use when the request has been successful
     */
    public static final String RESPONSE_SUCCESSFUL_MESSAGE = "operation_executed_successfully_key";

    /**
     * {@code usersRepository} instance for the user repository
     */
    @Autowired(required = false)
    protected R usersRepository;

    /**
     * {@code jsonHelper} helper to work with JSON values
     */
    protected JsonHelper jsonHelper = new JsonHelper("{}");

    /**
     * {@code me} user representing the user who made a request on the server
     */
    protected T me;

    /**
     * Method to check if the status of the environment has been set up correctly
     * based on the use or not of the {@link #usersRepository} <br>
     *
     * No-any params required
     */
    @PostConstruct
    private void checkEnvironmentStatus() {
        if (usersRepository != null && serverProtector == null) {
            try {
                throw new Exception("The server protector must be initialized first");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     * @param <V>      generic type for the values in the payload
     */
    protected <V> void loadJsonHelper(Map<String, V> payload) {
        jsonHelper.setJSONObjectSource(new JSONObject(payload));
    }

    /**
     * Method to load the {@link #jsonHelper}
     *
     * @param payload The payload received with the request
     */
    protected void loadJsonHelper(String payload) {
        jsonHelper.setJSONObjectSource(payload);
    }

    /**
     * Method to check whether the user who made a request is an authorized user <br>
     * If the user is authorized the {@link #me} instance is loaded
     *
     * @param id:    the identifier of the user
     * @param token The token of the user
     * @return whether the user is an authorized user as boolean
     */
    protected boolean isMe(String id, String token) {
        Optional<T> query = usersRepository.findById(id);
        me = query.orElse(null);
        boolean isMe = me != null && me.getToken().equals(token);
        if (!isMe) {
            me = null;
            mantis.changeCurrentLocale(DEFAULT_LANGUAGE);
        } else
            mantis.changeCurrentLocale(me.getLanguage());
        return isMe;
    }

    /**
     * Method to get the payload for a successful response <br>
     * No-any params required
     *
     * @return the payload for a successful response as {@link String}
     */
    protected String successResponse() {
        return plainResponse(SUCCESSFUL, mantis.getResource(RESPONSE_SUCCESSFUL_MESSAGE));
    }

    /**
     * Method to get the payload for a successful response
     *
     * @param value The value to send as response
     * @param <V>    generic type for the values in the payload
     * @return the payload for a successful response as {@link HashMap} of {@link V}
     */
    protected <V> HashMap<String, V> successResponse(V value) {
        HashMap<String, V> response = new HashMap<>();
        response.put(RESPONSE_DATA_KEY, value);
        response.put(RESPONSE_STATUS_KEY, (V) SUCCESSFUL);
        return response;
    }

    /**
     * Method to get the payload for a successful response
     *
     * @param message The message to send as response
     * @return the payload for a successful response as {@link String}
     */
    protected String successResponse(JSONObject message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, SUCCESSFUL)
                .put(RESPONSE_DATA_KEY, message).toString();
    }

    /**
     * Method to get the payload for a successful response
     *
     * @param message The message to send as response
     * @return the payload for a successful response as {@link String}
     */
    protected String successResponse(String message) {
        return plainResponse(SUCCESSFUL, mantis.getResource(message));
    }

    /**
     * Method to get the payload for a failed response
     *
     * @param error The error message to send as response
     * @return the payload for a failed response as {@link String}
     */
    protected String failedResponse(String error) {
        return plainResponse(FAILED, mantis.getResource(error));
    }

    /**
     * Method to assemble the payload for a response
     *
     * @param responseCode The response code value
     * @param message The message to send as response
     * @return the payload for a response as {@link String}
     */
    private String plainResponse(StandardResponseCode responseCode, String message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, responseCode)
                .put(RESPONSE_DATA_KEY, message).toString();
    }

    /**
     * Method to generate an identifier of an item <br>
     * No-any params required
     *
     * @return the identifier as {@link String}
     */
    public static String generateIdentifier() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Method to init the {@link #serverProtector} and create the resources directories correctly
     *
     * @param storagePath Instance to manage the storage of the server secret
     * @param saveMessage The message to print when the server secret has been generated,
     *                     the start of the message is <b>"Note: is not an error, but is an alert!
     *                     Please you should safely save: server_secret_token_generated"</b>
     * @param context The launcher {@link Class} where this method has been invoked
     * @param args Custom arguments to share with {@link SpringApplication} and with the {@link #serverProtector}
     * @param customSubDirectories The custom subdirectories of the user
     *
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
     */
    public static void initEquinoxEnvironment(String storagePath, String saveMessage, Class<?> context, String[] args,
                                              String... customSubDirectories) {
        try {
            if (serverProtector == null) {
                serverProtector = new ServerProtector(storagePath, saveMessage);
                serverProtector.launch(args);
                setBasicResourcesConfiguration(context, customSubDirectories);
            } else
                throw new IllegalAccessException("The protector has been already instantiated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to configure the resources folders required by the Equinox's environment and the beans classes to
     * correctly serve the static resources and set the CORS policy
     *
     * @param context The launcher {@link Class} where this method has been invoked
     * @param customSubDirectories The custom subdirectories of the user
     * @throws IOException when an error during the creation of the files occurred
     */
    private static void setBasicResourcesConfiguration(Class<?> context, String... customSubDirectories) throws IOException {
        List<String> subDirectories = new ArrayList<>(Arrays.stream(customSubDirectories).toList());
        subDirectories.add(PROFILES_DIRECTORY);
        resourcesProvider = new ResourcesProvider(RESOURCES_KEY, subDirectories);
        resourcesProvider.createContainerDirectory();
        resourcesProvider.createSubDirectories();
        ConfigsGenerator configsGenerator = new ConfigsGenerator(context);
        configsGenerator.createResourcesConfigFile(resourcesProvider.getContainers());
        configsGenerator.crateCorsAdviceFile();
    }

}