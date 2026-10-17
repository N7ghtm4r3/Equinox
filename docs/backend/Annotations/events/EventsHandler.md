This annotation is useful to mark classes that handle events, such as [EquinoxEventsCollector](../../APIs/EquinoxEvents.md#create-the-collector).
Its purpose is to improve the readability of the code

It belongs to the `com.tecknobit.equinoxbackend.annotations.events` package.

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.events.EventsHandler;

    @EventsHandler
    public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
         // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.events.EventsHandler

    @EventsHandler
    interface TestEventsCollector : EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
        // your custom implementation
    
    }
    ```
