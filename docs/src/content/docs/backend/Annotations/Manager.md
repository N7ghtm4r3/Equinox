---
title: "Manager"
slug: "backend/Annotations/Manager"
---

The `Manager` annotation identifies backend interfaces that define endpoints shared by multiple
controllers. Its `services` parameter specifies the services used by the annotated interface

It belongs to the `com.tecknobit.equinoxbackend.annotations` package

## Usage

The following interface declares the endpoints shared by your controllers and identifies
`ItemsService` as the service it uses. Replace this example service with your own service class


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.Manager;

@Manager(services = {ItemsService.class})
public interface ItemsManager {
    // shared endpoint declarations
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.Manager

@Manager(services = [ItemsService::class])
interface ItemsManager {
    // shared endpoint declarations
}
```

</div>

</div>

The `services` parameter accepts multiple service classes. It is optional and defaults to an empty array
