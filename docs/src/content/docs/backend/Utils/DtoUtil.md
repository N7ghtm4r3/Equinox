---
title: "DtoUtil"
slug: "backend/Utils/DtoUtil"
---

The `DtoUtil` class provides utility methods for Data Transfer Objects (DTOs), the objects used to send
data to a client. Its conversion methods help you prepare a list of DTOs from a list of entities


<aside class="equinox-callout warning">

<p class="equinox-callout-title">Experimental API</p>


The conversion methods are experimental and may change in future releases

</aside>

## Example setup

The examples use cars with an identifier and a name. The client only needs the name, so that is
what each DTO will contain

### Entity class example

Implement [DTOConvertible](/Equinox/backend/APIs/DtoConvertible/) on the entity and define `convertToRelatedDTO`
to choose which information to include in its DTO


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


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

</div>

</div>

### Related DTO example

Define the object that will carry the car name to the client


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxcore.annotations.DTO

@DTO
data class CarEntityDto(
    val name: String
)
```

</div>

</div>

### Prepare the entities

Create the list of cars to convert


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.utils.DtoUtil;
import java.util.ArrayList;
import java.util.List;

List<CarEntity> entities = new ArrayList<>();
entities.add(new CarEntity("1", "Ferrari"));
entities.add(new CarEntity("2", "Lamborghini"));
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.utils.DtoUtil

val entities = listOf(
    CarEntity("1", "Ferrari"),
    CarEntity("2", "Lamborghini")
)
```

</div>

</div>

## convertToDTOs

Use `convertToDTOs` when you want to turn a list of entities into a list of DTOs.
It uses each entity's `convertToRelatedDTO` method and keeps the items in the same order

You can also provide `afterConversion` to perform an action for each converted item.
The action receives the original entity and its DTO, so you can use information from both

To choose different data for a particular conversion, provide a `converter` as the second argument.
If you also use `afterConversion`, pass the action second and the converter third

### Usage


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
List<CarEntityDto> dtos = DtoUtil.convertToDTOs(entities);
dtos.forEach(dto -> System.out.println(dto.getName())); // Ferrari, then Lamborghini
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
val dtos = DtoUtil.convertToDTOs<CarEntity, CarEntityDto>(entities)
dtos.forEach { println(it.name) } // Ferrari, then Lamborghini
```

</div>

</div>

## convertToDTOsWithIndex

Use `convertToDTOsWithIndex` when the action you perform for each DTO also needs its position in the
list, such as displaying a number beside each item. The action receives the original entity, its
position, and the DTO. Positions start at `0`

The method returns the converted DTOs in the same order as the entities.
You can also supply a `converter` to choose different data for the DTOs: pass it as the second
argument, or as the third argument when you also provide an action

### Usage


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
List<CarEntityDto> dtos = DtoUtil.convertToDTOsWithIndex(
        entities, (entity, index, dto) -> System.out.println(index + ": " + dto.getName())
);
// Prints "0: Ferrari" and "1: Lamborghini"
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
val dtos = DtoUtil.convertToDTOsWithIndex<CarEntity, CarEntityDto>(
    entities,
    { _, index, dto -> println("$index: ${dto.name}") }
)
// Prints "0: Ferrari" and "1: Lamborghini"
```

</div>

</div>
