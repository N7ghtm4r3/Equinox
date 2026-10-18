package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.*;

/**
 * The {@code ConstraintValidatorDefinition} annotation is useful to mark constraint validator
 * classes and indicate the constraint annotation that invokes the validator
 *
 * <p>This annotation provides source-level documentation of the association and does not
 * register the validator or trigger validation
 *
 * <pre>
 * {@code
 * @ConstraintValidatorDefinition(validates = Version.class)
 * public class VersionValidator implements ConstraintValidator<Version, String> {
 *     // implementation of the validator
 * }
 * }
 * </pre>
 *
 * <p>The example assumes an existing {@code Version} constraint annotation and its
 * {@code VersionValidator} implementation
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ConstraintValidatorDefinition {

    /**
     * The constraint annotation that invokes the annotated validator
     */
    Class<? extends Annotation> validates();

}
