This annotation is useful to mark types and methods that consume application events.
Its purpose is to improve the readability of the code

The optional `event` parameter identifies the application event class associated with the consumer.
It defaults to `EquinoxApplicationEvent.class`

The optional `type` parameter describes the consumed event type as a string, such as `"EVENT_ONE"`.
It defaults to `""`, meaning no specific event type is documented

This annotation is retained only in source code and does not register listeners or filter events.
The `type` value is descriptive and is not checked against the event's enum

## Usage

The following examples use `TestApplicationEvent` and the `EVENT_ONE` type from the
[events guide](../../APIs/EquinoxEvents.md#implementation). Both parameters can be omitted when only
the consumer role needs to be documented

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.events.EventConsumer;

    public class TestEventConsumer {

        @EventConsumer(event = TestApplicationEvent.class, type = "EVENT_ONE")
        public void consume(TestApplicationEvent event) {
            // handle the event
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.events.EventConsumer

    class TestEventConsumer {

        @EventConsumer(event = TestApplicationEvent::class, type = "EVENT_ONE")
        fun consume(event: TestApplicationEvent) {
            // handle the event
        }

    }
    ```
