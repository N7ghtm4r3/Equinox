Use `EventsProcessor` to identify a service that both handles and emits events. It brings together the
roles described by [EventsHandler](EventsHandler.md) and [EventsNotifier](EventsNotifier.md), making
both responsibilities visible through a single annotation

Like those annotations, `EventsProcessor` is an optional marker used to improve code readability.
The service implements [EquinoxEventsCollector](../../APIs/EquinoxEvents.md#create-the-collector) to
handle events and extends [EquinoxEventsEmitter](../../APIs/EquinoxEvents.md#create-the-emitter) to
publish them

## Usage

The following service uses the `TestEvent` and `TestApplicationEvent` types defined in the
[events guide](../../APIs/EquinoxEvents.md#implementation). Implement `onEventCollected` with your event
handling logic and use the inherited `emitEvent` method whenever the service needs to publish an event

=== "Java"

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

=== "Kotlin"

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

