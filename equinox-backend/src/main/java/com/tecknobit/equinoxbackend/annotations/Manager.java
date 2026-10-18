package com.tecknobit.equinoxbackend.annotations;

/**
 * The {@code Manager} annotation is useful to mark backend interfaces that define
 * endpoints shared by multiple controllers
 *
 * <pre>
 * {@code
 * @Manager(services = {ItemsService.class})
 * public interface ItemsManager {
 *
 *     // shared endpoint declarations
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public @interface Manager {

    /**
     * The list of the services used by the annotated interface
     */
    Class<?>[] services() default {};

}
