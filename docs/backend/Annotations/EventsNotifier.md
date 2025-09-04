This annotation is useful to mark classes that send events, such as [EquinoxEventsEmitter](../APIs/EquinoxEvents.md#create-the-emitter). 
Its purpose is to improve the readability of the code

## Usage

=== "Java"

    ```java
    @Service // required annotation
    @EventsNotifier
    public class TestEventsEmitter extends EquinoxEventsEmitter<TestEvent> {
    
        // your custom implementation
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @Service // required annotation
    @EventsNotifier
    class TestEventsEmitter : EquinoxEventsEmitter<TestEvent>() {
    
        // your custom implementation
    
    }
    ```