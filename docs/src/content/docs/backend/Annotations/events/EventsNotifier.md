---
title: "EventsNotifier"
slug: "backend/Annotations/events/EventsNotifier"
---

This annotation is useful to mark classes that send events, such as [EquinoxEventsEmitter](/Equinox/backend/APIs/EquinoxEvents/#create-the-emitter).
Its purpose is to improve the readability of the code

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.events.EventsNotifier;

@Service // required annotation
@EventsNotifier
public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {

    // your custom implementation

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.events.EventsNotifier

@Service // required annotation
@EventsNotifier
class TestEventsEmitter : EquinoxEventsEmitter<TestEvent>() {

    // your custom implementation

}
```

</div>

</div>
