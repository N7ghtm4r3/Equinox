package com.tecknobit.equinox.environment.helpers.services;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.equinox.environment.helpers.services.repositories.EquinoxUsersRepository;
import com.tecknobit.equinox.environment.records.EquinoxUser;
import com.tecknobit.equinox.resourcesutils.ResourcesManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
import static com.tecknobit.equinox.environment.records.EquinoxItem.IDENTIFIER_KEY;
import static com.tecknobit.equinox.environment.records.EquinoxUser.*;
import static java.lang.System.currentTimeMillis;

/**
 * The {@code EquinoxUsersHelper} class is useful to manage all the user database operations
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ResourcesManager
 *
 * @since 1.0.1
 */
@Service
@Transactional
public class EquinoxUsersHelper<T extends EquinoxUser> implements ResourcesManager {

    //TODO: TO COMMENT
    protected static final String SINGLE_QUOTE = "'";

    //TODO: TO COMMENT
    protected static final String COMMA = ",";

    //TODO: TO COMMENT
    protected static final String ROUND_BRACKET = ")";

    //TODO: TO COMMENT
    protected static final String VALUES_QUERY_PART = " VALUES (";

    //TODO: TO COMMENT
    protected static final String BASE_SIGN_UP_QUERY = "INSERT INTO " + USERS_KEY + "(";

    //TODO: TO COMMENT
    protected static final List<String> DEFAULT_USER_VALUES_KEYS = List.of(DISCRIMINATOR_VALUE_KEY, IDENTIFIER_KEY,
            TOKEN_KEY, NAME_KEY, SURNAME_KEY, EMAIL_KEY, PASSWORD_KEY, LANGUAGE_KEY);

    /**
     * {@code usersRepository} instance for the users repository
     */
    @Autowired
    private EquinoxUsersRepository<T> usersRepository;

    /**
     * {@code discriminatorValue} value of the discriminator to use to save the users in the related table
     */
    protected String discriminatorValue;

    /**
     * {@code entityManager} entity manager helper
     */
    @PersistenceContext
    protected EntityManager entityManager;

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
     */
    //TODO: TO COMMENT
    public int signUpUser(String id, String token, String name, String surname, String email, String password,
                          String language, Object... custom) throws NoSuchAlgorithmException {
        StringBuilder queryBuilder = new StringBuilder(BASE_SIGN_UP_QUERY);
        arrangeQuery(queryBuilder, getQueryValuesKeys(), false);
        List<Object> values = new ArrayList<>(List.of(discriminatorValue, id, token, name, surname, email, hash(password),
                language));
        values.addAll(Arrays.stream(custom).toList());
        queryBuilder.append(VALUES_QUERY_PART);
        arrangeQuery(queryBuilder, values, true);
        return entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();
    }

    //TODO: TO COMMENT
    protected List<String> getQueryValuesKeys() {
        return DEFAULT_USER_VALUES_KEYS;
    }

    //TODO: TO COMMENT
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
                queryBuilder.append(ROUND_BRACKET);
        }
    }

    /**
     * Method to sign in an existing user
     *
     * @param email:    the email of the user
     * @param password: the password of the user
     * @return the authenticated user as {@link EquinoxUser} if the credentials inserted were correct
     */
    //TODO: TO COMMENT
    public T signInUser(String email, String password, Object... custom) throws NoSuchAlgorithmException {
        T user = usersRepository.findUserByEmail(email);
        if (validateSignIn(user, password, custom))
            return user;
        return null;
    }

    //TODO: TO COMMENT
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