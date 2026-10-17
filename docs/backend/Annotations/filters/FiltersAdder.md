This annotation is useful to indicate those methods used to adding the extracted filters to a [FilteredQuery](../../APIs/FilteredQuery.md).
It belongs to the `com.tecknobit.equinoxbackend.annotations.filters` package.

## Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.filters.FiltersAdder;

    @FiltersAdder(
        description = "Will be added to the query the plates such AA000AA, etc..."
    )
    private void addPlates() {
        // the rest of the method
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.filters.FiltersAdder

    @FiltersAdder(
        description = "Will be added to the query the plates such AA000AA, etc..."
    )
    private fun addPlates() {
        // the rest of the method
    }
    ```
