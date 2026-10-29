---
title: "Overview"
slug: "backend/Validation"
---

<span id="overview"></span>

Use the backend constraints to validate request fields with Jakarta Bean Validation

| Constraint | Validates |
| --- | --- |
| [Name](/Equinox/backend/Validation/Name/) | Names up to 20 characters |
| [Surname](/Equinox/backend/Validation/Surname/) | Surnames up to 30 characters |
| [Email](/Equinox/backend/Validation/Email/) | Email addresses up to 75 characters |
| [UserLanguage](/Equinox/backend/Validation/UserLanguage/) | Supported language keys or values |
| [ServerSecret](/Equinox/backend/Validation/ServerSecret/) | The server protection secret |

All constraints reject null and empty values. See each constraint's page for its
complete rules and default validation message

## Usage

Annotate your request fields and add `@Valid` to the controller's request parameter


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

public record ProfileRequest(@Email String email) {}

@RestController
public class ProfileController {

    @PostMapping("/profile/validate")
    public void validateProfile(@Valid @RequestBody ProfileRequest request) {}
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class ProfileRequest(@field:Email val email: String?)

@RestController
class ProfileController {

    @PostMapping("/profile/validate")
    fun validateProfile(@Valid @RequestBody request: ProfileRequest) {}
}
```

</div>

</div>

Each constraint supports the standard `message`, `groups`, and `payload` attributes
