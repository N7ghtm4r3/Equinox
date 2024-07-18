package com.tecknobit.equinox.environment.helpers.services;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.equinox.environment.helpers.services.repositories.EquinoxUsersRepository;
import com.tecknobit.equinox.environment.records.EquinoxUser;
import com.tecknobit.equinox.resourcesutils.ResourcesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;

import static com.tecknobit.apimanager.apis.APIRequest.SHA256_ALGORITHM;
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
public class EquinoxUsersHelper<T extends EquinoxUser> implements ResourcesManager {

    /**
     * {@code usersRepository} instance for the users repository
     */
    @Autowired
    private EquinoxUsersRepository<T> usersRepository;

    /**
     * {@code discriminatorValue} value of the discriminator to use to save the users in the related table
     */
    private String discriminatorValue;

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
    public void signUpUser(String id, String token, String name, String surname, String email, String password,
                           String language) throws NoSuchAlgorithmException {
        usersRepository.saveUser(
                discriminatorValue,
                id,
                token,
                name,
                surname,
                email,
                hash(password),
                language
        );
    }

    /**
     * Method to sign in an existing user
     *
     * @param email:    the email of the user
     * @param password: the password of the user
     * @return the authenticated user as {@link EquinoxUser} if the credentials inserted were correct
     */
    public EquinoxUser signInUser(String email, String password) throws NoSuchAlgorithmException {
        EquinoxUser user = usersRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(hash(password)))
            return user;
        return null;
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
    private String hash(String secret) throws NoSuchAlgorithmException {
        return APIRequest.base64Digest(secret, SHA256_ALGORITHM);
    }

}