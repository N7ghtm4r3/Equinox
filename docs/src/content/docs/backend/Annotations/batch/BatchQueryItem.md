---
title: "BatchQueryItem"
slug: "backend/Annotations/batch/BatchQueryItem"
---

This annotation is useful to indicate an object which its scope is the support to handle the data and manipulate them 
during the batch operations

It belongs to the `com.tecknobit.equinoxbackend.annotations.batch` package.

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryItem;

@BatchQueryItem(
    // not mandatory
    description = """
        Informative description about the scope of the this item, 
        such the operation where is used what data are manipulated, etc...
        """ // suggested text block
)
public class SimpleItem {

    private final String id;

    private final String ownedEntityId;

    ... rest of the item ...

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryItem

@BatchQueryItem(
    // not mandatory
    description = """
        Informative description about the scope of the this item, 
        such the operation where is used what data are manipulated, etc...
        """ // suggested text block
)
class SimpleItem(

    private val id: String,

    private val ownedEntityId: String

    // ... rest of the item ...

)
```

</div>

</div>
