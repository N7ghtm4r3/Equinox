---
title: "FiltersUtil"
slug: "backend/Utils/FiltersUtil"
---

The `FiltersUtil` class provides utility methods to work with filters in your backend

## resolveEnumSetFilters

Use `resolveEnumSetFilters` when your filters are enum values and you need their names as text.
The method turns your selection into a set of names matching the enum declaration

Pass the selected values as `filters` and their enum class as `enumType`.
If the selection is empty or `null`, the result includes every value from that enum

### Enum example

Define the possible values for your filter


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
enum Status {
    ACTIVE,
    INACTIVE
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
enum class Status {
    ACTIVE,
    INACTIVE
}
```

</div>

</div>

### Usage


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.utils.FiltersUtil;

import java.util.Set;

Set<String> selected = FiltersUtil.resolveEnumSetFilters(Set.of(Status.ACTIVE), Status.class);
// Contains "ACTIVE"
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.utils.FiltersUtil

val selected = FiltersUtil.resolveEnumSetFilters(setOf(Status.ACTIVE), Status::class.java)
// Contains "ACTIVE"
```

</div>

</div>
