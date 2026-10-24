The `UserLanguage` annotation validates user languages against `InputsValidator.SUPPORTED_LANGUAGES`. A value must match a supported language key or value. Null, empty, and unsupported values are rejected. Validation does not assign a default language.
The default validation message is `wrong_language`

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage;

    public record Request(@UserLanguage String language) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage

    data class Request(@field:UserLanguage val language: String?)
    ```

See the [validation guide](index.md#usage) to enable request validation
