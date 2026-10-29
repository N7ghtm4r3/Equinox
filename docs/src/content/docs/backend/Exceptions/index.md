---
title: "Overview"
slug: "backend/Exceptions"
---

<span id="overview"></span>

The backend provides exceptions for rejected requests, forbidden operations, and server
failures. They are available in
`com.tecknobit.equinoxbackend.environment.exceptions`

The [global exception handler](/Equinox/backend/Exceptions/EquinoxGlobalExceptionHandler/) converts these exceptions
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

See [EquinoxMessages](/Equinox/backend/APIs/EquinoxMessages/) for the supplied message keys

## Usage

Throw the exception matching the failed operation


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxBadRequestException;

throw new EquinoxBadRequestException("wrong_email");
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxBadRequestException

throw EquinoxBadRequestException("wrong_email")
```

</div>

</div>

## Custom status

Use `EquinoxException` to supply another HTTP status and a message key defined in
your [custom resource bundle](/Equinox/backend/Environment/#customizing-the-messages-resource-bundle)


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxException;

throw new EquinoxException("item_conflict", 409);
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxException

throw EquinoxException("item_conflict", 409)
```

</div>

</div>
