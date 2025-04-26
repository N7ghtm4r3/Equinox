## Equinox Events

Use the `EquinoxEventsEmitter` API, built on top of the `ApplicationEventPublisher` system, to share events between
services in a strict and controlled way by leveraging `enums`

### Implementation

#### Create the events set

Create the enum with the events to share

```java
enum TestEvent {

    EVENT_ONE,

    EVENT_TWO

}
```

#### Create your EquinoxApplicationEvent

You can choose the constructor to implement based on your requirements to create your custom application event to share
between services

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

#### Create your own emitter

You can create your own emitter to customize or for a better readability

```java

@Service // required annotation
public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {

    // your custom implementation

}
```

#### Create your own collector

You can create your own collector to customize or for a better readability

```java

@FunctionalInterface // not mandatory, but suggested
public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {

    // your custom implementation

}
```

> [!TIP]  
> Place all files related to the events system into a dedicated `events` package to improve readability and maintain a
> clean architecture
>
> ```
> com.your.package
> └── events
>     └── TestEvent
>     └── TestApplicationEvent
>     └── TestEventsEmitter
> ```

### Usage

#### Emit an event

You can wire the emitter with a service or multiple services to emit the events

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

#### Collect the emitted events

You can create multiple collectors implementing the `EquinoxEventsCollector` interface

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

The other apis will be gradually released

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot) [![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit
