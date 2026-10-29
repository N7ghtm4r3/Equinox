---
title: "UserLanguage"
slug: "backend/Validation/UserLanguage"
---

The `UserLanguage` annotation validates user languages against `InputsValidator.SUPPORTED_LANGUAGES`. A value must match a supported language key or value. Null, empty, and unsupported values are rejected. Validation does not assign a default language.
The default validation message is `wrong_language`

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage;

public record Request(@UserLanguage String language) {}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage

data class Request(@field:UserLanguage val language: String?)
```

</div>

</div>

See the [validation guide](/Equinox/backend/Validation/#usage) to enable request validation
