package com.tecknobit.equinoxmisc.backendmembership.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code PrivilegedMember} annotation is useful to mark methods intended for privileged members
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface PrivilegedMember {

    /**
     * Method used to specify the role label associated with the marked method
     *
     * @return the role label, or an empty string when no label is specified, as {@link String}
     */
    String role() default "";

}