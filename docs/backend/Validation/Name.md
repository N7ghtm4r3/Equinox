The `Name` annotation validates names using the rules provided by
`InputsValidator`. It rejects null or blank values and values longer than 20 characters.
The default validation message is `wrong_name`

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Name;

    public record ProfileRequest(@Name String name) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.Name

    data class ProfileRequest(@field:Name val name: String?)
    ```

See the [validation guide](index.md#usage) to enable request validation
