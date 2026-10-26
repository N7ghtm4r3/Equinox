package com.tecknobit.equinoxbackend.environment.services.users.repository;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.builtin.repositories.EquinoxCrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.tecknobit.equinoxbackend.apis.database.SQLConstants._WHERE_;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

/**
 * The {@code UsersRepository} interface is useful to manage the queries for the users operations
 *
 * @param <T>: generic type to allow the use of custom users inherited from {@link EquinoxUser}
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.1
 */
@Repository
public interface EquinoxUsersRepository<T extends EquinoxUser> extends EquinoxCrudRepository<T> {

    /**
     * Method used to execute the query to find a {@link EquinoxUser} by email field
     *
     * @param email: the email to find the user
     * @return the user, if exists, as {@link EquinoxUser}
     */
    @Query(
            value = "SELECT * FROM " + USERS_KEY + _WHERE_ + EMAIL_KEY + "=:" + EMAIL_KEY,
            nativeQuery = true
    )
    T findUserByEmail(
            @Param(EMAIL_KEY) String email
    );

    /**
     * Method used to execute the query to find a user by token
     *
     * @param token The token of the user to retrieve
     *
     * @return the matching user, or an empty optional if no user matches the token, as {@link Optional} of {@link T}
     *
     * @since 2.0.0
     */
    @Query(
            value = "SELECT * FROM " + USERS_KEY +
                    _WHERE_ + TOKEN_KEY + "=:" + TOKEN_KEY,
            nativeQuery = true
    )
    Optional<T> findUserByToken(
            @Param(TOKEN_KEY) String token
    );

    /**
     * Method used to execute the query to change the profile pic of the {@link EquinoxUser}
     *
     * @param profilePicUrl: the profile pic formatted as url
     * @param id:            the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + PROFILE_PIC_KEY + "=:" + PROFILE_PIC_KEY +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeProfilePic(
            @Param(PROFILE_PIC_KEY) String profilePicUrl,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method used to execute the query to change the email of the {@link EquinoxUser}
     *
     * @param newEmail: the new email of the user
     * @param id:       the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + EMAIL_KEY + "=:" + EMAIL_KEY +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeEmail(
            @Param(EMAIL_KEY) String newEmail,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method used to execute the query to change the password of the {@link EquinoxUser}
     *
     * @param newPassword: the new password of the user
     * @param id:          the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + PASSWORD_KEY + "=:" + PASSWORD_KEY +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changePassword(
            @Param(PASSWORD_KEY) String newPassword,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method used to execute the query to change the language of the {@link EquinoxUser}
     *
     * @param newLanguage: the new language of the user
     * @param id:          the identifier of the user
     */
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + LANGUAGE_KEY + "=:" + LANGUAGE_KEY +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void changeLanguage(
            @Param(LANGUAGE_KEY) String newLanguage,
            @Param(IDENTIFIER_KEY) String id
    );

    /**
     * Method used to record the deletion timestamp of a user without removing the account
     *
     * @param id        The identifier of the user
     * @param deletedAt The deletion timestamp in milliseconds since the Unix epoch
     *
     * @since 2.0.0
     */
    @Override
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + DELETED_AT_KEY + "=:" + DELETED_AT_KEY +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void softDelete(
            @Param(IDENTIFIER_KEY) String id,
            @Param(DELETED_AT_KEY) long deletedAt
    );

    /**
     * Method used to restore a user by resetting the stored deletion timestamp to {@code -1}
     *
     * @param id The identifier of the user to restore
     *
     * @since 2.0.0
     */
    @Override
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + USERS_KEY + " SET " + DELETED_AT_KEY + "= -1" +
                    _WHERE_ + IDENTIFIER_KEY + "=:" + IDENTIFIER_KEY,
            nativeQuery = true
    )
    void restoreSoftDeletedEntity(
            @Param(IDENTIFIER_KEY) String id
    );

}
