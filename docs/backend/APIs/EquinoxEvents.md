Use the `EquinoxEventsEmitter` API, built on top of the `ApplicationEventPublisher` system, to share events between
services in a strict and controlled way by leveraging `enums`

## Implementation

### Create the events set

Create the enum with the events to share

=== "Java"

    ```java
    public enum TestEvent {
    
        EVENT_ONE,
    
        EVENT_TWO
    
    }
    ```

=== "Kotlin"

    ```java
    enum class TestEvent {
    
        EVENT_ONE,
    
        EVENT_TWO
    
    }
    ```

### Create the EquinoxApplicationEvent

You can choose the constructor to implement based on your requirements to create your custom application event to share
between services

=== "Java"

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

=== "Kotlin"

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

### Create the emitter

You can create your own emitter to customize or for a better readability

=== "Java"

    ```java
    @EventsNotifier // not mandatory, but suggested
    @Service // required annotation
    public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {
    
        // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @EventsNotifier // not mandatory, but suggested
    @Service // required annotation
    class TestEventsEmitter : EquinoxEventsEmitter<TestEvent>() {
    
        // your custom implementation
    
    }
    ```

### Create the collector

You can create your own collector to customize or for a better readability

=== "Java"

    ```java
    @EventsHandler // not mandatory, but suggested
    @FunctionalInterface // not mandatory, but suggested
    public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
        // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @EventsHandler // not mandatory, but suggested
    @FunctionalInterface // not mandatory, but suggested
    interface TestEventsCollector : EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
        // your custom implementation
    
    }
    ```

!!! tip

    Place all files related to the events system into a dedicated `events` package to improve readability and to maintain
    a clean architecture
    
    ```bash { .yaml .no-copy }
    com.your.package
    └── events
         └── TestEvent
         └── TestApplicationEvent
         └── TestEventsEmitter
    ```

## Usage

### Emit an event

You can wire the emitter with a service or multiple services to emit the events

=== "Java"

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

=== "Kotlin"

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

### Collect the emitted events

You can create multiple collectors implementing the `EquinoxEventsCollector` interface

=== "Java"

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

=== "Kotlin"

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