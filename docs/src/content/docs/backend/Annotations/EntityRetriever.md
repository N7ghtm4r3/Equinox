---
title: "EntityRetriever"
slug: "backend/Annotations/EntityRetriever"
---

The `EntityRetriever` annotation identifies methods that retrieve a persisted
entity from the database through a repository

## Usage

Apply the annotation to the method responsible for retrieving the entity.
The repository call and missing-entity handling remain part of the method's
implementation


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.EntityRetriever;

@EntityRetriever
public Item retrieveItem(String id) {
    return itemsRepository.findById(id).orElseThrow();
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.EntityRetriever

@EntityRetriever
fun retrieveItem(id: String): Item {
    return itemsRepository.findById(id).orElseThrow()
}
```

</div>

</div>

The examples assume an existing `Item` entity and an `itemsRepository`
providing `findById`
