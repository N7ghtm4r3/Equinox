package com.tecknobit.equinoxbackend.environment.services.users.controller;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.security.authorization.annotations.IsAuthorizedUser;
import com.tecknobit.equinoxbackend.environment.services.builtin.controllers.EquinoxController;
import com.tecknobit.equinoxbackend.environment.services.builtin.controllers.EquinoxControllerCore;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.*;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.ChangeProfilePicResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.DynamicAccountDataResponseDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * @param <U> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersService} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @see EquinoxControllerCore
 * @see EquinoxController
 *
 * @since 1.0.1
 */
@RestController
public class EquinoxUsersController<U extends EquinoxUser, R extends EquinoxUsersRepository<U>,
        H extends EquinoxUsersService<U, R>> extends EquinoxController<U, R, H> {

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
     * <p>A custom request subclass must be selected by the configured JSON deserializer. Declaring a
     * subclass alone does not change the request type instantiated for this endpoint
     *
     * @param signUpRequest The validated request containing the user details and server secret
     *
     * @return the registration details wrapped in the success body as {@link ResponseEntity}
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

        SignUpResponseDto signUpResponse = usersService.signUpUser(signUpRequest);
        return successResponse(signUpResponse);
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
     * <p>A custom request subclass must be selected by the configured JSON deserializer.
     * Declaring a subclass alone does not change the request type instantiated for this endpoint
     *
     * @param signInRequest The validated request containing the credentials and the request language
     *
     * @return the sign-in details wrapped in the success body as {@link ResponseEntity}
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

        SignInResponseDto response = usersService.signInUser(signInRequest);
        return successResponse(response);
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
    protected JSONObject assembleSignInSuccessResponse(U user) {
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
     * Method used to retrieve the authenticated user's dynamic account data for synchronization across devices
     *
     * @param user   The authenticated user resolved from the security context
     * @param userId The requested user identifier, which must match the authenticated user
     *
     * @return the dynamic account data wrapped in the success body as {@link ResponseEntity}
     *
     * @since 2.0.0
     */
    @IsAuthorizedUser
    @GetMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + DYNAMIC_ACCOUNT_DATA_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}/dynamicAccountData", method = GET)
    public ResponseEntity<?> getDynamicAccountData(
            @AuthenticationPrincipal U user,
            @PathVariable(USER_IDENTIFIER_KEY) String userId
    ) {
        DynamicAccountDataResponseDto accountDataResponse = usersService.getDynamicAccountData(user);

        return successResponse(accountDataResponse);
    }

    /**
     * Method used to change the authenticated user's profile pic from a multipart upload
     *
     * @param userId     The requested user identifier, which must match the authenticated user
     * @param profilePic The uploaded file to set as the new profile pic
     *
     * @return the new profile pic path wrapped in the success body as {@link ResponseEntity}
     */
    @IsAuthorizedUser
    @PostMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_PROFILE_PIC_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}/changeProfilePic", method = POST)
    public ResponseEntity<?> changeProfilePic(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestParam(PROFILE_PIC_KEY) MultipartFile profilePic
    ) {
        ChangeProfilePicResponseDto response = usersService.changeProfilePic(profilePic, userId);

        return successResponse(response);
    }

    /**
     * Method used to change the authenticated user's email from a validated request
     *
     * @param userId             The requested user identifier, which must match the authenticated user
     * @param changeEmailRequest The validated request containing the new email address
     *
     * @return the successful response with the localized confirmation message as {@link ResponseEntity}
     */
    @IsAuthorizedUser
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_EMAIL_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}/changeEmail", method = PATCH)
    public ResponseEntity<?> changeEmail(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestBody @Valid ChangeEmailRequestDto changeEmailRequest
    ) {
        usersService.changeEmail(changeEmailRequest, userId);

        return successResponse();
    }

    /**
     * Method used to change the authenticated user's password from the request
     *
     * @param userId                The requested user identifier, which must match the authenticated user
     * @param changePasswordRequest The request containing the new plain-text password
     *
     * @return the successful response with the localized confirmation message as {@link ResponseEntity}
     */
    @IsAuthorizedUser
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_PASSWORD_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}/changePassword", method = PATCH)
    public ResponseEntity<?> changePassword(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestBody @Valid ChangePasswordRequestDto changePasswordRequest
    ) {
        usersService.changePassword(changePasswordRequest, userId);

        return successResponse();
    }

    /**
     * Method used to change the authenticated user's language from a validated request
     *
     * @param userId                The requested user identifier, which must match the authenticated user
     * @param changeLanguageRequest The validated request containing the new supported language
     *
     * @return the successful response with the localized confirmation message as {@link ResponseEntity}
     */
    @IsAuthorizedUser
    @PatchMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}" + CHANGE_LANGUAGE_ENDPOINT,
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}/changeLanguage", method = PATCH)
    public ResponseEntity<?> changeLanguage(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestBody @Valid ChangeLanguageRequestDto changeLanguageRequest
    ) {
        usersService.changeLanguage(changeLanguageRequest, userId);

        return successResponse();
    }

    /**
     * Method used to delete the authenticated user's account
     *
     * <p>Soft deletion is the default and preserves the account and profile resource. A successful
     * request-based sign-in restores the account. Hard deletion permanently removes both
     *
     * @param userId     The requested user identifier, which must match the authenticated user
     * @param hardDelete Whether to permanently remove the account; defaults to {@code false} when omitted
     *
     * @return the successful response with the localized confirmation message as {@link ResponseEntity}
     */
    @IsAuthorizedUser
    @DeleteMapping(
            path = USERS_KEY + "/{" + USER_IDENTIFIER_KEY + "}",
            headers = {
                    TOKEN_KEY
            }
    )
    @RequestPath(path = "/api/v2/users/{user_id}", method = DELETE)
    public ResponseEntity<?> deleteAccount(
            @PathVariable(USER_IDENTIFIER_KEY) String userId,
            @RequestParam(value = HARD_DELETE_KEY, required = false, defaultValue = "false") boolean hardDelete
    ) {
        usersService.deleteUser(userId, hardDelete);

        return successResponse();
    }

}