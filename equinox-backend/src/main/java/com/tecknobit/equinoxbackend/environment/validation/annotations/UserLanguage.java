package com.tecknobit.equinoxbackend.environment.validation.annotations;

import com.tecknobit.equinoxbackend.environment.constants.EquinoxMessages;
import com.tecknobit.equinoxbackend.environment.validation.validators.UserLanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@code UserLanguage} annotation is useful to validate supported user languages
 *
 * <p>The value must match a key or value in {@code InputsValidator.SUPPORTED_LANGUAGES}. Null and empty values are rejected; no default language is assigned
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Constraint(validatedBy = UserLanguageValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@NotEmpty(message = EquinoxMessages.WRONG_LANGUAGE_MESSAGE)
public @interface UserLanguage {

    /**
     * The validation message, defaulting to {@link EquinoxMessages#WRONG_LANGUAGE_MESSAGE}
     */
    String message() default EquinoxMessages.WRONG_LANGUAGE_MESSAGE;

    /**
     * The validation groups associated with this constraint, empty by default
     */
    Class<?>[] groups() default {};

    /**
     * The payload types associated with this constraint, empty by default
     */
    Class<? extends Payload>[] payload() default {};

}
