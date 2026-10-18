package com.tecknobit.equinoxbackend.environment.validation.annotations;

import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.validation.validators.SurnameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.SURNAME_MAX_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code Surname} annotation is useful to validate surname values with a maximum length of 30 characters
 *
 * <p>The value must contain at least one non-whitespace character, as checked by {@link SurnameValidator}. The composed constraints reject null and empty values
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Constraint(validatedBy = SurnameValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Size(max = SURNAME_MAX_LENGTH)
@NotEmpty(message = EquinoxMessages.WRONG_SURNAME_MESSAGE)
public @interface Surname {

    /**
     * The validation message, defaulting to {@link EquinoxMessages#WRONG_SURNAME_MESSAGE}
     */
    String message() default EquinoxMessages.WRONG_SURNAME_MESSAGE;

    /**
     * The validation groups associated with this constraint, empty by default
     */
    Class<?>[] groups() default {};

    /**
     * The payload types associated with this constraint, empty by default
     */
    Class<? extends Payload>[] payload() default {};

}
