---
title: "DtoConvertible"
slug: "backend/APIs/DtoConvertible"
---

The `DTOConvertible` interface allows to convert a complete entity object into the related Data Transfer Object

## Usage

### Entity Class Example


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
class CarEntity(
    val id: String?,
    val name: String?
)  {

}
```

</div>

</div>

### Related DTO Example


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
@DTO
data class CarEntityDto(
    val name: String?
)
```

</div>

</div>

### Implementation

Make implement to the entity class the `DTOConvertible` annotation to override the `convertToRelatedDTO` method for the 
mapping conversion


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
public class CarEntity implements DTOConvertible<CarEntityDto> {

    ...

    @Override
    public CarEntityDto convertToRelatedDTO() {
        return new CarEntityDto(name);
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


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

</div>

</div>

### Conversion

Invoke the `convertToRelatedDTO` method to obtain the `DTO` object


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
public CarEntityDto getCarDTO() {
    CarEntity carEntity = new CarEntity("1", "Ferrari");
    
    return carEntity.convertToRelatedDTO();
}
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
fun getCarDTO(): CarEntityDto {
    val carEntity = CarEntity("1", "Ferrari")

    return carEntity.convertToRelatedDTO()
}
```

</div>

</div>
