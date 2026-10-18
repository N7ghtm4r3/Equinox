The `Manager` annotation identifies backend interfaces that define endpoints shared by multiple
controllers. Its `services` parameter specifies the services used by the annotated interface

It belongs to the `com.tecknobit.equinoxbackend.annotations` package

## Usage

The following interface declares the endpoints shared by your controllers and identifies
`ItemsService` as the service it uses. Replace this example service with your own service class

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.Manager;

    @Manager(services = {ItemsService.class})
    public interface ItemsManager {
        // shared endpoint declarations
    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.Manager

    @Manager(services = [ItemsService::class])
    interface ItemsManager {
        // shared endpoint declarations
    }
    ```

The `services` parameter accepts multiple service classes. It is optional and defaults to an empty array
