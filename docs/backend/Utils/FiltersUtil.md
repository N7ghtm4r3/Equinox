The `FiltersUtil` class provides utility methods to work with filters in your backend

## resolveEnumSetFilters

Use `resolveEnumSetFilters` when your filters are enum values and you need their names as text.
The method turns your selection into a set of names matching the enum declaration

Pass the selected values as `filters` and their enum class as `enumType`.
If the selection is empty or `null`, the result includes every value from that enum

### Enum example

Define the possible values for your filter

=== "Java"

    ```java
    enum Status {
        ACTIVE,
        INACTIVE
    }
    ```

=== "Kotlin"

    ```kotlin
    enum class Status {
        ACTIVE,
        INACTIVE
    }
    ```

### Usage

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.utils.FiltersUtil;

    import java.util.Set;

    Set<String> selected = FiltersUtil.resolveEnumSetFilters(Set.of(Status.ACTIVE), Status.class);
    // Contains "ACTIVE"
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.utils.FiltersUtil

    val selected = FiltersUtil.resolveEnumSetFilters(setOf(Status.ACTIVE), Status::class.java)
    // Contains "ACTIVE"
    ```
