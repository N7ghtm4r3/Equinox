---
title: "EquinoxEvents"
slug: "backend/APIs/EquinoxEvents"
---

Use the `EquinoxEventsEmitter` API, built on top of the `ApplicationEventPublisher` system, to share events between
services in a strict and controlled way by leveraging `enums`

The optional [EventsNotifier](/Equinox/backend/Annotations/events/EventsNotifier/) and
[EventsHandler](/Equinox/backend/Annotations/events/EventsHandler/) annotations identify the emitter and collector roles
to improve code readability.

Use [EventsProcessor](/Equinox/backend/Annotations/events/EventsProcessor/), available since `2.0.0`, to identify
a service that both handles and emits events with a single annotation.

Use [EventConsumer](/Equinox/backend/Annotations/events/EventConsumer/), available since
`2.0.0`, to mark a type or method that consumes application events and optionally
identify its application event class. This source-only marker does not register
listeners or filter events

## Implementation

### Create the events set

Create the enum with the events to share


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
public enum TestEvent {

    EVENT_ONE,

    EVENT_TWO

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```java
enum class TestEvent {

    EVENT_ONE,

    EVENT_TWO

}
```

</div>

</div>

### Create the EquinoxApplicationEvent

You can choose the constructor to implement based on your requirements to create your custom application event to share
between services


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
public class TestApplicationEvent extends EquinoxApplicationEvent<TestEvent> {

    public TestApplicationEvent(Object source, TestEvent eventType) {
        super(source, eventType);
    }

    public TestApplicationEvent(Object source, Clock clock, TestEvent eventType) {
        super(source, clock, eventType);
    }

    public TestApplicationEvent(Object source, TestEvent eventType, Object... extra) {
        super(source, eventType, extra);
    }

    public TestApplicationEvent(Object source, Clock clock, TestEvent eventType, Object... extra) {
        super(source, clock, eventType, extra);
    }

    public TestApplicationEvent(Object source, TestEvent eventType, OnEventConsumed onEventConsumed) {
        super(source, eventType, onEventConsumed);
    }

    public TestApplicationEvent(Object source, Clock clock, TestEvent eventType, OnEventConsumed onEventConsumed) {
        super(source, clock, eventType, onEventConsumed);
    }

    public TestApplicationEvent(Object source, TestEvent eventType, OnEventConsumed onEventConsumed, Object... extra) {
        super(source, eventType, onEventConsumed, extra);
    }

    public TestApplicationEvent(Object source, Clock clock, TestEvent eventType, OnEventConsumed onEventConsumed, Object... extra) {
        super(source, clock, eventType, onEventConsumed, extra);
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
class TestApplicationEvent : EquinoxApplicationEvent<TestEvent> {

    constructor(
        source: Any,
        eventType: TestEvent
    ) : super(source, eventType)

    constructor(
        source: Any,
        clock: Clock,
        eventType: TestEvent
    ) : super(source, clock, eventType)

    constructor(
        source: Any,
        eventType: TestEvent,
        vararg extra: Any
    ) : super(source, eventType, *extra)

    constructor(
        source: Any,
        clock: Clock,
        eventType: TestEvent,
        vararg extra: Any
    ) : super(source, clock, eventType, *extra)

    constructor(
        source: Any,
        eventType: TestEvent,
        onEventConsumed: OnEventConsumed
    ) : super(source, eventType, onEventConsumed)

    constructor(
        source: Any,
        clock: Clock,
        eventType: TestEvent,
        onEventConsumed: OnEventConsumed
    ) : super(source, clock, eventType, onEventConsumed)

    constructor(
        source: Any,
        eventType: TestEvent,
        onEventConsumed: OnEventConsumed,
        vararg extra: Any
    ) : super(source, eventType, onEventConsumed, *extra)

    constructor(
        source: Any,
        clock: Clock,
        eventType: TestEvent,
        onEventConsumed: OnEventConsumed,
        vararg extra: Any
    ) : super(source, clock, eventType, onEventConsumed, *extra)

}
```

</div>

</div>

### Create the emitter

You can create your own emitter to customize or for a better readability


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
@EventsNotifier // not mandatory, but suggested
@Service // required annotation
public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {

    // your custom implementation

}
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
@EventsNotifier // not mandatory, but suggested
@Service // required annotation
class TestEventsEmitter : EquinoxEventsEmitter<TestEvent>() {

    // your custom implementation

}
```

</div>

</div>

### Create the collector

You can create your own collector to customize or for a better readability


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
@EventsHandler // not mandatory, but suggested
@FunctionalInterface // not mandatory, but suggested
public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    // your custom implementation

}
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
@EventsHandler // not mandatory, but suggested
@FunctionalInterface // not mandatory, but suggested
interface TestEventsCollector : EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    // your custom implementation

}
```

</div>

</div>


<aside class="equinox-callout tip">

<p class="equinox-callout-title">Tip</p>


Place all files related to the events system into a dedicated `events` package to improve readability and to maintain
a clean architecture

```bash
com.your.package
└── events
     └── TestEvent
     └── TestApplicationEvent
     └── TestEventsEmitter
```

</aside>

## Usage

### Emit an event

You can wire the emitter with a service or multiple services to emit the events


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
@Service
public class EventsService {

    @Autowired // wire the emitter
    private TestEventsEmitter eventsEmitter;

    // any service method
    public void triggerEmitter() {
        // choose from your set the event to emit
        TestEvent type = EVENT_ONE;

        // create a not required callback to execute after the event emitted is performed
        EquinoxEventsCollector.OnEventConsumed onEventConsumed = new EquinoxEventsCollector.OnEventConsumed() {
            @Override
            public void perform(Object... extra) {
                // perform extra action after the event has been consumed
            }
        };

        // create the event
        TestApplicationEvent event = new TestApplicationEvent(this, type, onEventConsumed, /*add extra arguments if needed*/);

        // emit the event
        eventsEmitter.emitEvent(event);
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
@Service
class EventsService {

    @Autowired // wire the emitter
    private lateinit var eventsEmitter: TestEventsEmitter

    // any service method
    fun triggerEmitter() {
        // choose from your set the event to emit
        val type: TestEvent = TestEvent.EVENT_ONE

        // create a not required callback to execute after the event emitted is performed
        val onEventConsumed = object : EquinoxEventsCollector.OnEventConsumed {
            override fun perform(vararg extra: Any) {
                // perform extra action after the event has been consumed
            }
        }

        // create the event
        val event = TestApplicationEvent(this, type, onEventConsumed /*, add extra arguments if needed */)

        // emit the event
        eventsEmitter.emitEvent(event)
    }

}
```

</div>

</div>

### Collect the emitted events

You can create multiple collectors implementing the `EquinoxEventsCollector` interface


<div class="equinox-tabs" id="equinox-tabs-5">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-5-tab-0" aria-controls="equinox-tabs-5-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-5-tab-1" aria-controls="equinox-tabs-5-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-5-panel-0" aria-labelledby="equinox-tabs-5-tab-0" tabindex="0">


```java
@Service
public class AnyService implements TestEventsCollector {

    @Override
    public void onEventCollected(TestApplicationEvent event) {
        // get the extra arguments from the event
        Object[] extra = event.getExtra();

        // handle the specific type of the event collected
        switch (event.getEventType()) {
            case EVENT_ONE -> {
                System.out.println("EVENT_ONE collected!");

                // perform the OnEventConsumed if needed
                event.performOnEventConsumed();
            }
            case EVENT_TWO -> {
                System.out.println("EVENT_TWO collected!");

                // perform the OnEventConsumed if needed
                event.performOnEventConsumed();
            }
        }
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-5-panel-1" aria-labelledby="equinox-tabs-5-tab-1" tabindex="0">


```kotlin
@Service
class AnyService : TestEventsCollector {

