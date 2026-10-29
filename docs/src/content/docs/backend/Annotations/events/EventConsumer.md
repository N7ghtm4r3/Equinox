---
title: "EventConsumer"
slug: "backend/Annotations/events/EventConsumer"
---

This annotation is useful to mark types and methods that consume application events.
Its purpose is to improve the readability of the code

The optional `event` parameter identifies the application event class associated with the consumer.
It defaults to `EquinoxApplicationEvent.class`. This annotation is retained only in source code
and does not register listeners or filter events

## Usage

The following examples use `TestApplicationEvent` from the
[events guide](/Equinox/backend/APIs/EquinoxEvents/#create-the-equinoxapplicationevent)


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.events.EventConsumer;

public class TestEventConsumer {

    @EventConsumer(event = TestApplicationEvent.class)
    public void consume(TestApplicationEvent event) {
        // handle the event
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.events.EventConsumer

class TestEventConsumer {

    @EventConsumer(event = TestApplicationEvent::class)
    fun consume(event: TestApplicationEvent) {
        // handle the event
    }

}
```

</div>

</div>
