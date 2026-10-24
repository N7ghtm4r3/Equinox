package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage;
import com.tecknobit.equinoxcore.annotations.Validator;
import com.tecknobit.equinoxcore.helpers.InputsValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The {@code UserLanguageValidator} class is useful to validate supported user languages
 *
 * <p>Null values are rejected. Validation does not assign a default language
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ConstraintValidator
 *
 * @since 2.0.0
 */
@ConstraintValidatorDefinition(validates = UserLanguage.class)
public class UserLanguageValidator implements ConstraintValidator<UserLanguage, String> {

    /**
     * Method used to validate supported user languages
     *
     * @param language The language key or value to validate, or {@code null} to obtain {@code false}
     * @param context The constraint validation context, unused by this implementation
     *
     * @return whether the language matches a supported key or value as {@code boolean}
     */
    @Override
    @Validator
    public boolean isValid(String language, ConstraintValidatorContext context) {
        return InputsValidator.isLanguageValid(language);
    }

}
