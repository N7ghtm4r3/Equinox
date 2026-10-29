---
title: "BatchQueryImpl"
slug: "backend/Annotations/batch/BatchQueryImpl"
---

This annotation is useful to indicate an implementation of [BatchQuery](/Equinox/backend/APIs/EquinoxItemsHelper/#insert-in-batch).
It belongs to the `com.tecknobit.equinoxbackend.annotations.batch` package.

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryImpl;

@BatchQueryImpl(
    // not mandatory
    description = """
        Informative description about the behavior of the implemented batch query"
    """ // suggested text block
)
public class SimpleBatchQuery implements EquinoxItemsHelper.BatchQuery<Simple> {

    ...
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryImpl

@BatchQueryImpl(
    // not mandatory
    description = """
        Informative description about the behavior of the implemented batch query"
    """ // suggested text block
)
class SimpleBatchQuery : EquinoxItemsHelper.BatchQuery<Simple> {

    ...
}
```

</div>

</div>
