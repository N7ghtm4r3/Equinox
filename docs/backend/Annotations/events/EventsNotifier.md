This annotation is useful to mark classes that send events, such as [EquinoxEventsEmitter](../../APIs/EquinoxEvents.md#create-the-emitter).
Its purpose is to improve the readability of the code

It belongs to the `com.tecknobit.equinoxbackend.annotations.events` package.

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.events.EventsNotifier;

    @Service // required annotation
    @EventsNotifier
    public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {
    
        // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.events.EventsNotifier

    @Service // required annotation
    @EventsNotifier
    class TestEventsEmitter : EquinoxEventsEmitter<TestEvent>() {
    
        // your custom implementation
    
    }
    ```
