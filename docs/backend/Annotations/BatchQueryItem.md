This annotation is useful to indicate an object which its scope is the support to handle the data and manipulate them 
during the batch operations

## Usage

=== "Java"
    
    ```java
    @BatchQueryItem(
        // not mandatory
        description = """
            Informative description about the scope of the this item, 
            such the operation where is used what data are manipulated, etc...
            """ // suggested text block
    )
    public class SimpleItem {
    
        private final String id;
    
        private final String ownedEntityId;
    
        ... rest of the item ...
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @BatchQueryItem(
        // not mandatory
        description = """
            Informative description about the scope of the this item, 
            such the operation where is used what data are manipulated, etc...
            """ // suggested text block
    )
    class SimpleItem(
    
        private val id: String,
    
        private val ownedEntityId: String
    
        // ... rest of the item ...
    
    )
    ```