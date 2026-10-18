package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Name;
import com.tecknobit.equinoxcore.annotations.Validator;
import com.tecknobit.equinoxcore.helpers.InputsValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The {@code NameValidator} class is useful to validate name values with {@link InputsValidator}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ConstraintValidator
 *
 * @since 2.0.0
 */
@ConstraintValidatorDefinition(validates = Name.class)
public class NameValidator implements ConstraintValidator<Name, String> {

    /**
     * Method used to validate whether the name contains a non-whitespace character and does not exceed 20 characters
     *
     * @param name    The name value to validate, or {@code null} to obtain {@code false}
     * @param context The constraint validation context, unused by this implementation
     *
     * @return whether the name is valid as {@code boolean}
     */
    @Override
    @Validator
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return InputsValidator.isNameValid(name);
    }

}
