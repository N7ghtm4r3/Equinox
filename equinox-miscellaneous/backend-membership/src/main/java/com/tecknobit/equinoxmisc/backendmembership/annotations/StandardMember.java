package com.tecknobit.equinoxmisc.backendmembership.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code StandardMember} annotation is useful to mark methods intended for standard members
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface StandardMember {

}