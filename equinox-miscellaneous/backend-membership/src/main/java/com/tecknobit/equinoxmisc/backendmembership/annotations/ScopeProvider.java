package com.tecknobit.equinoxmisc.backendmembership.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code ScopeProvider} annotation is useful to mark types and record components that provide a membership scope
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Target({ElementType.TYPE, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface ScopeProvider {

}