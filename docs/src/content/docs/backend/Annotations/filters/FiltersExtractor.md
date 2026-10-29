---
title: "FiltersExtractor"
slug: "backend/Annotations/filters/FiltersExtractor"
---

This annotation is useful to indicate those methods used to extract from the raw filters set
the specific set indicates by the method, its main usage is related to the [FilteredQuery](/Equinox/backend/APIs/FilteredQuery/).

It belongs to the `com.tecknobit.equinoxbackend.annotations.filters` package.

## Usage 


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.filters.FiltersExtractor;

@FiltersExtractor(
    description = "Method used to extracts the plate filters to use"
)
private HashSet<String> extractPlateFilters() {
    return extractFiltersByPattern(your_pattern); // the specific pattern to extract the plates
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.filters.FiltersExtractor

@FiltersExtractor(
    description = "Method used to extracts the plate filters to use"
)
private fun extractPlateFilters(): HashSet<String> {
    return extractFiltersByPattern(your_pattern) // the specific pattern to extract the plates
}
```

</div>

</div>
