This annotation is useful to mark classes that handle events, such as [EquinoxEventsCollector](../APIs/EquinoxEvents.md#create-the-collector).
Its purpose is to improve the readability of the code

## Usage

=== "Java"

    ```java
    @EventsHandler
    public interface TestEventsCollector extends EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
         // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @EventsHandler
    interface TestEventsCollector : EquinoxEventsCollector<TestEvent, TestApplicationEvent> {
    
        // your custom implementation
    
    }
    ```