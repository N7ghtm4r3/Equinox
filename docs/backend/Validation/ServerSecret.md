The `ServerSecret` annotation validates the server secret used to protect backend requests. Null, empty, and whitespace-only values are always rejected. When the server protector is enabled, the value must also match the configured server secret. When it is disabled, any non-blank value passes.
The default validation message is `not_authorized`

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.validation.annotations.ServerSecret;

    public record Request(@ServerSecret String serverSecret) {}
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.validation.annotations.ServerSecret

    data class Request(@field:ServerSecret val serverSecret: String?)
    ```

See the [validation guide](index.md#usage) to enable request validation
