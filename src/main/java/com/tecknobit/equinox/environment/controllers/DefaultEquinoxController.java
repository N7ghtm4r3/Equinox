package com.tecknobit.equinox.environment.controllers;

import com.tecknobit.equinox.environment.records.EquinoxUser;

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
public abstract class DefaultEquinoxController extends EquinoxController<EquinoxUser> {
}
