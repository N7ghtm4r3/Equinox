---
title: "FiltersAdder"
slug: "backend/Annotations/filters/FiltersAdder"
---

This annotation is useful to indicate those methods used to adding the extracted filters to a [FilteredQuery](/Equinox/backend/APIs/FilteredQuery/).
It belongs to the `com.tecknobit.equinoxbackend.annotations.filters` package.

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.filters.FiltersAdder;

@FiltersAdder(
    description = "Will be added to the query the plates such AA000AA, etc..."
)
private void addPlates() {
    // the rest of the method
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.filters.FiltersAdder

@FiltersAdder(
    description = "Will be added to the query the plates such AA000AA, etc..."
)
private fun addPlates() {
    // the rest of the method
}
```

</div>

</div>
