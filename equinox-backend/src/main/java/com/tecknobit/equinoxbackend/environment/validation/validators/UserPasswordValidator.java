package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserPassword;
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
@ConstraintValidatorDefinition(validates = UserPassword.class)
public class UserPasswordValidator implements ConstraintValidator<UserPassword, String> {

    @Override
    @Validator
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return InputsValidator.isPasswordValid(password);
    }

}
