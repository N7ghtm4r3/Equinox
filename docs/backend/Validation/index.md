# Overview

The backend provides `Name`, `Surname`, and `Email` constraints for validating string
values with Jakarta Bean Validation. They are available in
`com.tecknobit.equinoxbackend.environment.validation.annotations`

| Constraint | Validation |
| --- | --- |
| [Name](Name.md) | Rejects null, blank values, and names longer than 20 characters |
| [Surname](Surname.md) | Rejects null, blank values, and surnames longer than 30 characters |
| [Email](Email.md) | Combines email format validation with a maximum length of 75 characters and rejects null or empty values |

Each constraint exposes `message`, `groups`, and `payload`. Their default message
keys are `wrong_name`, `wrong_surname`, and `wrong_email`, respectively

## Usage

Apply the constraints to your request fields

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Name;
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname;

    public record ProfileRequest(
        @Name String name,
        @Surname String surname,
        @Email String email
    ) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Name
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname

    data class ProfileRequest(
        @field:Name val name: String?,
        @field:Surname val surname: String?,
        @field:Email val email: String?
    )
    ```

Use `@Valid` on the controller parameter to request validation

=== "Java"

    ```java
    import jakarta.validation.Valid;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class ProfileController {

        @PostMapping("/profile/validate")
        public void validateProfile(@Valid @RequestBody ProfileRequest request) {}
    }
    ```

=== "Kotlin"

    ```kotlin
    import jakarta.validation.Valid
    import org.springframework.web.bind.annotation.PostMapping
    import org.springframework.web.bind.annotation.RequestBody
    import org.springframework.web.bind.annotation.RestController

    @RestController
    class ProfileController {

        @PostMapping("/profile/validate")
        fun validateProfile(@Valid @RequestBody request: ProfileRequest) {}
    }
    ```

## Validator implementations

`NameValidator`, `SurnameValidator`, and `EmailValidator` implement
`ConstraintValidator` and delegate to the corresponding methods of
[InputsValidator](../../../equinox-core/src/commonMain/kotlin/com/tecknobit/equinoxcore/helpers/InputsValidator.kt).
All three reject null values. `EmailValidator` additionally checks the core email
pattern and the 75-character limit

`Name`, `Surname`, and `Email` reference their corresponding Equinox validators
through `@Constraint(validatedBy = ...)`. `EmailValidator` implements the contract
for the custom Equinox `Email` annotation

The [ConstraintValidatorDefinition](../Annotations/ConstraintValidatorDefinition.md)
marker documents a validator association without registering it. The marker on
`SurnameValidator` currently names `Name.class`, while its implemented validation
contract is for `Surname`
