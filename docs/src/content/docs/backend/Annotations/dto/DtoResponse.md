---
title: "DtoResponse"
slug: "backend/Annotations/dto/DtoResponse"
---

The `DtoResponse` annotation marks types that carry outgoing response data. It makes the role of a
response DTO clear when reading the backend code

It belongs to the `com.tecknobit.equinoxbackend.annotations.dto` package.

## Usage

Apply `@DtoResponse` to the class that describes the data returned by the backend. In this example,
the response contains the car identifier and name


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse

@DtoResponse
data class CarResponse(
    val id: String,
    val name: String
)
```

</div>

</div>

Use [DtoRequest](/Equinox/backend/Annotations/dto/DtoRequest/) for DTOs carrying the data received in a request, such as the name
supplied when creating a car
