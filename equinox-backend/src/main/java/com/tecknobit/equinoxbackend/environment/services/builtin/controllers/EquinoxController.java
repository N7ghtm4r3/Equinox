package com.tecknobit.equinoxbackend.environment.services.builtin.controllers;

import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;
import com.tecknobit.equinoxcore.annotations.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;
import static com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.BASE_EQUINOX_ENDPOINT;

/**
 * The {@code EquinoxController} class provides the basic behavior of the <b>Equinox's controllers</b>
 * which use and require the users handling ({@link T}, {@link R}, {@link H})
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param <T> The type of the {@link EquinoxUser} used in the system, is generic to avoid manual casts if it has been customized
 * @param <R> The type of the {@link EquinoxUsersRepository} used in the system, is generic to avoid manual casts if it has been customized
 * @param <H> The type of the {@link EquinoxUsersService} used in the system, is generic to avoid manual casts if it has been customized
 *
 * @see EquinoxControllerCore
 *
 * @since 1.0.1
 */
@RestController
@RequestMapping(BASE_EQUINOX_ENDPOINT)
abstract public class EquinoxController<T extends EquinoxUser, R extends EquinoxUsersRepository<T>,
        H extends EquinoxUsersService<T, R>> extends EquinoxControllerCore {

    /**
     * {@code usersRepository} instance for the user repository
     */
    @Autowired(required = false)
    protected R usersRepository;

    /**
     * {@code me} user representing the user who made a request on the server
     */
    protected T me;

    /**
     * Method used to check whether the user who made a request is an authorized user <br>
     * If the user is authorized the {@link #me} instance is loaded
     *
     * @param id The identifier of the user
     * @param token The token of the user
     * @return whether the user is an authorized user as boolean
     */
    @Validator
    protected boolean isMe(String id, String token) {
        Optional<T> query = usersRepository.findById(id);
        me = query.orElse(null);
        boolean isMe = me != null && me.getToken().equals(token);
        if (!isMe) {
            me = null;
            setSessionLocale(DEFAULT_LANGUAGE);
        } else
            setSessionLocale(me.getLanguage());
        return isMe;
    }

}
