package com.tecknobit.equinox.environment.controllers;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.equinox.environment.helpers.services.EquinoxUsersHelper;
import com.tecknobit.equinox.environment.records.EquinoxUser;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.apimanager.apis.ServerProtector.SERVER_SECRET_KEY;
import static com.tecknobit.equinox.environment.helpers.EquinoxBaseEndpointsSet.*;
import static com.tecknobit.equinox.environment.records.EquinoxUser.*;
import static com.tecknobit.equinox.inputs.InputValidator.*;

/**
 * The {@code EquinoxUsersController} class is useful to manage all the user operations
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxController
 *
 * @since 1.0.1
 */
@RestController
public class EquinoxUsersController<T extends EquinoxUser> extends EquinoxController<T> {

    /**
     * {@code usersHelper} helper to manage the users database operations
     */
    protected final EquinoxUsersHelper<T> usersHelper;

    /**
     * Constructor to init the {@link EquinoxUsersController} controller
     *
     * @param usersHelper: helper to manage the users database operations
     */
    public EquinoxUsersController(EquinoxUsersHelper<T> usersHelper) {
        super();
        this.usersHelper = usersHelper;
    }

    /**
     * Method to sign up in the <b>Neutron's system</b>
     *
     * @param payload: payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "server_secret" : "the secret of the server" -> [String],
     *                                  "name" : "the name of the user" -> [String],
     *                                  "surname": "the surname of the user" -> [String],
     *                                  "email": "the email of the user" -> [String],
     *                                  "password": "the password of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PostMapping(path = SIGN_UP_ENDPOINT)
    @RequestPath(path = "/api/v1/users/signUp", method = POST)
    public String signUp(@RequestBody Map<String, String> payload) {
        loadJsonHelper(payload);
        mantis.changeCurrentLocale(jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE));
        if (serverProtector.serverSecretMatches(jsonHelper.getString(SERVER_SECRET_KEY))) {
            String name = jsonHelper.getString(NAME_KEY);
            String surname = jsonHelper.getString(SURNAME_KEY);
            String email = jsonHelper.getString(EMAIL_KEY);
            String password = jsonHelper.getString(PASSWORD_KEY);
            String language = jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
            mantis.changeCurrentLocale(language);
            Object[] custom = getSignUpCustomParams();
            String signUpValidation = validateSignUp(name, surname, email, password, language, custom);
            if (signUpValidation != null)
                return failedResponse(signUpValidation);
            try {
                JSONObject response = new JSONObject();
                String id = generateIdentifier();
                String token = generateIdentifier();
                usersHelper.signUpUser(
                        id,
                        token,
                        name,
                        surname,
                        email.toLowerCase(),
                        password,
                        language,
                        custom
                );
                mantis.changeCurrentLocale(DEFAULT_LANGUAGE);
                return successResponse(response
                        .put(IDENTIFIER_KEY, id)
                        .put(TOKEN_KEY, token)
                        .put(PROFILE_PIC_KEY, DEFAULT_PROFILE_PIC)
                );
            } catch (Exception e) {
                return failedResponse(WRONG_PROCEDURE_MESSAGE);
            }
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Method to get the list of the custom parameters of a custom {@link EquinoxUser} from the payload of the {@link #signUp(Map)}
     * method <br>
     * <p>
     * No-any params required
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
     * Method to validate the inputs of the {@link #signUp(Map)} method to correctly execute a sign-up operation
     *
     * @param name: the name of the user
     * @param surname: the surname of the user
     * @param email: the email of the user
     * @param password: the password of the user
     * @param language: the language of the user
     * @param custom: the custom parameters added in a customization of the {@link EquinoxUser} to execute a customized
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
        if (!isNameValid(name))
            return WRONG_NAME_MESSAGE;
        if (!isSurnameValid(surname))
            return WRONG_SURNAME_MESSAGE;
        if (!isEmailValid(email))
            return WRONG_EMAIL_MESSAGE;
        if (!isPasswordValid(password))
            return WRONG_PASSWORD_MESSAGE;
        if (!isLanguageValid(language))
            return WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method to sign in the <b>Neutron's system</b>
     *
     * @param payload: payload of the request
     *                 <pre>
     *                      {@code
     *                              {
     *                                  "email": "the email of the user", -> [String]
     *                                  "password": "the password of the user" -> [String]
     *                              }
     *                      }
     *                 </pre>
     * @return the result of the request as {@link String}
     */
    @PostMapping(path = SIGN_IN_ENDPOINT)
    @RequestPath(path = "/api/v1/users/signIn", method = POST)
    public String signIn(@RequestBody Map<String, String> payload) {
        loadJsonHelper(payload);
        String email = jsonHelper.getString(EMAIL_KEY);
        String password = jsonHelper.getString(PASSWORD_KEY);
        String language = jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
        mantis.changeCurrentLocale(language);
        Object[] custom = getSignInCustomParams();
        String signInValidation = validateSignIn(email, password, language, custom);
        if (signInValidation != null)
            return failedResponse(signInValidation);
        try {
            T user = usersHelper.signInUser(email.toLowerCase(), password, custom);
            if (user != null) {
                mantis.changeCurrentLocale(DEFAULT_LANGUAGE);
                return successResponse(assembleSignInSuccessResponse(user));
            } else
                return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
        } catch (Exception e) {
            return failedResponse(WRONG_PROCEDURE_MESSAGE);
        }
    }

