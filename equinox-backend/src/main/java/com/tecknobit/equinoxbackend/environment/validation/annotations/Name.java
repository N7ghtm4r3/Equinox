package com.tecknobit.equinoxbackend.environment.validation.annotations;

import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.validation.validators.NameValidator;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.NAME_MAX_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code Name} annotation is useful to validate name values with a maximum length of 20 characters
 *
 * <p>The value must contain at least one non-whitespace character, as checked by {@link NameValidator}. The composed constraints reject null and empty values
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Constraint(validatedBy = NameValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Size(max = NAME_MAX_LENGTH)
@NotEmpty(message = EquinoxMessages.WRONG_NAME_MESSAGE)
public @interface Name {

    /**
     * The validation message, defaulting to {@link EquinoxMessages#WRONG_NAME_MESSAGE}
     */
    String message() default EquinoxMessages.WRONG_NAME_MESSAGE;

    /**
     * The validation groups associated with this constraint, empty by default
     */
    Class<?>[] groups() default {};

    /**
     * The payload types associated with this constraint, empty by default
     */
    Class<? extends Payload>[] payload() default {};

}
