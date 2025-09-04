This annotation is useful to indicate an implementation of [BatchQuery](../APIs/EquinoxItemsHelper.md#insert-in-batch)

## Usage

=== "Java"
    
    ```java
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