---
title: "Surname"
slug: "backend/Validation/Surname"
---

The `Surname` annotation validates surnames using the rules provided by
`InputsValidator`. It rejects null or blank values and values longer than 30 characters.
The default validation message is `wrong_surname`

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname;

public record ProfileRequest(@Surname String surname) {}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname

data class ProfileRequest(@field:Surname val surname: String?)
```

</div>

</div>

See the [validation guide](/Equinox/backend/Validation/#usage) to enable request validation
