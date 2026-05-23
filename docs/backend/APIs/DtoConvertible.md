The `DTOConvertible` interface allows to convert a complete entity object into the related Data Transfer Object

## Usage

### Entity Class Example

=== "Java"

    ```java
    public class CarEntity {
        
        private final String id;
        
        private final String name;
    
        public CarEntity(String id, String name) {
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
    class CarEntity(
        val id: String?,
        val name: String?
    )  {
    
    }
    ```

### Related DTO Example

=== "Java"

    ```java
    @DTO
    public class CarEntityDto {
    
        private final String name;
    
        public CarEntityDto(String id, String name) {
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
    
    }
    ```

=== "Kotlin"

    ```kotlin
    @DTO
    data class CarEntityDto(
        val name: String?
    )
    ```

### Implementation

Make implement to the entity class the `DTOConvertible` annotation to override the `convertToRelatedDTO` method for the 
mapping conversion

=== "Java"

    ```java
    public class CarEntity implements DTOConvertible<CarEntityDto> {

        ...
    
        @Override
        public CarEntityDto convertToRelatedDTO() {
            return new CarEntityDto(name);
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    class CarEntity(
        val id: String?,
        val name: String?
    ) : DTOConvertible<CarEntityDto?> {
    
        override fun convertToRelatedDTO(): CarEntityDto {
            return CarEntityDto(name)
        }
    }
    ```

### Conversion

Invoke the `convertToRelatedDTO` method to obtain the `DTO` object

=== "Java"

    ```java
    public CarEntityDto getCarDTO() {
        CarEntity carEntity = new CarEntity("1", "Ferrari");
        
        return carEntity.convertToRelatedDTO();
    }
    ```

=== "Kotlin"

    ```kotlin
    fun getCarDTO(): CarEntityDto {
        val carEntity = CarEntity("1", "Ferrari")

        return carEntity.convertToRelatedDTO()
    }
    ```