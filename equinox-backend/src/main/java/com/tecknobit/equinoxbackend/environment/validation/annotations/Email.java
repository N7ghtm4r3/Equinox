package com.tecknobit.equinoxbackend.environment.validation.annotations;

import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.validation.validators.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.EMAIL_MAX_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code Email} annotation is useful to validate email addresses with a maximum length of 75 characters
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Constraint(validatedBy = EmailValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Size(max = EMAIL_MAX_LENGTH)
@NotEmpty(message = EquinoxMessages.WRONG_EMAIL_MESSAGE)
public @interface Email {

    /**
     * The validation message, defaulting to {@link EquinoxMessages#WRONG_EMAIL_MESSAGE}
     */
    String message() default EquinoxMessages.WRONG_EMAIL_MESSAGE;

    /**
     * The validation groups associated with this constraint, empty by default
     */
    Class<?>[] groups() default {};

    /**
     * The payload types associated with this constraint, empty by default
     */
    Class<? extends Payload>[] payload() default {};

}
