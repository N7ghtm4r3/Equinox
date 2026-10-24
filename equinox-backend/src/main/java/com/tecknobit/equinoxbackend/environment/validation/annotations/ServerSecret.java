package com.tecknobit.equinoxbackend.environment.validation.annotations;

import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.validation.validators.ServerSecretValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code ServerSecret} annotation is useful to validate the server secret used to protect backend requests
 *
 * <p>Blank and null values are rejected. When server protection is enabled, the value must match the configured server secret
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Constraint(validatedBy = ServerSecretValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ServerSecret {

    /**
     * The validation message, defaulting to {@link EquinoxMessages#NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE}
     */
    String message() default EquinoxMessages.NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE;

    /**
     * The validation groups associated with this constraint, empty by default
     */
    Class<?>[] groups() default {};

    /**
     * The payload types associated with this constraint, empty by default
     */
    Class<? extends Payload>[] payload() default {};

}