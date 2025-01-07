package com.tecknobit.equinoxbackend.environment.services.users.repository;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.IDENTIFIER_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

/**
 * The {@code UsersRepository} interface is useful to manage the queries for the users operations
 *
 * @param <T>: generic type to allow the use of custom users inherited from {@link EquinoxUser}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see JpaRepository
 * @see EquinoxUser
 *
 * @since 1.0.1
 */
@Service
@Repository
public interface EquinoxUsersRepository<T extends EquinoxUser> extends JpaRepository<T, String> {

    /**
     * Method to execute the query to find a {@link EquinoxUser} by email field
     *
     * @param email: the email to find the user
     * @return the user, if exists, as {@link EquinoxUser}
     */
    @Query(
            value = "SELECT * FROM " + USERS_KEY + " WHERE " + EMAIL_KEY + "=:" + EMAIL_KEY,
            nativeQuery = true
    )
    T findUserByEmail(
            @Param(EMAIL_KEY) String email
    );

    /**
     * Method to execute the query to change the profile pic of the {@link EquinoxUser}
     *
     * @param profilePicUrl: the profile pic formatted as url
     * @param id:            the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + PROFILE_PIC_KEY + "=:" + PROFILE_PIC_KEY + " WHERE "
                    + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeProfilePic(
            @Param(PROFILE_PIC_KEY) String profilePicUrl,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method to execute the query to change the email of the {@link EquinoxUser}
     *
     * @param newEmail: the new email of the user
     * @param id:       the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + EMAIL_KEY + "=:" + EMAIL_KEY + " WHERE "
                    + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeEmail(
            @Param(EMAIL_KEY) String newEmail,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method to execute the query to change the password of the {@link EquinoxUser}
     *
     * @param newPassword: the new password of the user
     * @param id:          the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + PASSWORD_KEY + "=:" + PASSWORD_KEY + " WHERE "
                    + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changePassword(
            @Param(PASSWORD_KEY) String newPassword,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method to execute the query to change the language of the {@link EquinoxUser}
     *
     * @param newLanguage: the new language of the user
     * @param id:          the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + LANGUAGE_KEY + "=:" + LANGUAGE_KEY + " WHERE "
                    + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeLanguage(
            @Param(LANGUAGE_KEY) String newLanguage,
            @Param(IDENTIFIER_KEY) String id
    );

}