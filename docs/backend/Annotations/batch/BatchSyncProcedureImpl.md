This annotation is useful to indicate an implementation of [BatchSynchronizationProcedure](../../APIs/BatchSynchronizationProcedure.md).
It belongs to the `com.tecknobit.equinoxbackend.annotations.batch` package.

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.batch.BatchSyncProcedureImpl;

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
    import com.tecknobit.equinoxbackend.annotations.batch.BatchSyncProcedureImpl

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
