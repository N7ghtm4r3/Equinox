---
title: "EventsHandler"
slug: "backend/Annotations/events/EventsHandler"
---

This annotation is useful to mark classes that handle events, such as [EquinoxEventsCollector](/Equinox/backend/APIs/EquinoxEvents/#create-the-collector).
Its purpose is to improve the readability of the code

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.events.EventsHandler;

@EventsHandler
public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

     // your custom implementation

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.events.EventsHandler

@EventsHandler
interface TestEventsCollector : EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    // your custom implementation

}
```

</div>

</div>
