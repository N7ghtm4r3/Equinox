package com.tecknobit.equinoxbackend.environment.services.users.controller;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.apimanager.apis.ServerProtector.SERVER_SECRET_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;
import static com.tecknobit.equinoxcore.helpers.InputsValidator.Companion;
import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.*;

/**
 * The {@code EquinoxUsersController} class is useful to manage all the user operations
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxController
 *
 * @param <T> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersService} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @since 1.0.1
 */
@RestController
public class EquinoxUsersController<T extends EquinoxUser, R extends EquinoxUsersRepository<T>,
        H extends EquinoxUsersService<T, R>> extends EquinoxController<T, R, H> {

    /**
     * {@code usersService} helper to manage the users database operations
     */
    protected final H usersService;

    /**
     * Constructor to init the controller
     *
     * @param usersService The helper to manage the users database operations
     */
    @Autowired
    public EquinoxUsersController(H usersService) {
        this.usersService = usersService;
    }

    /**
     * Method used to sign up in the <b>Equinox's system</b>
     *
     * @param payload Payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "server_secret" : "the secret of the server" -> [String],
     *                                  "name" : "the name of the user" -> [String],
     *                                  "surname": "the surname of the user" -> [String],
     *                                  "email": "the email of the user" -> [String],
     *                                  "password": "the password of the user" -> [String],
     *                                  "language": "the language of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PostMapping(path = SIGN_UP_ENDPOINT)
    @RequestPath(path = "/api/v1/users/signUp", method = POST)
    public String signUp(@RequestBody Map<String, Object> payload) {
        loadJsonHelper(payload);
        setSessionLocale(jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE));
        if (configuration.serverProtectorEnabled() && !serverProtector.serverSecretMatches(jsonHelper.getString(SERVER_SECRET_KEY)))
            return notAuthorizedOrWrongDetailsResponse();
        String name = jsonHelper.getString(NAME_KEY);
        String surname = jsonHelper.getString(SURNAME_KEY);
        String email = jsonHelper.getString(EMAIL_KEY);
        String password = jsonHelper.getString(PASSWORD_KEY);
        String language = jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
        setSessionLocale(language);
        Object[] custom = getSignUpCustomParams();
        String signUpValidation = validateSignUp(name, surname, email, password, language, custom);
        if (signUpValidation != null)
            return failedResponse(signUpValidation);
        try {
            JSONObject response = new JSONObject();
            String userId = generateIdentifier();
            String token = generateIdentifier();
            usersService.signUpUser(
                    userId,
                    token,
                    name,
                    surname,
                    email.toLowerCase(),
                    password,
                    language,
                    custom
            );
            setSessionLocale(DEFAULT_LANGUAGE);
            return successResponse(response
                    .put(USER_IDENTIFIER_KEY, userId)
                    .put(TOKEN_KEY, token)
                    .put(PROFILE_PIC_KEY, DEFAULT_PROFILE_PIC)
            );
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to get the list of the custom parameters of a custom {@link EquinoxUser} from the payload of the {@link #signUp(Map)}
     * method
     *
     * @return the custom parameters as array of {@link Object}
     * @apiNote to get the parameters from the payload you have to use the {@link #jsonHelper} instance previously loaded
     * by the {@link #signUp(Map)} method:
     * <pre>
     *     {@code
     *          protected Object[] getSignUpCustomParams() {
     *              return new Object[]{jsonHelper.getString("anything")};
     *          }
     *     }
     * </pre>
     * @implNote as default will be returned an empty array, so that means no customized user has being used
     */
    protected Object[] getSignUpCustomParams() {
        return new Object[0];
    }

    /**
     * Method used to validate the inputs of the {@link #signUp(Map)} method to correctly execute a sign-up operation
     *
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the {@link EquinoxUser} to execute a customized
     *             sign up validation
     *
     * @return the key of the error if any inputs is wrong, null otherwise as {@link String}
     *
     * @apiNote workflow example:
     * <pre>
     *     {@code
     *          @Override
     *          protected String validateSignUp(String name, String surname, String email, String password, String language, Object... custom) {
     *              String validation = super.validateSignUp(name, surname, email, password, language, custom);
     *              if (validation != null)
     *                  return validation;
     *              if(custom[0] == null)
     *                  return "error_key";
     *              return null;
     *          }
     *     }
     * </pre>
     */
    protected String validateSignUp(String name, String surname, String email, String password, String language,
                                    Object... custom) {
        if (!Companion.isNameValid(name))
            return WRONG_NAME_MESSAGE;
        if (!Companion.isSurnameValid(surname))
            return WRONG_SURNAME_MESSAGE;
        if (!Companion.isEmailValid(email))
            return WRONG_EMAIL_MESSAGE;
        if (!Companion.isPasswordValid(password))
            return WRONG_PASSWORD_MESSAGE;
        if (!Companion.isLanguageValid(language))
            return WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method used to sign in the <b>Equinox's system</b>
     *
     * @param payload The payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "email": "the email of the user", -> [String]
     *                                  "password": "the password of the user" -> [String],
     *                                  "language": "the language of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PostMapping(path = SIGN_IN_ENDPOINT)
    @RequestPath(path = "/api/v1/users/signIn", method = POST)
    public String signIn(@RequestBody Map<String, Object> payload) {
        loadJsonHelper(payload);
        String email = jsonHelper.getString(EMAIL_KEY);
        String password = jsonHelper.getString(PASSWORD_KEY);
        String language = jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
        setSessionLocale(language);
        Object[] custom = getSignInCustomParams();
        String signInValidation = validateSignIn(email, password, language, custom);
        if (signInValidation != null)
            return failedResponse(signInValidation);
        try {
            T user = usersService.signInUser(email.toLowerCase(), password, custom);
            if (user == null)
                return notAuthorizedOrWrongDetailsResponse();
            setSessionLocale(DEFAULT_LANGUAGE);
            return successResponse(assembleSignInSuccessResponse(user));
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to get the list of the custom parameters of a custom {@link EquinoxUser} from the payload of the {@link #signIn(Map)}
     * method 
     *
     * @return the custom parameters as array of {@link Object}
     *
     * @apiNote to get the parameters from the payload you have to use the {@link #jsonHelper} instance previously loaded
     * by the {@link #signIn(Map)} method:
     * <pre>
     *     {@code
     *          protected Object[] getSignInCustomParams() {
     *              return new Object[]{jsonHelper.getString("anything")};
     *          }
     *     }
     * </pre>
     *
     * @implNote as default will be returned an empty array, so that means no customized user has being used
     */
    protected Object[] getSignInCustomParams() {
        return new Object[0];
    }

    /**
     * Method used to validate the inputs of the {@link #signIn(Map)} method to correctly execute a sign-in operation
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the {@link EquinoxUser} to execute a customized
     *             sign-in validation
     *
     * @return the key of the error if any inputs is wrong, null otherwise as {@link String}
     *
     * @apiNote workflow example:
     * <pre>
     *     {@code
     *          @Override
     *          protected String validateSignIn(String email, String password, String language, Object... custom) {
     *              String validation = super.validateSignIn(email, password, language, custom);
     *              if (validation != null)
     *                  return validation;
     *              if(custom[0] == null)
     *                  return "error_key";
     *              return null;
     *          }
     *     }
     * </pre>
     */
    protected String validateSignIn(String email, String password, String language, Object... custom) {
        if (!Companion.isEmailValid(email))
            return WRONG_EMAIL_MESSAGE;
        if (!Companion.isPasswordValid(password))
            return WRONG_PASSWORD_MESSAGE;
        if (!Companion.isLanguageValid(language))
            return WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method used to assemble the sign-in response with the user details
     *
     * @param user The user authenticated in that operation
     *
     * @return the response as {@link JSONObject}
     *
     * @apiNote workflow example to add custom user property of a custom {@link EquinoxUser}:
     * <pre>
     *     {@code
     *          @Override
     *          protected JSONObject assembleSignInSuccessResponse(CustomUser user) {
     *              JSONObject response = super.assembleSignInSuccessResponse(user);
     *              response.put("custom_property", user.customProperty());
     *              return response;
     *          }
     *     }
     * </pre>
     */
    protected JSONObject assembleSignInSuccessResponse(T user) {
        JSONObject response = new JSONObject();
        response.put(USER_IDENTIFIER_KEY, user.getId());
        response.put(TOKEN_KEY, user.getToken());
        response.put(PROFILE_PIC_KEY, user.getProfilePic());
        response.put(NAME_KEY, user.getName());
        response.put(SURNAME_KEY, user.getSurname());
        response.put(LANGUAGE_KEY, user.getLanguage());
        return response;
    }

    /**
     * Method used to get the dynamic data of the user to correctly update in all the devices where the user is connected
     *
     * @param userId    The identifier of the user
     * @param token The token of the user
     * @return the result of the request as {@link String}
     */
    @GetMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + DYNAMIC_ACCOUNT_DATA_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}/dynamicAccountData", method = GET)
    public String getDynamicAccountData(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        return successResponse(usersService.getDynamicAccountData(userId));
    }

    /**
     * Method used to change the profile pic of the user
     *
     * @param userId The identifier of the user
     * @param token The token of the user
     * @param profilePic The profile pic chosen by the user to set as the new profile pic
     * @return the result of the request as {@link String}
     */
    @PostMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_PROFILE_PIC_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}/changeProfilePic", method = POST)
    public String changeProfilePic(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestParam(PROFILE_PIC_KEY) MultipartFile profilePic
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        if (profilePic.isEmpty())
            return wrongProcedureResponse();
        JSONObject response = new JSONObject();
        try {
            String profilePicUrl = usersService.changeProfilePic(profilePic, userId);
            response.put(PROFILE_PIC_KEY, profilePicUrl);
            return successResponse(response);
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to change the email of the user
     *
     * @param userId The identifier of the user
     * @param token The token of the user
     * @param payload Payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "email": "the new email of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_EMAIL_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}/changeEmail", method = PATCH)
    public String changeEmail(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String email = jsonHelper.getString(EMAIL_KEY);
        if (!Companion.isEmailValid(email))
            return failedResponse(WRONG_EMAIL_MESSAGE);
        try {
            usersService.changeEmail(email, userId);
            return successResponse();
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to change the password of the user
     *
     * @param userId The identifier of the user
     * @param token The token of the user
     * @param payload Payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "password": "the new password of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_PASSWORD_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}/changePassword", method = PATCH)
    public String changePassword(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String password = jsonHelper.getString(PASSWORD_KEY);
        if (!Companion.isPasswordValid(password))
            return failedResponse(WRONG_PASSWORD_MESSAGE);
        try {
            usersService.changePassword(password, userId);
            return successResponse();
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to change the language of the user
     *
     * @param userId The identifier of the user
     * @param token The token of the user
     * @param payload Payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "language": "the new language of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_LANGUAGE_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}/changeLanguage", method = PATCH)
    public String changeLanguage(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String language = jsonHelper.getString(LANGUAGE_KEY);
        if (!Companion.isLanguageValid(language))
            return failedResponse(WRONG_LANGUAGE_MESSAGE);
        try {
            usersService.changeLanguage(language, userId);
            return successResponse();
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to delete the account of the user
     *
     * @param userId The identifier of the user
     * @param token The token of the user
     * @return the result of the request as {@link String}
     */
    @DeleteMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}",
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{user_id}", method = DELETE)
    public String deleteAccount(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        usersService.deleteUser(userId);
        return successResponse();
    }

}