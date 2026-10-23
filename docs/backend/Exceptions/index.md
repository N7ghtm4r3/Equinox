# Overview

The backend provides exceptions for rejected requests, forbidden operations, and server
failures. They are available in
`com.tecknobit.equinoxbackend.environment.exceptions`

The [global exception handler](EquinoxGlobalExceptionHandler.md) converts these exceptions
into localized HTTP responses

## Provided exceptions

| Exception | HTTP status | Default message key |
| --- | --- | --- |
| `EquinoxBadRequestException` | 400 | `wrong_procedure` |
| `EquinoxForbiddenException` | 403 | `not_authorized` |
| `EquinoxServerException` | 500 | `server_error` |

All three extend `EquinoxException`. Constructors accept a custom message key,
formatting arguments, and a locale. Without an explicit locale, the exception captures
the current Spring locale when created

See [EquinoxMessages](../APIs/EquinoxMessages.md) for the supplied message keys

## Usage

Throw the exception matching the failed operation

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxBadRequestException;

    throw new EquinoxBadRequestException("wrong_email");
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxBadRequestException

    throw EquinoxBadRequestException("wrong_email")
    ```

## Custom status

Use `EquinoxException` to supply another HTTP status and a message key defined in
your [custom resource bundle](../Environment.md#customizing-the-messages-resource-bundle)

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxException;

    throw new EquinoxException("item_conflict", 409);
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxException

    throw EquinoxException("item_conflict", 409)
    ```
