package com.tecknobit.equinoxbackend.environment.helpers.services;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.equinoxbackend.environment.helpers.services.repositories.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.records.EquinoxUser;
import com.tecknobit.equinoxbackend.resourcesutils.ResourcesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tecknobit.apimanager.apis.APIRequest.SHA256_ALGORITHM;
import static com.tecknobit.equinoxbackend.environment.records.EquinoxItem.IDENTIFIER_KEY;
import static com.tecknobit.equinoxbackend.environment.records.EquinoxUser.*;
import static java.lang.System.currentTimeMillis;

/**
 * The {@code EquinoxUsersHelper} class is useful to manage all the user database operations
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ResourcesManager
 *
 * @param <T>: the type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R>: the type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @since 1.0.1
 */
@Service
public class EquinoxUsersHelper<T extends EquinoxUser, R extends EquinoxUsersRepository<T>>
        extends EquinoxItemsHelper<T> implements ResourcesManager {

    /**
     * {@code ALTER_TABLE_} query command
     */
    protected static final String ALTER_TABLE_ = "ALTER TABLE ";

    /**
     * {@code _DROP_COLUMN_} query command
     */
    protected static final String _DROP_COLUMN_ = " DROP COLUMN ";

    /**
     * {@code VALUES_QUERY_PART} values query part
     */
    protected static final String VALUES_QUERY_PART = " VALUES (";

    /**
     * {@code BASE_SIGN_UP_QUERY} base part of the insertion query
     */
    protected static final String BASE_SIGN_UP_QUERY = "INSERT INTO " + USERS_KEY + "(";

    /**
     * {@code DEFAULT_USER_VALUES_KEYS} the default keys of the values to use in the {@link #BASE_SIGN_UP_QUERY}
     */
    protected static final List<String> DEFAULT_USER_VALUES_KEYS = List.of(DISCRIMINATOR_VALUE_KEY, IDENTIFIER_KEY,
            TOKEN_KEY, NAME_KEY, SURNAME_KEY, EMAIL_KEY, PASSWORD_KEY, LANGUAGE_KEY);

    /**
     * {@code usersRepository} instance for the users repository
     */
    @Autowired
    protected R usersRepository;

    /**
     * {@code discriminatorValue} value of the discriminator to use to save the users in the related table
     */
    protected String discriminatorValue;

    /**
     * Constructor to init the {@link EquinoxUsersHelper} controller <br>
     * <p>
     * No-any params required
     */
    public EquinoxUsersHelper() {
        try {
            discriminatorValue = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } catch (ClassCastException e) {
            discriminatorValue = "EquinoxUser";
        }
    }

    /**
     * Method to sign up a new user in the system
     *
     * @param id:       the identifier of the user
     * @param token:    the token of the user
     * @param name:     the name of the user
     * @param surname:  the surname of the user
     * @param email:    the email of the user
     * @param password: the password of the user
     * @param language: the language of the user
     * @param custom: the custom parameters to add in the default query
     *
     * @apiNote the order of the custom parameters must be the same of that specified in the {@link #getQueryValuesKeys()}
     */
    public void signUpUser(String id, String token, String name, String surname, String email, String password,
                           String language, Object... custom) throws NoSuchAlgorithmException {
        StringBuilder queryBuilder = new StringBuilder(BASE_SIGN_UP_QUERY);
        arrangeQuery(queryBuilder, getQueryValuesKeys(), false);
        List<Object> values = new ArrayList<>(List.of(discriminatorValue, id, token, name, surname, email, hash(password),
                language));
        values.addAll(Arrays.stream(custom).toList());
        queryBuilder.append(VALUES_QUERY_PART);
        arrangeQuery(queryBuilder, values, true);
        entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();
    }

    /**
     * Method to get the list of keys to use in the {@link #BASE_SIGN_UP_QUERY} <br>
     * No-any params required
     *
     * @return a list of keys as {@link List} of {@link String}
     * @apiNote This method allows a customizable sign-up with custom parameters added in a customization of the {@link EquinoxUser}
     */
    protected List<String> getQueryValuesKeys() {
        return DEFAULT_USER_VALUES_KEYS;
    }

    /**
     * Method to arrange the {@link #BASE_SIGN_UP_QUERY} with dynamic list of values to use in that query
     *
     * @param queryBuilder: the builder of the query to execute
     * @param list: the list to arrange
     * @param escape: whether the values of the list must be escaped with the {@link #SINGLE_QUOTE} character
     *
     * @param <E>: type of the element in the list
     */
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
     * Method to sign in an existing user
     *
     * @param email:    the email of the user
     * @param password: the password of the user
     * @param custom: the custom parameters added in a customization of the {@link EquinoxUser}
     *
     * @return the authenticated user as {@link EquinoxUser} if the credentials inserted were correct
     */
    public T signInUser(String email, String password, Object... custom) throws NoSuchAlgorithmException {
        T user = usersRepository.findUserByEmail(email);
        if (validateSignIn(user, password, custom))
            return user;
        return null;
    }

    /**
     * Method to validate the sign in request
     *
     * @param user:    the user to validated
     * @param password: the password of the user
     * @param custom: the custom parameters added in a customization of the {@link EquinoxUser} to execute a customized
     *             sign in validation
     *
     * @return the authenticated user as {@link EquinoxUser} if the credentials inserted were correct null otherwise
     */
    protected boolean validateSignIn(T user, String password, Object... custom) throws NoSuchAlgorithmException {
        return user != null && user.getPassword().equals(hash(password));
    }

    /**
     * Method to change the profile pic of the {@link EquinoxUser}
     *
     * @param profilePic: the profile pic resource
     * @param userId:     the identifier of the user
     */
    public String changeProfilePic(MultipartFile profilePic, String userId) throws IOException {
        String profilePicPath = createProfileResource(profilePic, userId + currentTimeMillis());
        usersRepository.changeProfilePic(profilePicPath, userId);
        deleteProfileResource(userId);
        saveResource(profilePic, profilePicPath);
        return profilePicPath;
    }

    /**
     * Method to change the email of the {@link EquinoxUser}
     *
     * @param newEmail: the new email of the user
     * @param userId:   the identifier of the user
     */
    public void changeEmail(String newEmail, String userId) {
        usersRepository.changeEmail(newEmail, userId);
    }

    /**
     * Method to change the password of the {@link EquinoxUser}
     *
     * @param newPassword: the new password of the user
     * @param userId:      the identifier of the user
     */
    public void changePassword(String newPassword, String userId) throws NoSuchAlgorithmException {
        usersRepository.changePassword(hash(newPassword), userId);
    }

    /**
     * Method to change the language of the {@link EquinoxUser}
     *
     * @param newLanguage: the new language of the user
     * @param userId:      the identifier of the user
     */
    public void changeLanguage(String newLanguage, String userId) {
        usersRepository.changeLanguage(newLanguage, userId);
    }

    /**
     * Method to delete a user
     *
     * @param id: the identifier of the user to delete
     */
    public void deleteUser(String id) {
        usersRepository.deleteById(id);
        deleteProfileResource(id);
    }

    /**
     * Method to hash a sensitive user data
     *
     * @param secret: the user value to hash
     * @throws NoSuchAlgorithmException when the hash of the user value fails
     */
    protected String hash(String secret) throws NoSuchAlgorithmException {
        return APIRequest.base64Digest(secret, SHA256_ALGORITHM);
    }

}