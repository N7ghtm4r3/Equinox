This annotation is useful to annotation is useful to indicate an implementation of a [BatchSynchronizationProcedure](../APIs/BatchSynchronizationProcedure.md)

## Usage

=== "Java"

    ```java
    @BatchSyncProcedureImpl(
        // not mandatory
        description = """
            Informative description about the behavior of the implemented procedure"
            """ // suggested text block
    )
    public class SimpleBatchQuerySyncProcedure extends BatchSynchronizationProcedure<Integer, String, SimpleCalendarBatchItem> {
    
        // ... rest of the procedure ...
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @BatchSyncProcedureImpl(
        // not mandatory
        description = """
            Informative description about the behavior of the implemented procedure"
            """ // suggested text block
    )
    class SimpleBatchQuerySyncProcedure : BatchSynchronizationProcedure<Int, String, SimpleCalendarBatchItem>() {
    
        // ... rest of the procedure ...
    
    }
    ```