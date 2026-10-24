# Overview

Use the backend constraints to validate request fields with Jakarta Bean Validation

| Constraint | Validates |
| --- | --- |
| [Name](Name.md) | Names up to 20 characters |
| [Surname](Surname.md) | Surnames up to 30 characters |
| [Email](Email.md) | Email addresses up to 75 characters |
| [UserLanguage](UserLanguage.md) | Supported language keys or values |
| [ServerSecret](ServerSecret.md) | The server protection secret |

All constraints reject null and empty values. See each constraint's page for its
complete rules and default validation message

## Usage

Annotate your request fields and add `@Valid` to the controller's request parameter

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
    import jakarta.validation.Valid;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;

    public record ProfileRequest(@Email String email) {}

    @RestController
    public class ProfileController {

        @PostMapping("/profile/validate")
        public void validateProfile(@Valid @RequestBody ProfileRequest request) {}
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email
    import jakarta.validation.Valid
    import org.springframework.web.bind.annotation.PostMapping
    import org.springframework.web.bind.annotation.RequestBody
    import org.springframework.web.bind.annotation.RestController

    data class ProfileRequest(@field:Email val email: String?)

    @RestController
    class ProfileController {

        @PostMapping("/profile/validate")
        fun validateProfile(@Valid @RequestBody request: ProfileRequest) {}
    }
    ```

Each constraint supports the standard `message`, `groups`, and `payload` attributes
