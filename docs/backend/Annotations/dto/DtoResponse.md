The `DtoResponse` annotation marks types that carry outgoing response data. It makes the role of a
response DTO clear when reading the backend code

It belongs to the `com.tecknobit.equinoxbackend.annotations.dto` package.

## Usage

Apply `@DtoResponse` to the class that describes the data returned by the backend. In this example,
the response contains the car identifier and name

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;

    @DtoResponse
    public class CarResponse {

        private final String id;
        private final String name;

        public CarResponse(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse

    @DtoResponse
    data class CarResponse(
        val id: String,
        val name: String
    )
    ```

Use [DtoRequest](DtoRequest.md) for DTOs carrying the data received in a request, such as the name
supplied when creating a car
