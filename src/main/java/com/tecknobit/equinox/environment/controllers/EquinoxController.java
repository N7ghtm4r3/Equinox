package com.tecknobit.equinox.environment.controllers;

import com.tecknobit.apimanager.apis.ServerProtector;
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinox.environment.helpers.services.repositories.EquinoxUsersRepository;
import com.tecknobit.equinox.environment.records.EquinoxUser;
import com.tecknobit.mantis.Mantis;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.FAILED;
import static com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.SUCCESSFUL;
import static com.tecknobit.equinox.Requester.RESPONSE_MESSAGE_KEY;
import static com.tecknobit.equinox.Requester.RESPONSE_STATUS_KEY;
import static com.tecknobit.equinox.environment.helpers.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;
import static com.tecknobit.equinox.inputs.InputValidator.DEFAULT_LANGUAGE;

/**
 * The {@code EquinoxController} class is useful to give the base behavior of the <b>Equinox's controllers</b>
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@RestController
@RequestMapping(BASE_EQUINOX_ENDPOINT)
abstract public class EquinoxController {

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
    public static ServerProtector serverProtector;

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
    @Autowired
    protected EquinoxUsersRepository usersRepository;

    /**
     * {@code jsonHelper} helper to work with JSON values
     */
    protected JsonHelper jsonHelper = new JsonHelper("{}");

    /**
     * {@code me} user representing the user who made a request on the server
     */
    protected EquinoxUser me;

    /**
     * Constructor to init the {@link EquinoxController} class <br>
     * <p>
     * No-any params required
     */
    public EquinoxController() {
        if (serverProtector == null) {
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
     * @param payload: the payload received with the request
     * @param <V>      generic type for the values in the payload
     */
    protected <V> void loadJsonHelper(Map<String, V> payload) {
        jsonHelper.setJSONObjectSource(new JSONObject(payload));
    }

    /**
     * Method to load the {@link #jsonHelper}
     *
     * @param payload: the payload received with the request
     */
    protected void loadJsonHelper(String payload) {
        jsonHelper.setJSONObjectSource(payload);
    }

    /**
     * Method to check whether the user who made a request is an authorized user <br>
     * If the user is authorized the {@link #me} instance is loaded
     *
     * @param id:    the identifier of the user
     * @param token: the token of the user
     * @return whether the user is an authorized user as boolean
     */
    protected boolean isMe(String id, String token) {
        Optional<EquinoxUser> query = usersRepository.findById(id);
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
     * @param value: the value to send as response
     * @param <V>    generic type for the values in the payload
     * @return the payload for a successful response as {@link HashMap} of {@link V}
     */
    protected <V> HashMap<String, V> successResponse(V value) {
        HashMap<String, V> response = new HashMap<>();
        response.put(RESPONSE_MESSAGE_KEY, value);
        response.put(RESPONSE_STATUS_KEY, (V) SUCCESSFUL);
        return response;
    }

    /**
     * Method to get the payload for a successful response
     *
     * @param message: the message to send as response
     * @return the payload for a successful response as {@link String}
     */
    protected String successResponse(JSONObject message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, SUCCESSFUL)
                .put(RESPONSE_MESSAGE_KEY, message).toString();
    }

    /**
     * Method to get the payload for a failed response
     *
     * @param error: the error message to send as response
     * @return the payload for a failed response as {@link String}
     */
    protected String failedResponse(String error) {
        return plainResponse(FAILED, mantis.getResource(error));
    }

    /**
     * Method to assemble the payload for a response
     *
     * @param responseCode: the response code value
     * @param message:      the message to send as response
     * @return the payload for a response as {@link String}
     */
    private String plainResponse(StandardResponseCode responseCode, String message) {
        return new JSONObject()
                .put(RESPONSE_STATUS_KEY, responseCode)
                .put(RESPONSE_MESSAGE_KEY, message).toString();
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

}
