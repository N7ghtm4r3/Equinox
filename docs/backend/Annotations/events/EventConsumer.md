This annotation is useful to mark types and methods that consume application events.
Its purpose is to improve the readability of the code

The optional `event` parameter identifies the application event class associated with the consumer.
It defaults to `EquinoxApplicationEvent.class`. This annotation is retained only in source code
and does not register listeners or filter events

## Usage

The following examples use `TestApplicationEvent` from the
[events guide](../../APIs/EquinoxEvents.md#create-the-equinoxapplicationevent)

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.events.EventConsumer;

    public class TestEventConsumer {

        @EventConsumer(event = TestApplicationEvent.class)
        public void consume(TestApplicationEvent event) {
            // handle the event
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.events.EventConsumer

    class TestEventConsumer {

        @EventConsumer(event = TestApplicationEvent::class)
        fun consume(event: TestApplicationEvent) {
            // handle the event
        }

    }
    ```