    override fun onEventCollected(event: TestApplicationEvent) {
        // get the extra arguments from the event
        val extra: Array<Any> = event.extra

        // handle the specific type of the event collected
        when (event.eventType) {
            TestEvent.EVENT_ONE -> {
                println("EVENT_ONE collected!")

                // perform the OnEventConsumed if needed
                event.performOnEventConsumed()
            }
            TestEvent.EVENT_TWO -> {
                println("EVENT_TWO collected!")

                // perform the OnEventConsumed if needed
                event.performOnEventConsumed()
            }
        }
    }

}
```

</div>

</div>

### Perform the callback with collector extra arguments

Since `2.0.0`, the collector can invoke the callback with its own extra arguments,
either alone or together with the emitter extra arguments. Set the callback before
invoking these methods


<div class="equinox-tabs" id="equinox-tabs-6">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-6-tab-0" aria-controls="equinox-tabs-6-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-6-tab-1" aria-controls="equinox-tabs-6-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-6-panel-0" aria-labelledby="equinox-tabs-6-tab-0" tabindex="0">


```java
// Use the emitter extra arguments stored in the event
event.performOnEventConsumed();

// Use only the collector extra arguments
event.performOnEventConsumedWithCollectorExtra(new Object[]{"result"});

// Use both sets of extra arguments
event.performOnEventConsumed(event.getExtra(), new Object[]{"result"});
```

</div>

<div role="tabpanel" id="equinox-tabs-6-panel-1" aria-labelledby="equinox-tabs-6-tab-1" tabindex="0">


```kotlin
// Use the emitter extra arguments stored in the event
event.performOnEventConsumed()

// Use only the collector extra arguments
event.performOnEventConsumedWithCollectorExtra(arrayOf<Any>("result"))

// Use both sets of extra arguments
event.performOnEventConsumed(event.extra, arrayOf<Any>("result"))
```

</div>

</div>

Override the two-array `perform` method to handle separate extra arguments.
Its default implementation performs no action. The default
`performWithCollectorExtra` implementation invokes it with `null` emitter extra arguments


<div class="equinox-tabs" id="equinox-tabs-7">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-7-tab-0" aria-controls="equinox-tabs-7-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-7-tab-1" aria-controls="equinox-tabs-7-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-7-panel-0" aria-labelledby="equinox-tabs-7-tab-0" tabindex="0">


```java
event.setOnEventConsumed(new EquinoxEventsCollector.OnEventConsumed() {
    @Override
    public void perform(Object... extra) {
        // Handle the emitter extra arguments
    }

    @Override
    public void perform(Object[] emitterExtra, Object[] collectorExtra) {
        // Handle both sets, or only collectorExtra when emitterExtra is null
    }
});
```

</div>

<div role="tabpanel" id="equinox-tabs-7-panel-1" aria-labelledby="equinox-tabs-7-tab-1" tabindex="0">


```kotlin
event.setOnEventConsumed(object : EquinoxEventsCollector.OnEventConsumed {
    override fun perform(vararg extra: Any) {
        // Handle the emitter extra arguments
    }

    override fun perform(
        emitterExtra: Array<out Any>?,
        collectorExtra: Array<out Any>?
    ) {
        // Handle both sets, or only collectorExtra when emitterExtra is null
    }
})
```

</div>

</div>