    /**
     * Method to get the list of the custom parameters of a custom {@link EquinoxUser} from the payload of the {@link #signIn(Map)}
     * method <br>
     *
     * No-any params required
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
     * Method to validate the inputs of the {@link #signIn(Map)} method to correctly execute a sign-in operation
     *
     * @param email: the email of the user
     * @param password: the password of the user
     * @param language: the language of the user
     * @param custom: the custom parameters added in a customization of the {@link EquinoxUser} to execute a customized
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
        if (!isEmailValid(email))
            return WRONG_EMAIL_MESSAGE;
        if (!isPasswordValid(password))
            return WRONG_PASSWORD_MESSAGE;
        if (!isLanguageValid(language))
            return WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method to assemble the sign-in response with the user details
     *
     * @param user: the user authenticated in that operation
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
        response.put(IDENTIFIER_KEY, user.getId());
        response.put(TOKEN_KEY, user.getToken());
        response.put(PROFILE_PIC_KEY, user.getProfilePic());
        response.put(NAME_KEY, user.getName());
        response.put(SURNAME_KEY, user.getSurname());
        response.put(LANGUAGE_KEY, user.getLanguage());
        return response;
    }

    /**
     * Method to execute the auth operations
     *
     * @param payload:      the payload received with the auth request
     * @param personalData: the personal data of the user like name and surname
     * @return the result of the auth operation as {@link String}
     */
    @Deprecated(since = "1.0.3", forRemoval = true)
    protected String executeAuth(Map<String, String> payload, String... personalData) {
        loadJsonHelper(payload);
        String email = jsonHelper.getString(EMAIL_KEY);
        String password = jsonHelper.getString(PASSWORD_KEY);
        String language = jsonHelper.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
        mantis.changeCurrentLocale(language);
        if (isEmailValid(email)) {
            if (isPasswordValid(password)) {
                if (isLanguageValid(language)) {
                    String id;
                    String token;
                    String profilePicUrl;
                    JSONObject response = new JSONObject();
                    if (personalData.length == 2) {
                        id = generateIdentifier();
                        token = generateIdentifier();
                        profilePicUrl = DEFAULT_PROFILE_PIC;
                        try {
                            usersHelper.signUpUser(
                                    id,
                                    token,
                                    personalData[0],
                                    personalData[1],
                                    email.toLowerCase(),
                                    password,
                                    language
                            );
                        } catch (Exception e) {
                            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
                        }
                    } else {
                        try {
                            EquinoxUser user = usersHelper.signInUser(email.toLowerCase(), password);
                            if (user != null) {
                                id = user.getId();
                                token = user.getToken();
                                profilePicUrl = user.getProfilePic();
                                response.put(NAME_KEY, user.getName());
                                response.put(SURNAME_KEY, user.getSurname());
                                response.put(LANGUAGE_KEY, user.getLanguage());
                            } else
                                return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
                        } catch (Exception e) {
                            return failedResponse(WRONG_PROCEDURE_MESSAGE);
                        }
                    }
                    mantis.changeCurrentLocale(DEFAULT_LANGUAGE);
                    return successResponse(response
                            .put(IDENTIFIER_KEY, id)
                            .put(TOKEN_KEY, token)
                            .put(PROFILE_PIC_KEY, profilePicUrl)
                    );
                } else
                    return failedResponse(WRONG_LANGUAGE_MESSAGE);
            } else
                return failedResponse(WRONG_PASSWORD_MESSAGE);
        } else
            return failedResponse(WRONG_EMAIL_MESSAGE);
    }

