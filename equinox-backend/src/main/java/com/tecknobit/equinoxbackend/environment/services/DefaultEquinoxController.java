package com.tecknobit.equinoxbackend.environment.services;

import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
import com.tecknobit.equinoxbackend.environment.services.users.service.EquinoxUsersService;

/**
 * The {@code DefaultEquinoxController} class is useful to give the base behavior of the <b>Equinox's controllers</b>
 * with the default {@link EquinoxUser} class and for a greater clarity during your own implementation:
 *
 * <pre>
 * {@code
 *
 *     @RestController
 *     public SomeCustomController extends EquinoxController<EquinoxUser> {
 *          // rest of the class
 *     }
 *
 *     @RestController
 *     public SomeCustomControllerTwo extends EquinoxController<EquinoxUser> {
 *          // rest of the class
 *     }
 *
 *     // wrap the declaration for a better readability
 *     @RestController
 *     public SomeCustomController extends DefaultEquinoxController {
 *          // rest of the class
 *     }
 *
 *      // wrap the declaration for a better readability
 *     @RestController
 *     public SomeCustomControllerTwo extends DefaultEquinoxController {
 *          // rest of the class
 *     }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.2
 */
public abstract class DefaultEquinoxController extends EquinoxController<EquinoxUser, EquinoxUsersRepository<EquinoxUser>,
        EquinoxUsersService<EquinoxUser, EquinoxUsersRepository<EquinoxUser>>> {
}
