This annotation is useful to indicate those methods used to adding the extracted filters to a [FilteredQuery](../APIs/FilteredQuery.md)

## Usage

=== "Java"

    ```java
    @FiltersAdder(
        description = "Will be added to the query the plates such AA000AA, etc..."
    )
    private void addPlates() {
        // the rest of the method
    }
    ```

=== "Kotlin"

    ```kotlin
    @FiltersAdder(
        description = "Will be added to the query the plates such AA000AA, etc..."
    )
    private fun addPlates() {
        // the rest of the method
    }
    ```