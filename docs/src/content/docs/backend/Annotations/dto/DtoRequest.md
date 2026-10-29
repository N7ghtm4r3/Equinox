---
title: "DtoRequest"
slug: "backend/Annotations/dto/DtoRequest"
---

The `DtoRequest` annotation marks types that carry incoming request data. It makes the role of a
request DTO clear when reading the backend code

It belongs to the `com.tecknobit.equinoxbackend.annotations.dto` package.

## Usage

Apply `@DtoRequest` to the class that describes the data expected in a request. In this example,
creating a car requires its name


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


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

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest

@DtoRequest
data class CreateCarRequest(
    val name: String
)
```

</div>

</div>

Once the backend has created the car, its response can include the generated identifier together
with the name. Use [DtoResponse](/Equinox/backend/Annotations/dto/DtoResponse/) to identify the DTO carrying that response data
