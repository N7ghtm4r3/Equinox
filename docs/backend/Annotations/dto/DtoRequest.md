The `DtoRequest` annotation marks types that carry incoming request data. It makes the role of a
request DTO clear when reading the backend code

It belongs to the `com.tecknobit.equinoxbackend.annotations.dto` package.

## Usage

Apply `@DtoRequest` to the class that describes the data expected in a request. In this example,
creating a car requires its name

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;

    @DtoRequest
    public class CreateCarRequest {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest

    @DtoRequest
    data class CreateCarRequest(
        val name: String
    )
    ```

Once the backend has created the car, its response can include the generated identifier together
with the name. Use [DtoResponse](DtoResponse.md) to identify the DTO carrying that response data
