package com.tecknobit.equinoxbackend.environment.security.authorization.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * The {@code IsAuthorizedUser} annotation is useful to mark methods or types whose
 * access requires the requested user identifier to match the authenticated principal identifier
 *
 * <p>The {@link PreAuthorize} expression compares the {@code userId} method argument
 * with {@code authentication.principal.id}. Its enforcement requires Spring Security
 * method authorization to be enabled, the {@code userId} argument to be resolvable by
 * name, and the authenticated principal to expose an {@code id} property
 *
 * <pre>
 * {@code
 * @IsAuthorizedUser
 * public void updateUser(String userId) {
 *     // update the requested user
 * }
 * }
 * </pre>
 *
 * <p>The example requires the method argument name to be available to the authorization
 * expression. When applied to a type, the same expression applies to its secured methods
 *
 * <p>The annotation is retained at runtime and is inherited by subclasses when applied
 * to a type
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#userId == authentication.principal.id")
public @interface IsAuthorizedUser {

}
