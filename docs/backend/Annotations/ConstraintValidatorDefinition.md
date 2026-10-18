The `ConstraintValidatorDefinition` annotation identifies constraint validator
classes. Its required `validates` parameter specifies the constraint annotation
that invokes the annotated validator

This association is documented at source level. The annotation does not register
the validator or trigger validation

## Usage

Apply the annotation to your validator class and specify the corresponding
constraint annotation

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;

    @ConstraintValidatorDefinition(validates = Version.class)
    public class VersionValidator implements ConstraintValidator<Version, String> {
        // validator implementation
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition

    @ConstraintValidatorDefinition(validates = Version::class)
    class VersionValidator : ConstraintValidator<Version, String> {
        // validator implementation
    }
    ```

The examples assume an existing `Version` constraint annotation and the
corresponding `ConstraintValidator` interface. Implement its required methods
in your validator class
