package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.ElementType.TYPE;

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
@Target(value = {TYPE, RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface Manager {

    /**
     * The list of the services used by the annotated interface
     */
    Class<?>[] services() default {};

}
