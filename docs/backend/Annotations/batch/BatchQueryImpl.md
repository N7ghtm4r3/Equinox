This annotation is useful to indicate an implementation of [BatchQuery](../../APIs/EquinoxItemsHelper.md#insert-in-batch).
It belongs to the `com.tecknobit.equinoxbackend.annotations.batch` package.

## Usage

=== "Java"
    
    ```java
    import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryImpl;

    @BatchQueryImpl(
        // not mandatory
        description = """
            Informative description about the behavior of the implemented batch query"
        """ // suggested text block
    )
    public class SimpleBatchQuery implements EquinoxItemsHelper.BatchQuery<Simple> {
    
        ...
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.batch.BatchQueryImpl

    @BatchQueryImpl(
        // not mandatory
        description = """
            Informative description about the behavior of the implemented batch query"
        """ // suggested text block
    )
    class SimpleBatchQuery : EquinoxItemsHelper.BatchQuery<Simple> {
    
        ...
    }
    ```
