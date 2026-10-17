The `DtoUtil` class converts lists of entities into their related Data Transfer Objects. You can use
the mapping defined by each entity or provide a converter callback to customize the DTOs

!!! warning "Experimental API"

    The conversion methods are experimental and may change in future releases

## Usage

The following examples convert a list of cars into DTOs containing their names. First, define the
entity and its DTO, then prepare the list used throughout the conversion examples

### Entity class example

Each `CarEntity` stores the car identifier and name. Implementing
[DTOConvertible](../APIs/DtoConvertible.md) lets the entity define how to create its DTO through
`convertToRelatedDTO`

=== "Java"

    ```java
    import com.tecknobit.equinoxcore.dtoutils.DTOConvertible;

    public class CarEntity implements DTOConvertible<CarEntityDto> {

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

        @Override
        public CarEntityDto convertToRelatedDTO() {
            return new CarEntityDto(name);
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxcore.dtoutils.DTOConvertible

    class CarEntity(
        val id: String,
        val name: String
    ) : DTOConvertible<CarEntityDto> {

        override fun convertToRelatedDTO(): CarEntityDto {
            return CarEntityDto(name)
        }

    }
    ```

### Related DTO example

The `CarEntityDto` contains the name copied from the entity. The identifier belongs to the entity
and is not needed in this DTO

=== "Java"

    ```java
    import com.tecknobit.equinoxcore.annotations.DTO;

    @DTO
    public class CarEntityDto {

        private final String name;

        public CarEntityDto(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxcore.annotations.DTO

    @DTO
    data class CarEntityDto(
        val name: String
    )
    ```

### Prepare the entities

Create two cars to use in the following examples. Their position in this list also determines
their order in the conversion result

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.utils.DtoUtil;
    import java.util.ArrayList;
    import java.util.List;

    List<CarEntity> entities = new ArrayList<>();
    entities.add(new CarEntity("1", "Ferrari"));
    entities.add(new CarEntity("2", "Lamborghini"));
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.utils.DtoUtil

    val entities = listOf(
        CarEntity("1", "Ferrari"),
        CarEntity("2", "Lamborghini")
    )
    ```

### Convert entities

With the entities ready, call `convertToDTOs` to create their DTOs. Each entity supplies its own
mapping through `convertToRelatedDTO`

=== "Java"

    ```java
    List<CarEntityDto> dtos = DtoUtil.convertToDTOs(entities);

    for (CarEntityDto dto : dtos) {
        System.out.println(dto.getName()); // e.g. Ferrari
    }
    ```

=== "Kotlin"

    ```kotlin
    val dtos = DtoUtil.convertToDTOs<CarEntity, CarEntityDto>(entities)

    for (dto in dtos) {
        println(dto.name) // e.g. Ferrari
    }
    ```

Both names appear in the same order as the source entities. The DTO type requested in the call
must match the type returned by `convertToRelatedDTO`

### Process converted DTOs

When you need to work with a DTO as soon as it has been created, pass an `afterConversion`
callback. It receives both the source entity and its DTO, so you can use information from either

=== "Java"

    ```java
    List<CarEntityDto> dtos = DtoUtil.convertToDTOs(
        entities,
        (entity, dto) -> {
            System.out.println(entity.getId() + ": " + dto.getName()); // e.g. Ferrari
        }
    );
    ```

=== "Kotlin"

    ```kotlin
    val dtos = DtoUtil.convertToDTOs<CarEntity, CarEntityDto>(
        entities,
        { entity, dto ->
            println("${entity.id}: ${dto.name}") // e.g. Ferrari
        }
    )
    ```

Here the callback prints the entity identifier beside the DTO name. It runs synchronously after
each conversion, before the DTO is added to the result

### Access the entity index

If you also need the entity's position in the source list, use `convertToDTOsWithIndex`. Its callback
receives the entity, its zero-based index and the DTO, in this order

=== "Java"

    ```java
    List<CarEntityDto> dtos = DtoUtil.convertToDTOsWithIndex(
        entities,
        (entity, index, dto) -> {
            System.out.println(index + ": " + dto.getName()); // e.g. Ferrari
        }
    );
    ```

=== "Kotlin"

    ```kotlin
    val dtos = DtoUtil.convertToDTOsWithIndex<CarEntity, CarEntityDto>(
        entities,
        { entity, index, dto ->
            println("$index: ${dto.name}") // e.g. Ferrari
        }
    )
    ```

The first car has index `0` and the second has index `1`. Calling this method without a callback
still converts the entities, but does not expose their indices

### Use a custom converter

For a different mapping, supply a converter callback instead of relying on `convertToRelatedDTO`.
In this example, the callback creates each DTO with the `Car: ` prefix added to its name

=== "Java"

    ```java
    List<CarEntityDto> dtos = DtoUtil.convertToDTOs(
        entities,
        entity -> {
            return new CarEntityDto("Car: " + entity.getName());
        }
    );

    for (CarEntityDto dto : dtos) {
        System.out.println(dto.getName()); // e.g. Ferrari
    }
    ```

=== "Kotlin"

    ```kotlin
    val dtos = DtoUtil.convertToDTOs<CarEntity, CarEntityDto>(
        entities,
        { entity ->
            CarEntityDto("Car: ${entity.name}")
        }
    )

    for (dto in dtos) {
        println(dto.name) // e.g. Ferrari
    }
    ```

You can also combine this mapping with an `afterConversion` callback. Both conversion methods
accept the converter as the third argument, after that callback. The entities still need to
implement `DTOConvertible`, even when you supply the mapping yourself

## Conversion behavior

Each call returns a new list with the DTOs in source order. If the source list is empty, the result
is empty too

Pass a non-null source list. When using the entity's default mapping, its elements must also be
non-null. A custom converter receives each element as supplied and is responsible for handling it

You can omit the processing callback when you only need the converted list. If a converter or
callback throws an exception, conversion stops and the exception propagates to the caller
