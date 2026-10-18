The `Surname` annotation validates surnames using the rules provided by
`InputsValidator`. It rejects null or blank values and values longer than 30 characters.
The default validation message is `wrong_surname`

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname;

    public record ProfileRequest(@Surname String surname) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname

    data class ProfileRequest(@field:Surname val surname: String?)
    ```

See the [validation guide](index.md#usage) to enable request validation
