The `Email` annotation validates email addresses using the rules provided by
`InputsValidator`. It rejects null values, values that do not match the core email pattern, and values
longer than 75 characters.
The default validation message is `wrong_email`

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;

    public record ProfileRequest(@Email String email) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Email

    data class ProfileRequest(@field:Email val email: String?)
    ```

See the [validation guide](index.md#usage) to enable request validation
