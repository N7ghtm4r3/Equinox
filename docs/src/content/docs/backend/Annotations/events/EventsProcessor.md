---
title: "EventsProcessor"
slug: "backend/Annotations/events/EventsProcessor"
---

Use `EventsProcessor` to identify a service that both handles and emits events. It brings together the
roles described by [EventsHandler](/Equinox/backend/Annotations/events/EventsHandler/) and [EventsNotifier](/Equinox/backend/Annotations/events/EventsNotifier/), making
both responsibilities visible through a single annotation

Like those annotations, `EventsProcessor` is an optional marker used to improve code readability.
The service implements [EquinoxEventsCollector](/Equinox/backend/APIs/EquinoxEvents/#create-the-collector) to
handle events and extends [EquinoxEventsEmitter](/Equinox/backend/APIs/EquinoxEvents/#create-the-emitter) to
publish them

## Usage

The following service uses the `TestEvent` and `TestApplicationEvent` types defined in the
[events guide](/Equinox/backend/APIs/EquinoxEvents/#implementation). Implement `onEventCollected` with your event
handling logic and use the inherited `emitEvent` method whenever the service needs to publish an event


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.events.EventsProcessor;
import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsCollector;
import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsEmitter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@EventsProcessor
public class TestEventsProcessor extends EquinoxEventsEmitter<TestEvent>
        implements EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    public TestEventsProcessor(ApplicationEventPublisher publisher) {
        super(publisher);
    }

    @Override
    public void onEventCollected(TestApplicationEvent event) {
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.events.EventsProcessor
import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsCollector
import com.tecknobit.equinoxbackend.apis.events.EquinoxEventsEmitter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
@EventsProcessor
class TestEventsProcessor(
    publisher: ApplicationEventPublisher
) : EquinoxEventsEmitter<TestEvent>(publisher),
    EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    override fun onEventCollected(event: TestApplicationEvent) {
    }

}
```

</div>

</div>
