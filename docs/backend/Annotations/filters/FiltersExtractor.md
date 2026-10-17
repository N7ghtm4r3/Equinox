This annotation is useful to indicate those methods used to extract from the raw filters set
the specific set indicates by the method, its main usage is related to the [FilteredQuery](../../APIs/FilteredQuery.md).

It belongs to the `com.tecknobit.equinoxbackend.annotations.filters` package.

## Usage 

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.filters.FiltersExtractor;

    @FiltersExtractor(
        description = "Method used to extracts the plate filters to use"
    )
    private HashSet<String> extractPlateFilters() {
        return extractFiltersByPattern(your_pattern); // the specific pattern to extract the plates
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.filters.FiltersExtractor

    @FiltersExtractor(
        description = "Method used to extracts the plate filters to use"
    )
    private fun extractPlateFilters(): HashSet<String> {
        return extractFiltersByPattern(your_pattern) // the specific pattern to extract the plates
    }
    ```
