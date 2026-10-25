package com.tecknobit.equinoxbackend.environment.services.users.controller;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.services.builtin.controllers.EquinoxController;
import com.tecknobit.equinoxbackend.environment.services.builtin.controllers.EquinoxControllerCore;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignInResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignUpResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import com.tecknobit.equinoxcore.helpers.InputsValidator;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.*;

/**
 * The {@code EquinoxUsersController} handles user-related operations, providing the
 * corresponding endpoints that clients can connect to in order to manage user data
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxController
 *
 * @param <T> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersService} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @see EquinoxControllerCore
 * @see EquinoxController
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
        super();
        this.usersService = usersService;
    }

    /**
     * Method used to register a user from a validated sign-up request
     *
     * <p>Request constraints are validated before this method is invoked. Registration is delegated to
     * {@link EquinoxUsersService#signUpUser(SignUpRequestDto)}, and its response is included in the success body
     *
     * <p>A custom request subclass must be selected by the configured JSON deserializer. Declaring a
     * subclass alone does not change the request type instantiated for this endpoint
     *
     * @param signUpRequest The validated request containing the user details and server secret
     *
     * @return the registration response, or the procedure failure response if registration throws an exception,
     * as {@link ResponseEntity}
     *
     * @since 2.0.0
     */
    @PostMapping(path = SIGN_UP_ENDPOINT)
    @RequestPath(path = "/api/v2/users/signUp", method = POST)
    public ResponseEntity<?> signUp(
            @RequestBody @Valid SignUpRequestDto signUpRequest
    ) {
        String language = signUpRequest.getLanguage();
        setSessionLocale(language);

        try {
            SignUpResponseDto signUpResponse = usersService.signUpUser(signUpRequest);

            return successResponse(signUpResponse);
        } catch (Exception e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to provide custom parameters for the legacy sign-up flow
     *
     * @return the custom parameters, empty by default, as {@code Object[]}
     * @deprecated declare custom fields in a subclass of {@link SignUpRequestDto} and configure its JSON deserialization.
     * The {@link #signUp(SignUpRequestDto)} endpoint does not invoke this hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected Object[] getSignUpCustomParams() {
        return new Object[0];
    }

    /**
     * Method used to validate the standard inputs of the legacy sign-up flow
     *
     * @param name     The name of the user
     * @param surname  The surname of the user
     * @param email    The email address of the user
     * @param password The plain-text password of the user
     * @param language The language of the user
     * @param custom   The additional sign-up values, unused by the default implementation
     *
     * @return the first validation error key, or {@code null} when all inputs are valid, as {@link String}
     * @deprecated declare validation constraints on {@link SignUpRequestDto} or a custom request subclass.
     * The {@link #signUp(SignUpRequestDto)} endpoint does not invoke this hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected String validateSignUp(String name, String surname, String email, String password, String language,
                                    Object... custom) {
        if (!InputsValidator.isNameValid(name))
            return EquinoxMessages.WRONG_NAME_MESSAGE;
        if (!InputsValidator.isSurnameValid(surname))
            return EquinoxMessages.WRONG_SURNAME_MESSAGE;
        if (!InputsValidator.isEmailValid(email))
            return EquinoxMessages.WRONG_EMAIL_MESSAGE;
        if (!InputsValidator.isPasswordValid(password))
            return EquinoxMessages.WRONG_PASSWORD_MESSAGE;
        if (!InputsValidator.isLanguageValid(language))
            return EquinoxMessages.WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method used to authenticate a user from the validated sign-in request
     *
     * <p>Request constraints are validated before this method is invoked. The request language
     * selects the locale, and the response DTO returned by {@link EquinoxUsersService#signInUser(SignInRequestDto)}
     * is included in the success body
     *
     * <p>A custom request subclass must be selected by the configured JSON deserializer.
     * Declaring a subclass alone does not change the request type instantiated for this endpoint
     *
     * @param signInRequest The validated request containing the credentials and the request language
     *
     * @return the sign-in response, or the procedure failure response when hashing fails, as {@link ResponseEntity}
     * @throws com.tecknobit.equinoxbackend.environment.exceptions.EquinoxForbiddenException
     *         when the user does not exist or sign-in validation fails
     *
     * @since 2.0.0
     */
    @PostMapping(path = SIGN_IN_ENDPOINT)
    @RequestPath(path = "/api/v2/users/signIn", method = POST)
    public ResponseEntity<?> signIn(
            @RequestBody @Valid SignInRequestDto signInRequest
    ) {
        String language = signInRequest.getLanguage();
        setSessionLocale(language);

        try {
            SignInResponseDto response = usersService.signInUser(signInRequest);

            return successResponse(response);
        } catch (NoSuchAlgorithmException e) {
            return wrongProcedureResponse();
        }
    }

    /**
     * Method used to provide custom parameters for the legacy sign-in flow
     *
     * @return the custom parameters, empty by default, as {@code Object[]}
     * @deprecated declare custom fields in a subclass of {@link SignInRequestDto} and configure its JSON deserialization.
     * The {@link #signIn(SignInRequestDto)} endpoint does not invoke this hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected Object[] getSignInCustomParams() {
        return new Object[0];
    }

    /**
     * Method used to validate the standard inputs of the legacy sign-in flow
     *
     * @param email    The email address of the user
     * @param password The plain-text password of the user
     * @param language The language used to localize the request
     * @param custom   The additional sign-in values, unused by the default implementation
     *
     * @return the first validation error key, or {@code null} when all inputs are valid, as {@link String}
     * @deprecated declare request constraints on {@link SignInRequestDto} or a custom subclass and apply
     * additional authentication checks in the users service. The {@link #signIn(SignInRequestDto)} endpoint
     * does not invoke this hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected String validateSignIn(String email, String password, String language, Object... custom) {
        if (!InputsValidator.isEmailValid(email))
            return EquinoxMessages.WRONG_EMAIL_MESSAGE;
        if (!InputsValidator.isPasswordValid(password))
            return EquinoxMessages.WRONG_PASSWORD_MESSAGE;
        if (!InputsValidator.isLanguageValid(language))
            return EquinoxMessages.WRONG_LANGUAGE_MESSAGE;
        return null;
    }

    /**
     * Method used to assemble user details for the legacy sign-in response
     *
     * @param user The authenticated user whose details are included in the response
     *
     * @return the user details as {@link JSONObject}
     * @deprecated override {@link EquinoxUsersService#resolveSignInResponse(EquinoxUser)} to return a custom
     * response DTO. The {@link #signIn(SignInRequestDto)} endpoint does not invoke this assembler
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
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
    @RequestPath(path = "/api/v2/users/{user_id}/dynamicAccountData", method = GET)
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
    @RequestPath(path = "/api/v2/users/{user_id}/changeProfilePic", method = POST)
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
    @RequestPath(path = "/api/v2/users/{user_id}/changeEmail", method = PATCH)
    public String changeEmail(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String email = jsonHelper.getString(EMAIL_KEY);
        if (!InputsValidator.isEmailValid(email))
            return failedResponse(EquinoxMessages.WRONG_EMAIL_MESSAGE);
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
    @RequestPath(path = "/api/v2/users/{user_id}/changePassword", method = PATCH)
    public String changePassword(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String password = jsonHelper.getString(PASSWORD_KEY);
        if (!InputsValidator.isPasswordValid(password))
            return failedResponse(EquinoxMessages.WRONG_PASSWORD_MESSAGE);
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
    @RequestPath(path = "/api/v2/users/{user_id}/changeLanguage", method = PATCH)
    public String changeLanguage(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestHeader(TOKEN_KEY) String token,
            @RequestBody Map<String, String> payload
    ) {
        if (!isMe(userId, token))
            return notAuthorizedOrWrongDetailsResponse();
        loadJsonHelper(payload);
        String language = jsonHelper.getString(LANGUAGE_KEY);
        if (!InputsValidator.isLanguageValid(language))
            return failedResponse(EquinoxMessages.WRONG_LANGUAGE_MESSAGE);
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
    @RequestPath(path = "/api/v2/users/{user_id}", method = DELETE)
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