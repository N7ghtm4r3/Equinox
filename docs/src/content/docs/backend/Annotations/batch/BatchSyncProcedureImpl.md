---
title: "BatchSyncProcedureImpl"
slug: "backend/Annotations/batch/BatchSyncProcedureImpl"
---

This annotation is useful to indicate an implementation of [BatchSynchronizationProcedure](/Equinox/backend/APIs/BatchSynchronizationProcedure/).
It belongs to the `com.tecknobit.equinoxbackend.annotations.batch` package.

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.batch.BatchSyncProcedureImpl;

@BatchSyncProcedureImpl(
    // not mandatory
    description = """
        Informative description about the behavior of the implemented procedure"
        """ // suggested text block
)
public class SimpleBatchQuerySyncProcedure extends BatchSynchronizationProcedure<Integer, String, SimpleCalendarBatchItem> {

    // ... rest of the procedure ...

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.batch.BatchSyncProcedureImpl

@BatchSyncProcedureImpl(
    // not mandatory
    description = """
        Informative description about the behavior of the implemented procedure"
        """ // suggested text block
)
class SimpleBatchQuerySyncProcedure : BatchSynchronizationProcedure<Int, String, SimpleCalendarBatchItem>() {

    // ... rest of the procedure ...

}
```

</div>

</div>
