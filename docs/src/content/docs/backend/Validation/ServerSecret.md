---
title: "ServerSecret"
slug: "backend/Validation/ServerSecret"
---

The `ServerSecret` annotation validates the server secret used to protect backend requests. Null, empty, and whitespace-only values are always rejected. When the server protector is enabled, the value must also match the configured server secret. When it is disabled, any non-blank value passes.
The default validation message is `not_authorized`

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.validation.annotations.ServerSecret;

public record Request(@ServerSecret String serverSecret) {}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.validation.annotations.ServerSecret

data class Request(@field:ServerSecret val serverSecret: String?)
```

</div>

</div>

See the [validation guide](/Equinox/backend/Validation/#usage) to enable request validation
