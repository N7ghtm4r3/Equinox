package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxcore.annotations.Validator;
import com.tecknobit.equinoxcore.helpers.InputsValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;

/**
 * The {@code EmailValidator} class is useful to validate email values with {@link InputsValidator}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ConstraintValidator
 *
 * @since 2.0.0
 */
@ConstraintValidatorDefinition(validates = Email.class)
public class EmailValidator implements ConstraintValidator<Email, String> {

    /**
     * Method used to validate whether the email matches the email pattern and does not exceed 75 characters
     *
     * @param email   The email value to validate, or {@code null} to obtain {@code false}
     * @param context The constraint validation context, unused by this implementation
     *
     * @return whether the email is valid as {@code boolean}
     */
    @Override
    @Validator
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return InputsValidator.isEmailValid(email);
    }

}
