The `EntityRetriever` annotation identifies methods that retrieve a persisted
entity from the database through a repository

## Usage

Apply the annotation to the method responsible for retrieving the entity.
The repository call and missing-entity handling remain part of the method's
implementation

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.EntityRetriever;

    @EntityRetriever
    public Item retrieveItem(String id) {
        return itemsRepository.findById(id).orElseThrow();
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.EntityRetriever

    @EntityRetriever
    fun retrieveItem(id: String): Item {
        return itemsRepository.findById(id).orElseThrow()
    }
    ```

The examples assume an existing `Item` entity and an `itemsRepository`
providing `findById`
