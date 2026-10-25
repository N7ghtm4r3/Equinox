package com.tecknobit.equinoxbackend.environment.services.users.service;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.equinoxbackend.annotations.EntityRetriever;
import com.tecknobit.equinoxbackend.apis.batch.EquinoxItemsHelper;
import com.tecknobit.equinoxbackend.apis.resources.ResourcesManager;
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxForbiddenException;
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxFormException;
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxServerException;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignInRequestDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.requests.SignUpRequestDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.ChangeProfilePicResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.DynamicAccountDataResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignInResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.dtos.responses.SignUpResponseDto;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxcore.annotations.Assembler;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.tecknobit.apimanager.apis.APIRequest.SHA256_ALGORITHM;
import static com.tecknobit.equinoxbackend.apis.database.SQLConstants.*;
import static com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages.WRONG_PROFILE_PIC_MESSAGE;
import static com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.DISCRIMINATOR_VALUE_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;
import static java.lang.System.currentTimeMillis;

/**
 * The {@code EquinoxUsersService} class is useful to manage all the user database operations
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ResourcesManager
 *
 * @param <U> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @since 1.0.1
 */
@Service
public class EquinoxUsersService<U extends EquinoxUser, R extends EquinoxUsersRepository<U>> extends EquinoxItemsHelper
        implements ResourcesManager {

    /**
     * {@code SELECT_} query command
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String SELECT_ = "SELECT ";

    /**
     * {@code _FROM_} query command
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String _FROM_ = " FROM ";

    /**
     * {@code ALTER_TABLE_} query command
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String ALTER_TABLE_ = "ALTER TABLE ";

    /**
     * {@code _DROP_COLUMN_} query command
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String _DROP_COLUMN_ = " DROP COLUMN ";

    /**
     * {@code VALUES_QUERY_PART} values query part
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String VALUES_QUERY_PART = " VALUES (";

    /**
     * {@code BASE_SIGN_UP_QUERY} base part of the insertion query
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final String BASE_SIGN_UP_QUERY = "INSERT INTO " + USERS_KEY + "(";

    /**
     * {@code DEFAULT_USER_SIGN_UP_KEYS} the default keys of the values to use in the {@link #BASE_SIGN_UP_QUERY}
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final List<String> DEFAULT_USER_SIGN_UP_KEYS = List.of(DISCRIMINATOR_VALUE_KEY, IDENTIFIER_KEY,
            TOKEN_KEY, NAME_KEY, SURNAME_KEY, EMAIL_KEY, PASSWORD_KEY, PROFILE_PIC_KEY, LANGUAGE_KEY);

    /**
     * {@code DEFAULT_DYNAMIC_ACCOUNT_DATA_KEYS} the default keys of the values to retrieve with the
     * {@link #getDynamicAccountData(String)} method
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected static final List<String> DEFAULT_DYNAMIC_ACCOUNT_DATA_KEYS = List.of(EMAIL_KEY, PROFILE_PIC_KEY, LANGUAGE_KEY);

    /**
     * {@code usersRepository} instance for the users repository
     */
    protected final R usersRepository;

    /**
     * {@code discriminatorValue} value of the discriminator to use to save the users in the related table
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected String discriminatorValue;

    /**
     * Constructor to init the {@link EquinoxUsersService} service
     *
     * @param usersRepository The instance for the users repository
     */
    @Autowired
    @SuppressWarnings("unchecked")
    public EquinoxUsersService(R usersRepository) {
        this.usersRepository = usersRepository;

        //TODO: TO REMOVE
        try {
            discriminatorValue = ((Class<U>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } catch (ClassCastException e) {
            discriminatorValue = "EquinoxUser";
        }
    }

    /**
     * Method used to register a user from the sign-up request and assemble the response
     *
     * <p>The password is hashed before constructing the user. The user returned by
     * {@link #resolveSignUpUser(String, String, String, SignUpRequestDto)} is saved through the users repository
     * before assembling the response. The same request instance is passed to both customization hooks
     *
     * @param signUpRequest The request containing the user details and the password to hash
     *
     * @return the registration details as {@link SignUpResponseDto}
     *
     * @since 2.0.0
     */
    @SuppressWarnings("unchecked")
    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequest)  {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

        String userId = environmentService.generateIdentifier();
        String userToken = environmentService.generateIdentifier();
        String password = hash(signUpRequest.getPassword());

        U signUpUser = (U) resolveSignUpUser(userId, userToken, password, signUpRequest);
        usersRepository.save(signUpUser);

        return resolveSignUpResponse(userId, userToken, signUpRequest);
    }

    /**
     * Method used to assemble the user to save during registration
     *
     * <p>Override this method to construct a customized {@link EquinoxUser} using additional request data.
     * The returned user must be compatible with the user type managed by the repository. This method only
     * constructs the user; persistence is performed by {@link #signUpUser(SignUpRequestDto)}
     *
     * @param userId        The generated identifier of the user
     * @param userToken     The generated token of the user
     * @param safePassword  The already hashed password to assign to the user
     * @param signUpRequest The request containing the standard and any custom user details
     *
     * @return the user to save as {@link EquinoxUser}
     *
     * @since 2.0.0
     */
    @Assembler
    protected EquinoxUser resolveSignUpUser(String userId, String userToken, String safePassword,
                                            SignUpRequestDto signUpRequest) {
        return new EquinoxUser(
                userId,
                userToken,
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getEmail(),
                safePassword,
                signUpRequest.getLanguage()
        );
    }

    /**
     * Method used to assemble the sign-up response after saving the user
     *
     * <p>The default response contains the generated identifier, token, and configured default profile pic.
     * Override this method to return a subclass of {@link SignUpResponseDto} with additional response data
     *
     * <p>The request retains its concrete type, allowing overrides to access custom fields by casting it
     * to the configured request subclass. The password in the request is not replaced with its hash
     *
     * @param userId        The generated identifier of the registered user
     * @param userToken     The generated token of the registered user
     * @param signUpRequest The original request containing the standard and any custom user details
     *
     * @return the registration details as {@link SignUpResponseDto}
     *
     * @since 2.0.0
     */
    @Assembler
    protected SignUpResponseDto resolveSignUpResponse(String userId, String userToken, SignUpRequestDto signUpRequest) {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();

        return environmentService.useDefaultsConfig(defaultsConfig -> new SignUpResponseDto(
                userId,
                userToken,
                defaultsConfig.getDefaultUserProfilePic()
        ));
    }

    /**
     * Method used to sign up a new user in the system
     *
     * <p>The configured default profile pic is included in the user insertion
     *
     * @param id The identifier of the user
     * @param token The token of the user
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters to add in the default query
     *
     * @apiNote the order of the custom parameters must be the same of that specified in the {@link #getSignUpKeys()}
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public void signUpUser(String id, String token, String name, String surname, String email, String password,
                           String language, Object... custom) throws NoSuchAlgorithmException {
        StringBuilder queryBuilder = new StringBuilder(BASE_SIGN_UP_QUERY);
        arrangeQuery(queryBuilder, getSignUpKeys(), false);

        String defaultProfilePic = "resolveUserDefaultProfilePic()";
        List<Object> values = new ArrayList<>(List.of(discriminatorValue, id, token, name, surname, email, hash(password),
                defaultProfilePic, language));
        values.addAll(Arrays.stream(custom).toList());

        queryBuilder.append(VALUES_QUERY_PART);
        arrangeQuery(queryBuilder, values, true);
        entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();
    }

    /**
     * Method used to get the list of keys to use in the {@link #BASE_SIGN_UP_QUERY}
     *
     * @return a list of keys as {@link List} of {@link String}
     * @apiNote This method allows a customizable sign-up with custom parameters added in a customization of the {@link EquinoxUser}
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected List<String> getSignUpKeys() {
        return DEFAULT_USER_SIGN_UP_KEYS;
    }

    /**
     * Method used to arrange the {@link #BASE_SIGN_UP_QUERY} with dynamic list of values to use in that query
     *
     * @param queryBuilder The builder of the query to execute
     * @param list The list to arrange
     * @param escape: whether the values of the list must be escaped with the {@link com.tecknobit.equinoxbackend.apis.database.SQLConstants#SINGLE_QUOTE} character
     *
     * @param <E> Type of the element in the list
     */
    @Assembler
    @Deprecated(since = "2.0.0", forRemoval = true)
    private <E> void arrangeQuery(StringBuilder queryBuilder, List<E> list, boolean escape) {
        int listSize = list.size();
        int lastIndex = listSize - 1;
        for (int j = 0; j < listSize; j++) {
            if (escape)
                queryBuilder.append(SINGLE_QUOTE);
            queryBuilder.append(list.get(j));
            if (escape)
                queryBuilder.append(SINGLE_QUOTE);
            if (j < lastIndex)
                queryBuilder.append(COMMA);
            else
                queryBuilder.append(CLOSED_ROUND_BRACKET);
        }
    }

    /**
     * Method used to authenticate a user from the sign-in request and assemble the response
     *
     * <p>The request email identifies the user to authenticate. The supplied password is hashed before
     * it is passed to {@link #validateSignIn(EquinoxUser, String, SignInRequestDto)} with the original request.
     * Missing users and rejected credentials cause the same forbidden exception
     *
     * <p>Override the validation hook to apply additional checks and
     * {@link #resolveSignInResponse(EquinoxUser)} to customize the response
     *
     * @param signInRequest The request containing the credentials and any custom sign-in data
     *
     * @return the authenticated user details as {@link SignInResponseDto}
     * @throws EquinoxForbiddenException when the user does not exist or sign-in validation fails
     *
     * @since 2.0.0
     */
    public SignInResponseDto signInUser(SignInRequestDto signInRequest) {
        String email = signInRequest.getEmail();
        U user = usersRepository.findUserByEmail(email);

        String password = signInRequest.getPassword();
        String safePassword = hash(password);

        if (validateSignIn(user, safePassword, signInRequest))
            return resolveSignInResponse(user);
        else
            throw new EquinoxForbiddenException();
    }

    /**
     * Method used to validate the credentials and any custom sign-in conditions
     *
     * <p>The default implementation rejects a missing user and compares the stored password with
     * the supplied hash. Overrides can inspect additional properties of the original request subclass
     * and should preserve these credential checks. The supplied password must not be hashed again
     *
     * @param user          The user found by email, or {@code null} when no user matches
     * @param safePassword  The already hashed password to compare with the stored password
     * @param signInRequest The original request containing the credentials and any custom sign-in data
     *
     * @return whether the sign-in conditions are satisfied as {@code boolean}
     *
     * @since 2.0.0
     */
    protected boolean validateSignIn(U user, String safePassword, SignInRequestDto signInRequest) {
        return user != null && user.getPassword().equals(safePassword);
    }

    /**
     * Method used to assemble the sign-in response from the authenticated user
     *
     * <p>The response contains the saved identifier, token, profile pic, name, surname, and language.
     * Override this method to return a subclass of {@link SignInResponseDto} with additional response data
     *
     * @param user The authenticated user whose details are included in the response
     *
     * @return the authenticated user details as {@link SignInResponseDto}
     *
     * @since 2.0.0
     */
    @Assembler
    protected SignInResponseDto resolveSignInResponse(U user) {
        return new SignInResponseDto(
                user.getId(),
                user.getToken(),
                user.getProfilePic(),
                user.getName(),
                user.getSurname(),
                user.getLanguage()
        );
    }

    /**
     * Method used to authenticate a user through the legacy sign-in overload
     *
     * @param email    The email address used to find the user
     * @param password The plain-text password to verify
     * @param custom   The custom values passed to the legacy credential validation hook
     *
     * @return the authenticated user, or {@code null} when validation fails, as {@link EquinoxUser}
     * @deprecated use {@link #signInUser(SignInRequestDto)} to authenticate a request and return its response DTO
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public U signInUser(String email, String password, Object... custom) throws NoSuchAlgorithmException {
        U user = usersRepository.findUserByEmail(email);
        if (validateSignIn(user, password, custom))
            return user;
        return null;
    }

    /**
     * Method used to validate credentials through the legacy sign-in hook
     *
     * @param user     The user found by email, or {@code null} when no user matches
     * @param password The plain-text password to verify
     * @param custom   The additional sign-in values, unused by the default implementation
     *
     * @return whether the user exists and the password matches as {@code boolean}
     * @deprecated use {@link #validateSignIn(EquinoxUser, String, SignInRequestDto)} with the already hashed password
     * and the original request. The request-based sign-in flow does not invoke this legacy hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected boolean validateSignIn(U user, String password, Object... custom) throws NoSuchAlgorithmException {
        return user != null && user.getPassword().equals(hash(password));
    }

    /**
     * Method used to get whether the user is present in the database
     *
     * @param userId The identifier of the user to check
     * @return whether the user exists as {@code boolean}
     */
    public boolean userExists(String userId) {
        return usersRepository.findById(userId).isPresent();
    }

    /**
     * Method used to retrieve dynamic account data through the legacy SQL query
     *
     * @param userId The identifier of the user
     *
     * @return the selected dynamic account data as {@link JSONObject}
     * @deprecated use {@link #getDynamicAccountData(EquinoxUser)} to assemble the response from the authenticated
     * user. The DTO-based endpoint does not invoke this overload
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public JSONObject getDynamicAccountData(String userId) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_);
        List<String> dynamicAccountDataKeys = getDynamicAccountDataKeys();

        arrangeDynamicDataQuery(queryBuilder, dynamicAccountDataKeys);
        queryBuilder.append(_FROM_ + USERS_KEY);
        queryBuilder.append(_WHERE_);
        queryBuilder.append(IDENTIFIER_KEY + "=").append(SINGLE_QUOTE).append(userId).append(SINGLE_QUOTE);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        JSONArray result = new JSONArray(query.getResultList().get(0));
        JSONObject dynamicData = new JSONObject();

        for (int j = 0; j < result.length(); j++)
            dynamicData.put(dynamicAccountDataKeys.get(j), result.get(j));

        return dynamicData;
    }

    /**
     * Method used to select the columns returned by the legacy dynamic account data query
     *
     * @return the selected column names as {@link List} of {@link String}
     * @deprecated override {@link #getDynamicAccountData(EquinoxUser)} and return a custom
     * {@link DynamicAccountDataResponseDto}. The DTO-based flow does not invoke this hook
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    protected List<String> getDynamicAccountDataKeys() {
        return DEFAULT_DYNAMIC_ACCOUNT_DATA_KEYS;
    }

    /**
     * Method used to arrange the dynamic data query to execute by the {@link #getDynamicAccountData(String)} method
     *
     * @param queryBuilder The builder of the query to execute
     * @param list         The list to arrange
     * @param <E>          Type of the element in the list
     */
    @Assembler
    @Deprecated(since = "2.0.0", forRemoval = true)
    private <E> void arrangeDynamicDataQuery(StringBuilder queryBuilder, List<E> list) {
        int ListSize = list.size();
        int lastIndex = ListSize - 1;

        for (int j = 0; j < ListSize; j++) {
            queryBuilder.append(list.get(j));
            if (j < lastIndex)
                queryBuilder.append(COMMA);
        }
    }

    /**
     * Method used to assemble dynamic account data from the authenticated user
     *
     * <p>The default response contains the user's email, profile pic, and saved language. This method
     * reads the supplied user without querying the database or modifying the account
     *
     * <p>Override this method to include custom user properties in a subclass of
     * {@link DynamicAccountDataResponseDto}
     *
     * @param user The authenticated user whose current account data is included in the response
     *
     * @return the dynamic account data as {@link DynamicAccountDataResponseDto}
     *
     * @since 2.0.0
     */
    public DynamicAccountDataResponseDto getDynamicAccountData(U user) {
        return new DynamicAccountDataResponseDto(
                user.getEmail(),
                user.getProfilePic(),
                user.getLanguage()
        );
    }

    /**
     * Method used to retrieve the user associated with the specified token
     *
     * @param token The token of the user to retrieve
     *
     * @return the matching user, or an empty optional if no user matches the token, as {@link Optional}
     *
     * @since 2.0.0
     */
    @EntityRetriever
    public Optional<U> findUserByToken(String token) {
        return usersRepository.findUserByToken(token);
    }

    /**
     * Method used to change the user's profile pic
     *
     * @param profilePic The uploaded file to set as the new profile pic
     * @param userId     The identifier of the user whose profile pic is changed
     *
     * @return the new profile pic path as {@link ChangeProfilePicResponseDto}
     */
    @Transactional
    public ChangeProfilePicResponseDto changeProfilePic(MultipartFile profilePic, String userId) {
        if(profilePic.isEmpty())
            throw new EquinoxFormException(WRONG_PROFILE_PIC_MESSAGE);

        String profilePicPath = createProfileResource(profilePic, userId + currentTimeMillis());
        usersRepository.changeProfilePic(profilePicPath, userId);

        try {
            deleteProfileResource(userId);
            saveResource(profilePic, profilePicPath);
        } catch (IOException e) {
            throw new EquinoxServerException(e);
        }

        return new ChangeProfilePicResponseDto(
                profilePicPath
        );
    }

    /**
     * Method used to change the email of the {@link EquinoxUser}
     *
     * @param newEmail The new email of the user
     * @param userId:   the identifier of the user
     */
    @Transactional
    public void changeEmail(String newEmail, String userId) {
        usersRepository.changeEmail(newEmail, userId);
    }

    /**
     * Method used to change the password of the {@link EquinoxUser}
     *
     * @param newPassword The new password of the user
     * @param userId:      the identifier of the user
     */
    @Transactional
    public void changePassword(String newPassword, String userId) throws NoSuchAlgorithmException {
        usersRepository.changePassword(hash(newPassword), userId);
    }

    /**
     * Method used to change the language of the {@link EquinoxUser}
     *
     * @param newLanguage The new language of the user
     * @param userId:      the identifier of the user
     */
    @Transactional
    public void changeLanguage(String newLanguage, String userId) {
        usersRepository.changeLanguage(newLanguage, userId);
    }

    /**
     * Method used to delete a user
     *
     * @param id The identifier of the user to delete
     */
    public void deleteUser(String id) {
        usersRepository.deleteById(id);
        deleteProfileResource(id);
    }

    /**
     * Method used to hash a sensitive user data
     *
     * @param secret The user value to hash
     *
     * @return the Base64-encoded SHA-256 digest as {@link String}
     */
    protected String hash(String secret) {
        try {
            return APIRequest.base64Digest(secret, SHA256_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EquinoxServerException(e);
        }
    }

}