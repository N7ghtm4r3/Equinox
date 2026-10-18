package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Name;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname;
import com.tecknobit.equinoxcore.annotations.Validator;
import com.tecknobit.equinoxcore.helpers.InputsValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The {@code SurnameValidator} class is useful to validate surname values with {@link InputsValidator}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ConstraintValidator
 *
 * @since 2.0.0
 */
@ConstraintValidatorDefinition(validates = Name.class)
public class SurnameValidator implements ConstraintValidator<Surname, String> {

    /**
     * Method used to validate whether the surname contains a non-whitespace character and does not exceed 30 characters
     *
     * @param surname The surname value to validate, or {@code null} to obtain {@code false}
     * @param context The constraint validation context, unused by this implementation
     *
     * @return whether the surname is valid as {@code boolean}
     */
    @Override
    @Validator
    public boolean isValid(String surname, ConstraintValidatorContext context) {
        return InputsValidator.isSurnameValid(surname);
    }

}
