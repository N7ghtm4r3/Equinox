The `ScopeProvider` annotation marks types and record components that provide a membership scope

## Usage

`ProjectScope` exposes the project identifier that defines its scope

=== "Java"

    ```java
    import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider;

    @ScopeProvider
    public interface ProjectScope {
        String getProjectId();
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxmisc.backendmembership.annotations.ScopeProvider

    @ScopeProvider
    interface ProjectScope {
        fun getProjectId(): String
    }
    ```

The module already applies this marker to [Scoped](../interfaces/Scoped.md)