    /**
     * Method to change the profile pic of the user
     *
     * @param id:         the identifier of the user
     * @param token:      the token of the user
     * @param profilePic: the profile pic chosen by the user to set as the new profile pic
     * @return the result of the request as {@link String}
     */
    @PostMapping(
            path = USERS_KEY + "/{" + IDENTIFIER_KEY + "}" + CHANGE_PROFILE_PIC_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{id}/changeProfilePic", method = POST)
    public String changeProfilePic(
            @PathVariable(IDENTIFIER_KEY) String id,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestParam(PROFILE_PIC_KEY) MultipartFile profilePic
    ) {
        if (isMe(id, token)) {
            if (!profilePic.isEmpty()) {
                JSONObject response = new JSONObject();
                try {
                    String profilePicUrl = usersHelper.changeProfilePic(profilePic, id);
                    response.put(PROFILE_PIC_KEY, profilePicUrl);
                } catch (Exception e) {
                    response.put(PROFILE_PIC_KEY, DEFAULT_PROFILE_PIC);
                }
                return successResponse(response);
            } else
                return failedResponse(WRONG_PROCEDURE_MESSAGE);
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Method to change the email of the user
     *
     * @param id:      the identifier of the user
     * @param token:   the token of the user
     * @param payload: payload of the request
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
            path = USERS_KEY + "/{" + IDENTIFIER_KEY + "}" + CHANGE_EMAIL_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{id}/changeEmail", method = PATCH)
    public String changeEmail(
            @PathVariable(IDENTIFIER_KEY) String id,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (isMe(id, token)) {
            loadJsonHelper(payload);
            String email = jsonHelper.getString(EMAIL_KEY);
            if (isEmailValid(email)) {
                try {
                    usersHelper.changeEmail(email, id);
                    return successResponse();
                } catch (Exception e) {
                    return failedResponse(WRONG_PROCEDURE_MESSAGE);
                }
            } else
                return failedResponse(WRONG_EMAIL_MESSAGE);
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Method to change the password of the user
     *
     * @param id:      the identifier of the user
     * @param token:   the token of the user
     * @param payload: payload of the request
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
            path = USERS_KEY + "/{" + IDENTIFIER_KEY + "}" + CHANGE_PASSWORD_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{id}/changePassword", method = PATCH)
    public String changePassword(
            @PathVariable(IDENTIFIER_KEY) String id,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (isMe(id, token)) {
            loadJsonHelper(payload);
            String password = jsonHelper.getString(PASSWORD_KEY);
            if (isPasswordValid(password)) {
                try {
                    usersHelper.changePassword(password, id);
                    return successResponse();
                } catch (Exception e) {
                    return failedResponse(WRONG_PROCEDURE_MESSAGE);
                }
            } else
                return failedResponse(WRONG_PASSWORD_MESSAGE);
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Method to change the language of the user
     *
     * @param id:      the identifier of the user
     * @param token:   the token of the user
     * @param payload: payload of the request
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
            path = USERS_KEY + "/{" + IDENTIFIER_KEY + "}" + CHANGE_LANGUAGE_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{id}/changeLanguage", method = PATCH)
    public String changeLanguage(
            @PathVariable(IDENTIFIER_KEY) String id,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (isMe(id, token)) {
            loadJsonHelper(payload);
            String language = jsonHelper.getString(LANGUAGE_KEY);
            if (isLanguageValid(language)) {
                try {
                    usersHelper.changeLanguage(language, id);
                    return successResponse();
                } catch (Exception e) {
                    return failedResponse(WRONG_PROCEDURE_MESSAGE);
                }
            } else
                return failedResponse(WRONG_LANGUAGE_MESSAGE);
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

    /**
     * Method to delete the account of the user
     *
     * @param id:    the identifier of the user
     * @param token: the token of the user
     * @return the result of the request as {@link String}
     */
    @DeleteMapping(
            path = USERS_KEY + "/{" + IDENTIFIER_KEY + "}",
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v1/users/{id}", method = DELETE)
    public String deleteAccount(
            @PathVariable(IDENTIFIER_KEY) String id,
            @RequestHeader(TOKEN_KEY) String token
    ) {
        if (isMe(id, token)) {
            usersHelper.deleteUser(id);
            return successResponse();
        } else
            return failedResponse(NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE);
    }

}