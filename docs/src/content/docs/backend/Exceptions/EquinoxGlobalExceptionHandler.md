---
title: "EquinoxGlobalExceptionHandler"
slug: "backend/Exceptions/EquinoxGlobalExceptionHandler"
---

Converts `EquinoxException` instances and request validation failures into HTTP responses.
It is available in `com.tecknobit.equinoxbackend.environment.exceptions`

## Registration

The handler must be registered in the Spring application context through component
scanning or an explicit import. Spring injects the configured `MessageSource`

If your application does not scan the handler package, import it from a configuration class


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EquinoxGlobalExceptionHandler.class)
public class ExceptionConfiguration {}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxGlobalExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(EquinoxGlobalExceptionHandler::class)
class ExceptionConfiguration
```

</div>

</div>

## Equinox exceptions

The handler resolves the exception message using its key, formatting arguments, and
locale, then returns the corresponding HTTP status with this response structure

```json
{
  "status": "FAILED",
  "response": "Email is not valid"
}
```

If message resolution throws `NoSuchMessageException`, the handler tries
`unknown_server_error` with the same arguments and locale

The supplied `MessageSourceConfig` returns unresolved keys as messages, so a missing
translation normally returns the key instead of activating this fallback

## Request validation

For `MethodArgumentNotValidException`, the handler returns HTTP 400 using the first
field error. Its default message is resolved as a message key with the current locale

If no field message is available, the handler uses `EquinoxBadRequestException`.
If message resolution throws `NoSuchMessageException`, it returns HTTP 400 without a body

See the [validation guide](/Equinox/backend/Validation/) for request DTO examples

## Customization

Extend the handler to override its protected methods

| Method | Purpose |
| --- | --- |
| `resolveExceptionError(exception)` | Selects the HTTP response status |
| `getInternationalizedMessage(exception)` | Resolves the exception message |
| `assembleResponse(message)` | Builds the response body |
